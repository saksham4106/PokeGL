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

        for (int spriteID = 0; spriteID < numSprites; spriteID++) {
            Sprite sprite = new Sprite(spriteWidth, spriteHeight, texture);
            float bottom_left_tx = spriteID * spriteWidth / (float) texture.getWidth();
            sprite.setTexCoords(new Vector2f(bottom_left_tx, 0));
            sprites.add(sprite);

        }

    }
}
