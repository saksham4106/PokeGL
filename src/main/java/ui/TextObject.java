package ui;

import fonts.Character;
import fonts.FontLoader;
import fonts.Fonts;
import game.components.Component;
import game.GameObject;
import game.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Sprite;
import utils.Assets;
import utils.ColorUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// FIXME: 15/05/22  THIS IS SHIT. implement SDF rendering
public class TextObject {

    public final FontLoader fontLoader;
    public String text;

    public List<GameObject> charObjects;
    public float x;
    public float y;
    public final float scale;
    public final int zIndex;
    public boolean isUIElement;
    public float totalWidth = 0;
    public float totalHeight;
    public float bottomEnd = 0;
    public Vector4f color;
    public float spacePadding;

    public TextObject(float x, float y, String text, boolean isUIElement){
        this(x,y, 1, text, Fonts.ATARI_CLASSIC_FONT, 100, isUIElement, ColorUtils.getColor(Color.WHITE));
    }

    public TextObject(float x, float y, float scale, String text, FontLoader fontLoader, int zIndex, boolean isUIElement, Vector4f color) {
        this.fontLoader = fontLoader;
        this.text = text;
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.zIndex = zIndex;
        this.isUIElement = isUIElement;
        this.charObjects = new ArrayList<>();
        this.color = new Vector4f(color);

        generateText();
    }

    private void generateText(){
        Sprite sprite = new Sprite(0, 0, Assets.getTexture(fontLoader.imagePath));

        float currentY = this.y;
        float currentX = this.x;

        // FIXME: 15/05/22 Find a better way to do this
        spacePadding = fontLoader.getCharacter(' ').xAdvance * scale;

        for(int charIndex = 0; charIndex < text.length(); charIndex++){
            char c = text.charAt(charIndex);

            if(c == '\n'){
                currentY -= fontLoader.lineHeight * scale;
                currentX = this.x;
                continue;
            }else if(c == '\t'){
                currentX += spacePadding * 4;
                this.totalWidth += spacePadding * 4;
                continue;
            }

            Character character = fontLoader.getCharacter(c);
            sprite = new Sprite(character.width, character.height, sprite.texture);
            sprite.setTexCoords(character.getTexCoords(fontLoader.imageWidth, fontLoader.imageHeight));
            sprite.color = color;

            float yOffset = FontLoader.getYOffset(c, scale);

            Transform newTransform = new Transform(currentX, currentY - yOffset, character.width * scale, character.height * scale);
            GameObject gameObject = new GameObject(newTransform, sprite, zIndex);

            gameObject.setUIElement(isUIElement);
            if(isUIElement){
                gameObject.setTransform(gameObject.getTransform());
            }
            this.charObjects.add(gameObject);

            currentX += character.xAdvance * scale;
            totalWidth += character.xAdvance * scale;
            this.bottomEnd = Math.max(bottomEnd, yOffset);
            totalHeight = Math.max(totalHeight, character.height * scale + yOffset);
        }
    }

    public void markDirty(boolean isDirty){
        this.charObjects.forEach(gameObject -> gameObject.markDirty(isDirty));
    }
    
    public void setColor(Vector4f color){
        this.charObjects.forEach(gameObject -> gameObject.getSprite().color = color);
        markDirty(true);
    }

    public void addPos(Vector2f pos){
        this.charObjects.forEach(gameObject -> gameObject.getTransform().position.add(pos));
        markDirty(true);
    }

    public void addScale(float scale){
        this.charObjects.forEach(gameObject -> gameObject.getTransform().scale.x += scale);
        this.charObjects.forEach(gameObject -> gameObject.getTransform().scale.y += scale);
        markDirty(true);
    }

    public void addComponent(Component component){
        this.charObjects.forEach(gameObject -> gameObject.components.add(component));
    }

}
