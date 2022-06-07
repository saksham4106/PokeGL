package scenes;

import entity.PlayerEntity;
import game.Camera;
import game.GameObject;
import game.Transform;
import game.Window;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Renderer;
import renderer.Sprite;
import renderer.Texture;
import ui.ButtonObject;

import java.util.*;

public abstract class Scene {

    public Set<GameObject> sceneGameObjects;
    public static PlayerEntity player;

    protected Renderer renderer;
    public Camera camera = new Camera(new Vector2f(0, 0));

    public Scene(){

        this.renderer = new Renderer();
        this.sceneGameObjects = new HashSet<>();
    }

    public void addGameObjectToScene(GameObject gameObject){
        if(gameObject instanceof ButtonObject buttonObject){
            this.renderer.addButton(buttonObject);
        }
        else{
            this.renderer.add(gameObject);
        }
        this.sceneGameObjects.add(gameObject);
    }

    public void removeGameObjectFromScene(GameObject gameObject){
        if(this.sceneGameObjects.remove(gameObject)) {
            this.renderer.removeGameObject(gameObject);
        }
    }


    public boolean containsGameObject(GameObject gameObject){
        return this.sceneGameObjects.contains(gameObject);
    }

    public void init(){
    }

    /*
    For each loop will cause ConcurrentModificationException, Shut up Intellij.
    (Update method of a gameObject might modify the list sceneGameObjects)
     */
    @SuppressWarnings("ForLoopReplaceableByForEach")
    public void update(float dt){
        GameObject[] gameObjects = sceneGameObjects.toArray(new GameObject[0]);
        for (int i = 0; i < gameObjects.length; i++) {
            gameObjects[i].update(dt);
        }

    }

    public void destroy(){
    }

    /*
    For each loop will cause ConcurrentModificationException, Shut up Intellij.
    (Tick method of a gameObject might modify the list sceneGameObjects)
     */
    @SuppressWarnings("ForLoopReplaceableByForEach")
    public void tick(){
        GameObject[] gameObjects = sceneGameObjects.toArray(new GameObject[0]);
        for (int i = 0; i < gameObjects.length; i++) {
            gameObjects[i].tick();
        }
    }

    public void setBackground(Texture texture){
        this.renderer.add(new GameObject(new Transform(0, 0, Window.width, Window.height),
                new Sprite(Window.width, Window.height, texture), 0, true));
    }

    public void setBackground(Vector4f color){
        this.renderer.add(new GameObject(new Transform(0, 0, Window.width, Window.height),
                new Sprite(Window.width, Window.height, color), 0, true));
    }



}
