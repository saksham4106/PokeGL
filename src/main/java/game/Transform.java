package game;

import org.joml.Vector2f;

public class Transform {
    public Vector2f position;
    public Vector2f scale;

    public Transform(Vector2f position, Vector2f scale){
        this.position = position;
        this.scale = scale;
    }

    public Transform(Transform transform){
        this.position = transform.position;
        this.scale = transform.scale;
    }

    /**
     *
     * @param x  X Coordinate of the bottom left corner
     * @param y  Y Coordinate of the bottom left corner
     */
    public Transform(float x, float y, float width, float height){
        this.position = new Vector2f(x, y);
        this.scale = new Vector2f(width, height);
    }

    public Transform(Vector2f coords, float width, float height){
        this(coords, new Vector2f(width, height));
    }

    public Transform(float x, float y, Vector2f scale){
        this(new Vector2f(x, y), scale);
    }


    public Vector2f getCenter(){
        return new Vector2f(this.position.x + (this.scale.x / 2), this.position.y + (this.scale.y / 2));
    }

    @Override
    public String toString() {
        return "Transform{" +
                "position=" + position +
                ", scale=" + scale +
                '}';
    }
}
