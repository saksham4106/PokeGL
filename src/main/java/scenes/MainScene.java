package scenes;

import entity.PlayerEntity;
import events.EventBus;
import game.Camera;
import org.joml.Vector2f;
import tiles.TiledMapParser;
import utils.Assets;

public class MainScene extends Scene {

    public World world;

    public MainScene() {
        super();
        camera = new Camera(new Vector2f(310, 140));
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

    @Override
    public void update(float dt) {
        super.update(dt);
//        this.renderer.drawString("FPS:\t" + Window.fps, -380, 230, 0.2f, Fonts.LEAGUE_SPARTA_FONT,
//                ColorUtils.getColor(Color.BLACK), false);
        player.update(dt);
        renderer.render();
    }

    @Override
    public void destroy() {
        this.renderer.clear();
    }
}
