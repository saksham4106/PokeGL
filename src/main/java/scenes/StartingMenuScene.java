package scenes;


import game.Camera;
import game.GameObject;
import game.Transform;
import game.Window;
import org.joml.Vector2f;
import org.joml.Vector4f;
import pokemon.Pokemons;
import renderer.Sprite;
import ui.ButtonObject;
import ui.InputObject;
import utils.Assets;
import utils.ColorUtils;


public class StartingMenuScene extends Scene {

    public StartingMenuScene() {
        super();
        this.camera = new Camera(new Vector2f(10, 10));
        this.sceneName = "starting_menu";
    }

    public void init() {

        ButtonObject enter_game = new ButtonObject(570, 250, "",
                () -> Window.setScene("main", new MainScene()),
                new Sprite(300, 100, Assets.getTexture("assets/scene/starting_menu/enter_game.png")));


        GameObject heading1 = new GameObject(new Transform(-250, 100, 600, 100),
                new Sprite(1000, 100, Assets.getTexture("assets/scene/starting_menu/heading.png")), 100);

        addGameObjectToScene(heading1);
        addGameObjectToScene(enter_game);

        this.setBackground(Assets.getTexture("assets/textures/Scene1.png"));

    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }
}
