package scenes;

import entity.PlayerEntity;
import entity.PokemonEntity;
import fonts.Fonts;
import game.Camera;
import game.GameObject;
import game.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Sprite;
import renderer.Texture;

public class BattleScene extends Scene {
    PokemonEntity pokemon;
    PlayerEntity player;
    public BattleScene(PokemonEntity pokemonEntity, PlayerEntity player) {
        super();
        this.pokemon = pokemonEntity;
        this.player = player;
        camera = new Camera(new Vector2f(0, 0));
    }

    @Override
    public void init() {
        this.setBackground(new Texture("assets/textures/battleBackground.png"));
        Sprite playerSprite = player.getSprite();
        playerSprite.texture = player.downSpriteSheet.texture;
        GameObject playerRender = new GameObject(new Transform(-135, -125, 64, 96),
                playerSprite, player.zIndex, false);
        GameObject pokemonRender = new GameObject(new Transform(200, 110, 96, 96),
                pokemon.getSprite(), pokemon.zIndex, false);

        addGameObjectToScene(playerRender);
        addGameObjectToScene(pokemonRender);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        this.renderer.drawString("BATTLE!!! between " + player.name + " and " + pokemon.name,
                -300, 100, 0.2f, Fonts.OPEN_SANS_FONT, new Vector4f(0, 0, 0, 1), false);

        this.renderer.render();

    }


}
