package scenes;

import entity.PlayerEntity;
import game.Camera;
import org.joml.Vector2f;
import utils.Assets;

public class Map2Scene extends Scene{

    public Map2Scene() {
        super();
        camera = new Camera(new Vector2f(0, 0));
    }

    @Override
    public void init() {
        super.init();
//        player = new PlayerEntity(32, 48, new Vector2f(0, 0), 1, Assets.getTexture("assets/textures/playerDown.png"));
//        player.init();
//        TiledMapParser map = new TiledMapParser("assets/map/pokemonMap.tmx");
//        map.generateTiles(0, 0, 64, 64).forEach(gameObject ->{
//                if (gameObject.isCollidable) collidableTiles.add(gameObject);
//                else addGameObjectToRenderer(gameObject);
//            }
//        );
        addGameObjectToScene(player);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        this.renderer.render();
        player.update(dt);
        this.sceneGameObjects.forEach(gameObject -> gameObject.update(dt));
    }
}
