package scenes;

import entity.PlayerEntity;
import entity.PokemonEntity;
import fonts.Fonts;
import game.Window;
import pokemon.Pokemons;
import renderer.Sprite;
import ui.ButtonObject;
import ui.InputObject;
import utils.ColorUtils;
import utils.Utils;

public class StarterPokemonScene extends Scene {
    private final PlayerEntity player;
    public StarterPokemonScene(PlayerEntity player) {
        super();
        this.player = player;
        this.sceneName = "starterPokemon";
    }

    @Override
    public void init() {
        this.setBackground(ColorUtils.BLACK);
//        addGameObjectToScene(new ButtonObject(150, 350, "",  () -> setPokemon(1), Utils.resize(1.5f, Pokemons.getPokemonSprite(1)[0])));
//        addGameObjectToScene(new ButtonObject(275, 350, "",  () -> setPokemon(4), Utils.resize(1.5f, Pokemons.getPokemonSprite(4)[0])));
//        addGameObjectToScene(new ButtonObject(400, 350, "",  () -> setPokemon(7), Utils.resize(1.5f, Pokemons.getPokemonSprite(7)[0])));
//        addGameObjectToScene(new ButtonObject(525, 350, "",  () -> setPokemon(152), Utils.resize(1.5f, Pokemons.getPokemonSprite(152)[0])));
//        addGameObjectToScene(new ButtonObject(650, 350, "",  () -> setPokemon(155), Utils.resize(1.5f, Pokemons.getPokemonSprite(155)[0])));
//        addGameObjectToScene(new ButtonObject(775, 350, "",  () -> setPokemon(158), Utils.resize(1.5f, Pokemons.getPokemonSprite(158)[0])));
//        addGameObjectToScene(new ButtonObject(900, 350, "",  () -> setPokemon(252), Utils.resize(1.5f, Pokemons.getPokemonSprite(252)[0])));
//        addGameObjectToScene(new ButtonObject(1025, 350, "",  () -> setPokemon(255), Utils.resize(1.5f, Pokemons.getPokemonSprite(255)[0])));
//        addGameObjectToScene(new ButtonObject(1150,350, "",  () -> setPokemon(258), Utils.resize(1.5f, Pokemons.getPokemonSprite(258)[0])));
//        addGameObjectToScene(new ButtonObject(150, 200, "",  () -> setPokemon(387), Utils.resize(1.5f, Pokemons.getPokemonSprite(387)[0])));
//        addGameObjectToScene(new ButtonObject(250, 200, "",  () -> setPokemon(390), Utils.resize(1.5f, Pokemons.getPokemonSprite(390)[0])));
//        addGameObjectToScene(new ButtonObject(350, 200, "",  () -> setPokemon(393), Utils.resize(1.5f, Pokemons.getPokemonSprite(393)[0])));
//        addGameObjectToScene(new ButtonObject(500, 200, "",  () -> setPokemon(495), Utils.resize(1.5f, Pokemons.getPokemonSprite(495)[0])));
//        addGameObjectToScene(new ButtonObject(600, 200, "",  () -> setPokemon(498), Utils.resize(1.5f, Pokemons.getPokemonSprite(498)[0])));
//        addGameObjectToScene(new ButtonObject(700, 200, "",  () -> setPokemon(501), Utils.resize(1.5f, Pokemons.getPokemonSprite(501)[0])));
//        addGameObjectToScene(new ButtonObject(850, 200, "",  () -> setPokemon(650), Utils.resize(1.5f, Pokemons.getPokemonSprite(650)[0])));
//        addGameObjectToScene(new ButtonObject(950, 200, "",  () -> setPokemon(653), Utils.resize(1.5f, Pokemons.getPokemonSprite(653)[0])));
//        addGameObjectToScene(new ButtonObject(1050,200, "",  () -> setPokemon(656), Utils.resize(1.5f, Pokemons.getPokemonSprite(656)[0])));

        addGameObjectToScene(new ButtonObject(150, 300, "",  () -> setPokemon(1),   Utils.resize(100, 100, Pokemons.getPokemonSprite(1)[0])));
        addGameObjectToScene(new ButtonObject(250, 300, "",  () -> setPokemon(4),   Utils.resize(100, 100, Pokemons.getPokemonSprite(4)[0])));
        addGameObjectToScene(new ButtonObject(350, 300, "",  () -> setPokemon(7),   Utils.resize(100, 100, Pokemons.getPokemonSprite(7)[0])));
        addGameObjectToScene(new ButtonObject(500, 300, "",  () -> setPokemon(152), Utils.resize(100, 100, Pokemons.getPokemonSprite(152)[0])));
        addGameObjectToScene(new ButtonObject(600, 300, "",  () -> setPokemon(155), Utils.resize(100, 100, Pokemons.getPokemonSprite(155)[0])));
        addGameObjectToScene(new ButtonObject(700, 300, "",  () -> setPokemon(158), Utils.resize(100, 100, Pokemons.getPokemonSprite(158)[0])));
        addGameObjectToScene(new ButtonObject(850, 300, "",  () -> setPokemon(252), Utils.resize(100, 100, Pokemons.getPokemonSprite(252)[0])));
        addGameObjectToScene(new ButtonObject(950, 300, "",  () -> setPokemon(255), Utils.resize(100, 100, Pokemons.getPokemonSprite(255)[0])));
        addGameObjectToScene(new ButtonObject(1050,300, "",  () -> setPokemon(258), Utils.resize(100, 100, Pokemons.getPokemonSprite(258)[0])));
        addGameObjectToScene(new ButtonObject(150, 200, "",  () -> setPokemon(387), Utils.resize(100, 100, Pokemons.getPokemonSprite(387)[0])));
        addGameObjectToScene(new ButtonObject(250, 200, "",  () -> setPokemon(390), Utils.resize(100, 100, Pokemons.getPokemonSprite(390)[0])));
        addGameObjectToScene(new ButtonObject(350, 200, "",  () -> setPokemon(393), Utils.resize(100, 100, Pokemons.getPokemonSprite(393)[0])));
        addGameObjectToScene(new ButtonObject(500, 200, "",  () -> setPokemon(495), Utils.resize(100, 100, Pokemons.getPokemonSprite(495)[0])));
        addGameObjectToScene(new ButtonObject(600, 200, "",  () -> setPokemon(498), Utils.resize(100, 100, Pokemons.getPokemonSprite(498)[0])));
        addGameObjectToScene(new ButtonObject(700, 200, "",  () -> setPokemon(501), Utils.resize(100, 100, Pokemons.getPokemonSprite(501)[0])));
        addGameObjectToScene(new ButtonObject(850, 200, "",  () -> setPokemon(650), Utils.resize(100, 100, Pokemons.getPokemonSprite(650)[0])));
        addGameObjectToScene(new ButtonObject(950, 200, "",  () -> setPokemon(653), Utils.resize(100, 100, Pokemons.getPokemonSprite(653)[0])));
        addGameObjectToScene(new ButtonObject(1050,200, "",  () -> setPokemon(656), Utils.resize(100, 100, Pokemons.getPokemonSprite(656)[0])));


        //Last button doesn't render properly for some reason
        addGameObjectToScene(new ButtonObject(0, 0, "", () -> {}, new Sprite(0, 0, ColorUtils.getColor(0,0,0,0))));

    }

    private void setPokemon(int id){
        this.player.addPokemonToInventory(new PokemonEntity(Pokemons.get(id)));
        Window.setScene("main", null);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
//        renderer.drawString("Welcome to PokeGL!", -275, 100, 0.5f,
//                Fonts.ATARI_CLASSIC_FONT, ColorUtils.WHITE, false);
        renderer.drawString("Hello! Professor Oak here. To start your pokemon journey, select a starter pokemon", Window.width / 50f, Window.height/1.1f, 0.3f, Fonts.OPEN_SANS_FONT, ColorUtils.WHITE, false);
    }


}
