package world;

import callback.KeyEventListener;
import entity.EntityFacing;
import game.Camera;
import game.GameObject;
import org.joml.Vector2f;
import renderer.Sprite;
import scenes.Scene;
import scenes.World;
import tiles.TilePosition;
import utils.ColorUtils;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;

public class WorldScene extends Scene {

    Map<TilePosition, GameObject> world = new HashMap<>();


    public WorldScene() {
//        this.camera = new Camera(new Vector2f(0,0));
        this.sceneName = "world";
    }

    @Override
    public void init() {
        super.init();
        SimplexNoise noise = new SimplexNoise();
        initialiseStartingWorld(noise);
    }

    float zoom = 10;
    public void initialiseStartingWorld(SimplexNoise noise){
        for(int i = -25; i < 25; i++){
            for(int j = -25; j < 25; j++){
                TilePosition pos = new TilePosition(i, j, 0);
                float f = noise.noise(i / zoom, j / zoom);
                f = (f + 1) / 2;
                GameObject go = new GameObject(i * 5,j*5, 5, 5, new Sprite(ColorUtils.getColor(f)));
                addGameObjectToScene(go);
                world.put(pos, go);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

    }

    @Override
    public void update(float dt) {
        super.update(dt);

        Vector2f velocity = new Vector2f();

        if(KeyEventListener.isKeyPressed(GLFW_KEY_W)){
            velocity.y += 1;

        }
        if(KeyEventListener.isKeyPressed(GLFW_KEY_S)){
            velocity.y -= 1;

        }
        if(KeyEventListener.isKeyPressed(GLFW_KEY_A)){
            velocity.x -= 1;

        }
        if(KeyEventListener.isKeyPressed(GLFW_KEY_D)){
            velocity.x += 1;
        }

        this.camera.position.add(velocity.mul(100 * dt));
    }
}
