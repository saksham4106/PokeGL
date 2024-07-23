package game;

import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Renderer;
import renderer.Sprite;
import renderer.Texture;
import utils.MathUtil;

import java.util.Objects;

public class GameObject {
    protected Transform transform;
    protected Sprite sprite;
    private boolean isDirty;
    private boolean isUIElement = false;
    public int zIndex;
    public boolean isCollidable = false;

    public GameObject(Transform transform, Sprite sprite, int zIndex, boolean isUIElement){
        this.sprite = sprite;
        this.zIndex = zIndex;
        this.isUIElement = isUIElement;
        this.sprite.width = transform.scale.x;
        this.sprite.height = transform.scale.y;

        this.setTransform(transform);
        this.markDirty(true);
    }

    public GameObject(Transform transform, Sprite sprite){
        this(transform, sprite, 0, false);
    }

    public GameObject(Transform transform, Sprite sprite, boolean isUIElement){
        this(transform, sprite, 100, isUIElement);
    }

    public GameObject(Transform transform, Sprite sprite, int zIndex){
        this(transform, sprite, zIndex, false);
    }

    public GameObject(float x, float y, float width, float height, Sprite sprite){
        this(new Transform(x,y,width,height), sprite);
    }

    public GameObject(float x, float y, float width, float height, Sprite sprite, boolean isUIElement){
        this(new Transform(x,y,width,height), sprite, isUIElement);
        this.sprite.width = width;
        this.sprite.height = height;
        this.markDirty(true);
    }

    public GameObject(GameObject go){
        this(go.transform, go.sprite, go.zIndex);
    }

    public GameObject(){
        this.markDirty(true);
        this.zIndex = 0;
    }

    public void init(){
        this.markDirty(true);

    }

    public void update(float dt, Renderer renderer){

    }

    public void tick(){

    }

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = this.isUIElement() ? MathUtil.normalizeTransform(transform) : transform;
        this.markDirty(true);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.markDirty(true);
    }

    public void markDirty(boolean isDirty){
        this.isDirty = isDirty;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public boolean isUIElement() {
        return isUIElement;
    }

    /*
      *   If Game Object is set as a UI Element, the coordinates have to be inside Screen bounds
      *   no point in rendering an element that is supposed to move with the camera out of camera's bounds.
     */
    public void setUIElement(boolean UIElement) {
        this.isUIElement = UIElement;
    }


    public void setCollidable(boolean collidable) {
        isCollidable = collidable;
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

    public void setPosition(float x, float y){
        this.transform.position = new Vector2f(x, y);
        this.markDirty(true);
    }

    public void setTexture(Texture texture){
        this.sprite.texture = texture;
        this.markDirty(true);
    }

    public void setColor(Vector4f color){
        this.sprite.color.set(color);
        this.markDirty(true);
    }
}
