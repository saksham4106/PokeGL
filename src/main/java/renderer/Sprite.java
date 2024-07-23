package renderer;

import org.joml.Vector2f;
import org.joml.Vector4f;

public class Sprite {

    public float width, height;
    public Texture texture;
    public Vector4f color;

    private Vector2f[] texCoords = new Vector2f[]{
                new Vector2f(0, 1),
                new Vector2f(1, 1),
                new Vector2f(1, 0),
                new Vector2f(0, 0)
    };

    public Sprite(Sprite sprite){
        this.width = sprite.width;
        this.height = sprite.height;
        this.texture = sprite.texture;
        this.color = sprite.color;
        this.texCoords = sprite.texCoords;
    }

    public Sprite(float width, float height, Texture texture, Vector4f color){
        this.width = width;
        this.height = height;
        this.texture = texture;
        this.color = color;
    }

    public Sprite(Texture texture){
        this(0, 0, texture);
    }
    public Sprite(Vector4f color){
        this(0,0, color);
    }

    public Sprite(float width, float height, Vector4f color){
        this(width, height, null, color);
    }

    public Sprite(float width, float height, Texture texture){
        this(width, height, texture, new Vector4f(1, 1, 1, 1));
    }


    public void setTexCoords(Vector2f[] texCoords) {
        this.texCoords = texCoords;
    }

    /*
     * @param texCoord The texture coordinates of the bottom left corner
     */
    public void generateTexCoord(Vector2f texCoord) {
        this.texCoords = new Vector2f[]{
                new Vector2f(texCoord.x, texCoord.y + height / (float)this.texture.getHeight()),
                new Vector2f(texCoord.x + width / (float)this.texture.getWidth(), texCoord.y + height / (float)this.texture.getHeight()),
                new Vector2f(texCoord.x + width / (float)this.texture.getWidth(), texCoord.y),
                new Vector2f(texCoord.x, texCoord.y),

        };
    }

    public Vector2f[] getTexCoords() {
        return texCoords;
    }

    public static Sprite resize(float scale, Sprite sprite){
        Sprite output = new Sprite(sprite);
        output.width *= scale;
        output.height *= scale;
        return output;
    }


}

