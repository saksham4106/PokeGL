package scenes;

import entity.PlayerEntity;
import entity.PokemonEntity;
import events.EventBus;
import events.PokemonDespawnEvent;
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

public class BattleScene extends Scene {
    PokemonEntity pokemon;
    PlayerEntity fakePlayer;
    public BattleScene(PokemonEntity pokemonEntity, PlayerEntity player) {
        super();
        this.pokemon = pokemonEntity;
        this.fakePlayer = player;
        this.camera = new Camera(new Vector2f(0, 0));
        this.throwaway = true;
        this.sceneName = "battle";
    }

    @Override
    public void init() {
        this.setBackground(new Texture("assets/textures/battleBackground.png"));
        Sprite playerSprite = fakePlayer.getSprite();
        playerSprite.texture = fakePlayer.downSpriteSheet.texture;
        GameObject playerRender = new GameObject(new Transform(-135, -125, 64, 96),
                playerSprite, fakePlayer.zIndex, false);
        GameObject pokemonRender = new GameObject(new Transform(200, 85, 96 * 2, 96 * 2),
                pokemon.getSprite(), 10, false);

        ButtonObject defeatedButton = new ButtonObject(100, 100, "Defeat ",
                (button -> Window.setScene("main", null)));

        addGameObjectstoScene(defeatedButton);

        addGameObjectToScene(playerRender);
        addGameObjectToScene(pokemonRender);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        this.renderer.drawString("BATTLE!!! between " + fakePlayer.name + " and " + pokemon.name,
                -300, 100, 0.2f, Fonts.OPEN_SANS_FONT, new Vector4f(0, 0, 0, 1), false);

        this.renderer.render();

    }

    @Override
    public void destroy() {
        super.destroy();
        EventBus.invoke(new PokemonDespawnEvent());
        this.renderer.clear();
    }
}

