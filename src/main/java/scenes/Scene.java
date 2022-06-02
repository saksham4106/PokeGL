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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Scene {

    public List<GameObject> sceneGameObjects;
    public Set<GameObject> uniqueGameObjects;
    public static PlayerEntity player;

    protected Renderer renderer;
    public Camera camera = new Camera(new Vector2f(0, 0));

    public Scene(){
        this.renderer = new Renderer();
        this.sceneGameObjects = new ArrayList<>();
        this.uniqueGameObjects = new HashSet<>();
    }

    public void addGameObjectToScene(GameObject gameObject){
        if(gameObject instanceof ButtonObject buttonObject){
            this.renderer.addButton(buttonObject);
        }
        else{
            this.renderer.add(gameObject);
        }
        this.sceneGameObjects.add(gameObject);
        this.uniqueGameObjects.add(gameObject);
    }

    public void removeGameObjectFromScene(GameObject gameObject){
        if(this.containsGameObject(gameObject)){
            this.sceneGameObjects.remove(gameObject);
            this.uniqueGameObjects.remove(gameObject);
            this.renderer.removeGameObject(gameObject);
        }
    }


    public boolean containsGameObject(GameObject gameObject){
        return this.uniqueGameObjects.contains(gameObject);
    }

    public void init(){
    }

    /*
    For each loop will cause ConcurrentModificationException, Shut up Intellij.
    (Update method of a gameObject might modify the list sceneGameObjects)
     */
    @SuppressWarnings("ForLoopReplaceableByForEach")
    public void update(float dt){
        for(int i = 0; i < this.sceneGameObjects.size(); i++){
            this.sceneGameObjects.get(i).update(dt);
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
        for(int i = 0; i < this.sceneGameObjects.size(); i++){
            this.sceneGameObjects.get(i).tick();
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
