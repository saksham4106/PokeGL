package scenes;

import entity.PlayerEntity;
import entity.PokemonEntity;
import fonts.FontLoader;
import fonts.Fonts;
import game.GameObject;
import game.Window;
import org.joml.Vector4f;
import pokemon.Pokemons;
import renderer.Sprite;
import ui.ButtonObject;
import ui.TextObject;
import utils.Assets;
import utils.ColorUtils;
import utils.Utils;

import java.awt.Color;
import java.util.List;
import java.util.Objects;

public class SelectTeamScene extends Scene{
    private final PlayerEntity player;
    private final List<PokemonEntity> team;
    private PokemonEntity selectedPokemon;
    private final PokemonEntity targetPokemon;

    public SelectTeamScene(PlayerEntity player, PokemonEntity targetPokemon){
        super();
        this.targetPokemon= targetPokemon;
        this.player = player;
        this.team = player.poketeam;
        this.sceneName = "select_team";
        this.throwaway = true;
    }

    @Override
    public void init() {
        int y = 450;
        int x = 300;
        renderer.addText(new TextObject(450, 600, 1f, "Your Team" , Fonts.ATARI_CLASSIC_FONT, 3, true, ColorUtils.BLACK));
        for(PokemonEntity pokemon: team){
            addGameObjectToScene(new ButtonObject(x, y, () -> {selectedPokemon = pokemon; player.selectedPokemon = selectedPokemon;}, new Sprite(350, 100, ColorUtils.getColor(Color.MAGENTA)) , 2));
            addGameObjectToScene(new GameObject(x + 10, y + 10,90 , 90, Pokemons.getPokemonSprite(pokemon.id)[0], true));

            renderer.addText(new TextObject(x + 100, y + 80, 0.25f, Utils.capitalize(pokemon.name), Fonts.ATARI_CLASSIC_FONT, 3, true, ColorUtils.BLACK));
            renderer.addText(new TextObject(x + 100, y + 50, 0.12f, "HP: "+ pokemon.hp + "/" + pokemon.max_hp, Fonts.ATARI_CLASSIC_FONT, 3, true, ColorUtils.BLACK));
            addGameObjectToScene(new GameObject(x + 200, y + 50, 100, 10, new Sprite(0,0,new Vector4f(0.8f)), true));
            addGameObjectToScene(new GameObject(x + 200, y + 50, 100 * ((float) pokemon.hp / pokemon.max_hp), 10, new Sprite(0,0,ColorUtils.getColor(Color.GREEN)), true));
            renderer.addText(new TextObject(x + 100, y + 30, 0.2f, "level:  "+ pokemon.level, Fonts.ATARI_CLASSIC_FONT, 3, true, ColorUtils.BLACK));



            y -= 150;
            if(y <= 500 - (150 * 3)){
                x += 450;
                y = 450;
            }
        }

//        addGameObjectstoScene(new ButtonObject(10000, y, 100, 100, () -> {}, new Sprite(1, 1, new Vector4f(0, 0,0,0)), 2));

        this.setBackground(ColorUtils.WHITE);

        addGameObjectstoScene(new ButtonObject(0, 0, "Start Game ", () -> Window.setScene("wild_battle", new WildBattleScene(targetPokemon, player)) ));
    }

    @Override
    public void update(float dt) {
        renderer.drawString(selectedPokemon != null ? "Selected Pokemon: " + Utils.capitalize(selectedPokemon.name) : "Selected Pokemon: ", 100, 100, 0.3f,
                Fonts.ATARI_CLASSIC_FONT, ColorUtils.BLACK, false);
        super.update(dt);
    }
}
