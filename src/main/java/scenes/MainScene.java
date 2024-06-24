package scenes;

import callback.KeyEventListener;
import entity.PlayerEntity;
import events.EventBus;
import fonts.Fonts;
import game.Camera;
import game.Window;
import org.joml.Vector2f;
import tiles.TiledMapParser;
import utils.Assets;
import utils.ColorUtils;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_1;
import java.awt.Color;

public class MainScene extends Scene {

    public World world;

    public MainScene() {
        super();
        this.camera = new Camera(new Vector2f(310, 140));
        this.sceneName = "main";
    }

    @Override
    public void init() {
        TiledMapParser map = new TiledMapParser("assets/map/Pellet Town.tmx");
        world = new World(50, 300, 48, 48, map);
        player = new PlayerEntity(32, 48, new Vector2f(1301, -675), 1,
                Assets.getTexture("assets/textures/playerDown.png"), world);

        EventBus.addListener(player);
        addGameObjectToScene(player);
    }


    private float counter = 0;
    private int fps;

    @Override
    public void update(float dt) {
        super.update(dt);

        counter += dt;
        if(counter >= 0.5f){
            fps = (int) (1 / dt);
            counter = 0;
        }
        this.renderer.drawString("FPS: " + fps, -395, 230, 0.15f, Fonts.ATARI_CLASSIC_FONT,
                ColorUtils.getColor(Color.BLACK), false);

        if(KeyEventListener.isKeyPressed(GLFW_KEY_1)){
            quitGame();
        }

        player.update(dt, renderer);
        renderer.render();
    }

    private void quitGame(){
        // save data
        Window.setScene("starting_menu", null);
    }
}
