package game;

import game.components.Component;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Renderer;
import renderer.Sprite;
import renderer.Texture;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameObject {

    public Transform transform;
    public Sprite sprite;
    public int zIndex;
    private boolean isUIElement = false;

    public boolean isDirty = true;
    public boolean isCollidable = false;
    public boolean remove = false;

    public List<Component> components = new ArrayList<>();


    public GameObject(Transform transform, Sprite sprite){
        this(transform, sprite, 0, false);
    }

    public GameObject(Transform transform, Sprite sprite, boolean isUIElement){
        this(transform, sprite, 1, isUIElement);
    }

    public GameObject(Transform transform, Sprite sprite, int zIndex){
        this(transform, sprite, zIndex, false);
    }

    public GameObject(float x, float y, float width, float height, Sprite sprite){
        this(x, y, width, height, sprite, false);
    }

    public GameObject(float x, float y, float width, float height, Sprite sprite, boolean isUIElement){
        this(x, y, width, height, sprite, isUIElement, 1);
    }

    public GameObject(float x, float y, float width, float height, Sprite sprite, boolean isUIElement, int zIndex){
        this(new Transform(x, y, width, height), sprite, zIndex, isUIElement);
    }

    public GameObject(){

    }

    public GameObject(Transform transform, Sprite sprite, int zIndex, boolean isUIElement){
        this.sprite = sprite;
        this.zIndex = zIndex;
        this.isUIElement = isUIElement;
        this.sprite.width = transform.scale.x;
        this.sprite.height = transform.scale.y;
        this.transform = transform;
    }

    public void init(){
        this.markDirty(true);
    }

    public void update(float dt, Renderer renderer){
        for (Component component : components) {
            component.update(dt);
        }
    }

    public void tick(){

    }

    public Transform getTransform() {
        return transform;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public boolean isUIElement() {
        return isUIElement;
    }

    public Vector2f getPosition(){
        return this.transform.position;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
        this.markDirty(true);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.markDirty(true);
    }

    public void markDirty(boolean isDirty){
        this.isDirty = isDirty;
    }

    public void setUIElement(boolean UIElement) {
        this.isUIElement = UIElement;
    }

    public void setCollidable(boolean collidable) {
        isCollidable = collidable;
    }

    public void setPosition(Vector2f pos){
        this.transform.position = pos;
        this.markDirty(true);
    }
    public void setPosition(float x, float y){
        this.transform.position = new Vector2f(x, y);
        this.markDirty(true);
    }

    public void setTexture(Texture texture){
        this.sprite.texture = texture;
        this.markDirty(true);
    }

    public void setColor(Vector4f color){
        if(!this.sprite.color.equals(color)){
            this.sprite.color.set(color);
            this.markDirty(true);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameObject that = (GameObject) o;
        return isUIElement == that.isUIElement && zIndex == that.zIndex && isCollidable == that.isCollidable && transform.equals(that.transform) && Objects.equals(sprite, that.sprite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transform, sprite, isUIElement, zIndex, isCollidable);
    }
}
