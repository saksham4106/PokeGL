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
    private Set<GameObject> removeableGameObjects;
    public static PlayerEntity player;

    protected Renderer renderer;
    public Camera camera = new Camera(new Vector2f(0, 0));

    public boolean throwaway = false;
    public String sceneName;


    public Scene(){

        this.renderer = new Renderer();
        this.sceneGameObjects = new HashSet<>();
        this.removeableGameObjects = new HashSet<>();
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

    public void addGameObjectstoScene(GameObject... gameObjects){
        for (GameObject gameObject : gameObjects) {
            this.addGameObjectToScene(gameObject);
        }
    }

    public void removeGameObjectFromScene(GameObject gameObject){
        removeableGameObjects.add(gameObject);
    }


    public boolean containsGameObject(GameObject gameObject){
        return this.sceneGameObjects.contains(gameObject);
    }

    public void init(){
    }

    public void update(float dt){
        Iterator<GameObject> gameObjectIterator = sceneGameObjects.iterator();
        while(gameObjectIterator.hasNext()){
            GameObject gameObject = gameObjectIterator.next();
            gameObject.update(dt, this.renderer);
            if(removeableGameObjects.contains(gameObject)){
                gameObjectIterator.remove();
                removeableGameObjects.remove(gameObject);
                this.renderer.removeGameObject(gameObject);
            }
        }

//        GameObject[] gameObjects = sceneGameObjects.toArray(new GameObject[0]);
//        for (int i = 0; i < gameObjects.length; i++) {
//            gameObjects[i].update(dt);
//        }
    }

    public void destroy(){
        if(throwaway) Window.removeScene(this.sceneName);
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
