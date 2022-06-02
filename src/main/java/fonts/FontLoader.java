package fonts;

import renderer.Texture;
import utils.Assets;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FontLoader {
    public String fontPath;
    public String imagePath;
    public Texture texture;
    public int lineHeight;
    public int imageHeight;
    public int imageWidth;

    public Map<Integer, Character> characters;

    public FontLoader(String fontPath){

        this.fontPath = fontPath;
        this.characters = new HashMap<>();
        parseFontFile();
        this.texture = Assets.getTexture(this.imagePath);
    }

    public void parseFontFile(){
        String[] fontSource;
        fontSource = Assets.readFileAsString(fontPath).split("\n");
        for (String text : fontSource) {
            if (text.startsWith("common")) {
                imageWidth = Integer.parseInt(getValue("scaleW", text));
                imageHeight = Integer.parseInt(getValue("scaleH", text));
                lineHeight = Integer.parseInt(getValue("lineHeight", text));

            } else if (text.startsWith("page")) {
                imagePath = Paths.get(this.fontPath).getParent() + "/" + getValue("file", text);

            } else if (text.startsWith("char ")) {
                int charID = Integer.parseInt(getValue("id", text));

                int x = Integer.parseInt(getValue("x", text));
                int y = Integer.parseInt(getValue("y", text));

                int width = Integer.parseInt(getValue("width", text));
                int height = Integer.parseInt(getValue("height", text));

                int xAdvance = Integer.parseInt(getValue("xadvance", text));
                int yOffset = Integer.parseInt(getValue("yoffset", text));
                int xOffset = Integer.parseInt(getValue("xoffset", text));

                characters.put(charID, new Character(width, height, xAdvance, charID, x, y, yOffset, xOffset));
            }
        }
    }

    public Character getCharacter(char character){
        return characters.getOrDefault((int)character, new Character(0, 0, 0, 0, 0, 0, 0, 0));
    }

    public String getValue(String var, String text){
        return Arrays.stream(text.split(" ")).filter(token -> token.contains(var)).findFirst()
                .get().split("=")[1].replaceAll("\"", "");

    }
}

