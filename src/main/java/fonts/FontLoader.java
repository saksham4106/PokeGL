package fonts;

import org.joml.Vector2f;
import renderer.Texture;
import utils.Assets;

import java.io.File;
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
        fontSource = Assets.readFileAsString(fontPath).split(System.lineSeparator());
        for (String text : fontSource) {
            if (text.startsWith("common")) {
                imageWidth = Integer.parseInt(getValue("scaleW", text));
                imageHeight = Integer.parseInt(getValue("scaleH", text));
                lineHeight = Integer.parseInt(getValue("lineHeight", text));

            } else if (text.startsWith("page")) {
                imagePath = Paths.get(this.fontPath).getParent().toString().replace("\\", "/") + "/" + getValue("file", text);

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

    public Vector2f getStringDimensions(String text, float scale){
        float width = 0;
        float height = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            Character ch = characters.getOrDefault((int)c, new Character(0 , 0 , 0 , 0 , 0 , 0 , 0 , 0));

            width += ch.xAdvance;
            height = Math.max(height, (ch.height + ch.yOffset));
        }
        return new Vector2f(width * scale, height * scale);
    }

    public float getMaxHeight(float scale){
        return getStringDimensions("\"abcdefghijklmnopqrstuvwxyz_~", scale).y + 30 * scale; // yOffset for down characters (y,g,etc)
    }
}

