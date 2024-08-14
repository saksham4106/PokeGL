package scenes;

import callback.KeyEventListener;
import entity.PlayerEntity;
import events.EventBus;
import fonts.Fonts;
import game.Window;
import org.joml.Vector2f;
import pokemon.Pokemons;
import serialization.LoadGame;
import tiles.TiledMapParser;
import utils.Assets;
import utils.ColorUtils;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_1;
import java.awt.Color;

public class MainScene extends Scene {

    public World world;

    public MainScene() {
        super();
        this.sceneName = "main";
    }

    @Override
    public void init() {
        TiledMapParser map = new TiledMapParser("assets/map/Pellet Town.tmx");
        Pokemons.init();
        world = new World(0, 0, 48, 48, map);


        // load player
        player = new PlayerEntity("Saksham4106", 32, 48, new Vector2f(1250, -900), 1,
                Assets.getTexture("assets/textures/playerDown.png"), world);
        player.poke_balls = 20;
        player.ultra_balls = 5;
        player.master_balls = 1;
        player.super_balls = 5;
        PlayerEntity p = LoadGame.loadPlayer(player);
        if(p != null){
            player = p;
        }
        player.init();



        if(player.pokemons.isEmpty()){
            Window.setScene("starterPokemon", new StarterPokemonScene(player));
        }

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
        this.renderer.drawString("FPS: " + fps, 5, Window.height - 20, 0.15f, Fonts.ATARI_CLASSIC_FONT,
                ColorUtils.getColor(Color.BLACK), false);

        if(KeyEventListener.isKeyPressed(GLFW_KEY_1)){
            quitGame();
        }

        player.update(dt, renderer);
    }

    private void quitGame(){
        // save data
        Window.setScene("starting_menu", null);
    }
}
