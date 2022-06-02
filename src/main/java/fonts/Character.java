package fonts;

import org.joml.Vector2f;

public class Character {
    public int width;
    public int height;
    public int xAdvance;
    public int charID;
    public float x;
    public float y;
    public int yOffset;
    public int xOffset;

    public Character(int width, int height, int xAdvance, int charID, float x, float y, int yOffset, int xOffset) {
        this.width = width;
        this.height = height;
        this.xAdvance = xAdvance;
        this.charID = charID;
        this.x = x;
        this.y = y;
        this.yOffset = yOffset;
        this.xOffset = xOffset;
    }

    public Vector2f[] getTexCoords(int imageWidth, int imageHeight){
        return new Vector2f[]{
                new Vector2f(x / imageWidth, (y + height) / imageHeight),
                new Vector2f((x + width) / imageWidth, (y + height) / imageHeight),
                new Vector2f((x + width) / imageWidth, y / imageHeight),
                new Vector2f(x / imageWidth, y / imageHeight),
        };
    }

    @Override
    public String toString() {
        return "Character{" +
                "width=" + width +
                ", height=" + height +
                ", xAdvance=" + xAdvance +
                ", charID=" + charID +
                ", x=" + x +
                ", y=" + y +
                ", char=" + (char)charID +
                '}';
    }
}
