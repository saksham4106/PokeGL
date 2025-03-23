package game;

import org.joml.Vector2f;

import java.util.Objects;

public class Transform {
    public Vector2f position;
    public Vector2f scale;


    public Transform(Vector2f coords, float width, float height){
        this(coords, new Vector2f(width, height));
    }

    public Transform(float x, float y, Vector2f scale){
        this(new Vector2f(x, y), scale);
    }

    public Transform(Transform transform){
        this(transform.position, transform.scale);
    }

    public Transform(float x, float y, float width, float height){
        this(new Vector2f(x, y), new Vector2f(width, height));
    }

    /**
     * @param position  coordinate of the bottom left corner
     */
    public Transform(Vector2f position, Vector2f scale){
        this.position = position;
        this.scale = scale;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transform transform = (Transform) o;
        return Objects.equals(position, transform.position) && Objects.equals(scale, transform.scale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, scale);
    }
}
