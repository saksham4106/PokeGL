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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarOutputStream;

public class SelectTeamScene extends Scene{
    private final PlayerEntity player;
    private final List<PokemonEntity> team;
    private final PokemonEntity targetPokemon;

    public SelectTeamScene(PlayerEntity player, PokemonEntity targetPokemon){
        super();
        this.targetPokemon= targetPokemon;
        this.player = player;
        this.team = player.poketeam;
        this.sceneName = "select_team";
        this.throwaway = true;
    }

    List<GameObject> borders = new ArrayList<>();

    @Override
    public void init() {
        int y = 450;
        int x = 300;

        addGameObjectToScene(new GameObject(200, 10, 970, 680, new Sprite(Assets.getTexture("assets/scene/select_team/bg.png")), true, 1));
        renderer.addText(new TextObject(410, 600, 1f, "Your Team" , Fonts.ATARI_CLASSIC_FONT, 3, true, ColorUtils.BLACK));

        // Select first pokemon by default
        GameObject first_b = new GameObject(x + 10, y + 7, 100 - 12, 100 - 15, new Sprite(Assets.getTexture("assets/scene/select_team/border.png")), true, 4);
        borders.add(first_b);
        addGameObjectToScene(first_b);
        player.selectedPokemon = team.get(0);

        // Display each pokemon
        for(PokemonEntity pokemon: team){
            int finalX = x;
            int finalY = y;

            ButtonObject b = new ButtonObject(x, y, () -> {
                // add border to selected pokemon and remove from the previous ones.
                GameObject border = new GameObject(finalX + 10, finalY + 7, 100 - 12, 100 - 15, new Sprite(Assets.getTexture("assets/scene/select_team/border.png")), true, 4);
                for(GameObject bd: borders){
                    this.removeGameObjectFromScene(bd);
                }
                this.borders.add(border);
                addGameObjectToScene(border);
                player.selectedPokemon = pokemon;
                }, new Sprite(350, 100, Assets.getTexture("assets/scene/select_team/pok_bg.png")) , 2);

            if(pokemon.hp == 0) b.disable(true);
            addGameObjectToScene(b);
            addGameObjectToScene(new GameObject(x + 10, y + 10,90 , 90, Pokemons.getPokemonSprite(pokemon.id)[0], true, 3));

            renderer.addText(new TextObject(x + 105, y + 77, 0.25f, Utils.capitalize(pokemon.name), Fonts.ATARI_CLASSIC_FONT, 3, true, ColorUtils.BLACK));
            // HP
            renderer.addText(new TextObject(x + 105, y + 50, 0.12f, "HP: "+ pokemon.hp + "/" + pokemon.max_hp, Fonts.ATARI_CLASSIC_FONT, 3, true, ColorUtils.BLACK));
            addGameObjectToScene(new GameObject(x + 200 - 2, y + 50 -2, 100 + 4, 10 + 4, new Sprite(0,0, ColorUtils.BLACK), true));
            addGameObjectToScene(new GameObject(x + 200, y + 50, 100, 10, new Sprite(0,0,new Vector4f(0.8f)), true));
            addGameObjectToScene(new GameObject(x + 200, y + 50, 100 * ((float) pokemon.hp / pokemon.max_hp), 10, new Sprite(0,0,ColorUtils.getColor(Color.GREEN)), true));

            renderer.addText(new TextObject(x + 105, y + 30, 0.2f, "level:  "+ pokemon.level, Fonts.ATARI_CLASSIC_FONT, 3, true, ColorUtils.BLACK));



            y -= 150;
            if(y <= 500 - (150 * 3)){
                x += 450;
                y = 450;
            }
        }

//        addGameObjectstoScene(new ButtonObject(10000, y, 100, 100, () -> {}, new Sprite(1, 1, new Vector4f(0, 0,0,0)), 2));

        this.setBackground(ColorUtils.getColor(199, 107, 59));

        addGameObjectstoScene(new ButtonObject(400, 70, "Proceed to battle", 0.5f, () -> Window.setScene("wild_battle", new WildBattleScene(targetPokemon, player)),
                new Sprite(new Vector4f(0.7f, 0.7f, 0.7f,0.2f)), 2, Fonts.ATARI_CLASSIC_FONT, ColorUtils.BLACK));
    }

    @Override
    public void update(float dt) {
//        renderer.drawString(selectedPokemon != null ? "Selected Pokemon: " + Utils.capitalize(player.selectedPokemon.name) : "Selected Pokemon: ", 300, 100, 0.3f,
//                Fonts.ATARI_CLASSIC_FONT, ColorUtils.BLACK, false);
        super.update(dt);
    }
}
