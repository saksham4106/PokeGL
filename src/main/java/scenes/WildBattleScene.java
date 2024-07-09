package scenes;

import callback.KeyEventListener;
import entity.EntityFacing;
import entity.PlayerEntity;
import entity.PokemonEntity;
import fonts.Fonts;
import game.Camera;
import game.GameObject;
import game.Transform;
import game.Window;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Sprite;
import renderer.Texture;
import ui.ButtonObject;
import utils.ColorUtils;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_F;

public class WildBattleScene extends Scene {
    PokemonEntity pokemon;
    PlayerEntity player;
    GameObject hpBar;

    public WildBattleScene(PokemonEntity pokemonEntity, PlayerEntity player) {
        super();
        this.pokemon = pokemonEntity;
        this.player = player;
        this.camera = new Camera(new Vector2f(0, 0));
        this.throwaway = true;
        this.sceneName = "wild_battle";
    }

    @Override
    public void init() {
        this.setBackground(new Texture("assets/textures/battleBackground.png"));

        player.switchFacing(EntityFacing.DOWN);

        Sprite playerSprite = new Sprite(player.getSprite());
        playerSprite.texture = player.downSpriteSheet.texture;
        GameObject playerRender = new GameObject(new Transform(-135, -125, 64, 96), playerSprite, player.zIndex, false);

        GameObject pokemonRender = new GameObject(new Transform(200, 70, 96 * 2, 96 * 2),
                pokemon.getSprite(), 10, false);

        ButtonObject defeatedButton = new ButtonObject(100, 500, "Resign ",
                () -> Window.setScene("main", new MainScene()));

        GameObject hp_bg = new GameObject(new Transform(250, 100, 100, 10),
                new Sprite(100, 10, new Vector4f(0.9f, 0.9f,0.9f, 1)), 15);
        hpBar = new GameObject(new Transform(250, 100, 100 * ((float) pokemon.hp / pokemon.max_hp), 10),
                new Sprite(100, 10, new Vector4f(0f, 0.9f,0f, 1)), 16);

        addGameObjectstoScene(hp_bg, hpBar);
        addGameObjectstoScene(defeatedButton);
        addGameObjectToScene(playerRender);
        addGameObjectToScene(pokemonRender);
    }

    @Override
    public void tick() {
        super.tick();
        if(KeyEventListener.isKeyPressed(GLFW_KEY_F) && this.pokemon.hp > 0){
            this.pokemon.hp -= 1;
            this.hpBar.setTransform(new Transform(250, 100, 100 * ((float) pokemon.hp / pokemon.max_hp), 10));
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        this.renderer.drawString("BATTLE!!! between " + player.name + " and " + pokemon.name + "," + pokemon.level,
                -300, 100, 0.2f, Fonts.LEAGUE_SPARTA_FONT, new Vector4f(0, 0, 0, 1), false);

        this.renderer.drawString("HP: " + pokemon.hp + "/" + pokemon.max_hp,
                200, 112, 0.1f, Fonts.LEAGUE_SPARTA_FONT, ColorUtils.WHITE, false);

    }

    @Override
    public void destroy() {
        super.destroy();
        player.removeTarget();
        this.renderer.clear();
    }
}

