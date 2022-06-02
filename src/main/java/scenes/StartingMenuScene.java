package scenes;


import fonts.Fonts;
import game.Camera;
import game.Window;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Sprite;
import renderer.Texture;
import ui.ButtonObject;

public class StartingMenuScene extends Scene {

    public StartingMenuScene() {
        super();
        camera = new Camera(new Vector2f(10, 10));
    }

    public void init() {

        ButtonObject buttonObject = new ButtonObject(100, 100, "Scene 1", button -> Window.setScene(new MainScene()),
                new Sprite(300, 200, new Texture("assets/textures/Scene1.png")));


        this.addGameObjectToScene(buttonObject);

//        ButtonObject playScene1Button = new ButtonObject(-300, -100, new Text("Scene 1", Fonts.LEAGUE_SPARTA_FONT, new Vector4f(1, 1, 1, 1), 0.15f), button -> {
//            Window.setScene(new MainScene());
////            Sound sound = Assets.getSound("assets/sounds/assets_sounds_stage_clear.ogg", new Sound("assets/sounds/assets_sounds_stage_clear.ogg", false));
////            sound.play();
//        }, 10, new Sprite(150, 100, Assets.getTexture("assets/textures/Scene1.png"), new Vector4f(0.7f, 0.7f, 0.7f, 1)));
//
//        ButtonObject playScene2Button = new ButtonObject(0, 0, new Text("Scene 2", Fonts.LEAGUE_SPARTA_FONT,
//                new Vector4f(1, 1, 1, 1), 0.15f), button -> Window.setScene(new Map2Scene()), 10,
//                new Sprite(150, 100, Assets.getTexture("assets/textures/Scene2.png"), new Vector4f(0.7f, 0.7f, 0.7f, 1)), true);
//
//
//        GameObject gameObject = new GameObject(new Transform(0, 0, 100, 100), new Sprite(
//                100, 100, new Vector4f(1, 1, 1, 1)
//        ), true);
//        addGameObjectToScene(gameObject);
//        TextObject textObject = new TextObject(0, 0, 1, "Hello", Fonts.OPEN_SANS_FONT,
//                11, true, new Vector4f(1, 1, 1, 1));
//        this.renderer.addText(textObject);
//        this.addGameObjectToScene(playScene1Button);
//        this.addGameObjectToScene(playScene2Button);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        this.renderer.drawString("Pokemon Game!", -300, 100, 0.7f, Fonts.LEAGUE_SPARTA_FONT,
                new Vector4f(1, 0, 1, 1), false);
        this.renderer.render();

    }

    @Override
    public void destroy() {
        this.renderer.clear();
    }
}
