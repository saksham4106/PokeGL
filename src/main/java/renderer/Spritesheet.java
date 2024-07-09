package renderer;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Spritesheet {
    public Texture texture;
    public List<Sprite> sprites;

    public Spritesheet(Texture texture, int spriteHeight, int spriteWidth, int numSprites) {
        this.sprites = new ArrayList<>();
        this.texture = texture;

        float x = 0;
        float y = 0;

        for (int spriteID = 0; spriteID < numSprites; spriteID++) {
            Sprite sprite = new Sprite(spriteWidth, spriteHeight, texture);
            sprite.generateTexCoord(new Vector2f(x, y));
            sprites.add(sprite);

            x += spriteWidth / (float) texture.getWidth();
            if(x >= 1){
                x = 0;
                y += (spriteHeight / (float) texture.getHeight());
            }
        }

    }
}
