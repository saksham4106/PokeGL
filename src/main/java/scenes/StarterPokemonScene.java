package scenes;

import entity.PlayerEntity;
import entity.PokemonEntity;
import fonts.Fonts;
import game.Window;
import pokemon.Pokemons;
import renderer.Sprite;
import ui.ButtonObject;
import utils.ColorUtils;

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
        addGameObjectToScene(new ButtonObject(150, 300, "",  () -> setPokemon(1), Sprite.resize(1.5f, Pokemons.getPokemonSprite(1)[0])));
        addGameObjectToScene(new ButtonObject(250, 300, "",  () -> setPokemon(4), Sprite.resize(1.5f, Pokemons.getPokemonSprite(4)[0])));
        addGameObjectToScene(new ButtonObject(350, 300, "",  () -> setPokemon(7), Sprite.resize(1.5f, Pokemons.getPokemonSprite(7)[0])));
        addGameObjectToScene(new ButtonObject(500, 300, "",  () -> setPokemon(152), Sprite.resize(1.5f, Pokemons.getPokemonSprite(152)[0])));
        addGameObjectToScene(new ButtonObject(600, 300, "",  () -> setPokemon(155), Sprite.resize(1.5f, Pokemons.getPokemonSprite(155)[0])));
        addGameObjectToScene(new ButtonObject(700, 300, "",  () -> setPokemon(158), Sprite.resize(1.5f, Pokemons.getPokemonSprite(158)[0])));
        addGameObjectToScene(new ButtonObject(850, 300, "",  () -> setPokemon(252), Sprite.resize(1.5f, Pokemons.getPokemonSprite(252)[0])));
        addGameObjectToScene(new ButtonObject(950, 300, "",  () -> setPokemon(255), Sprite.resize(1.5f, Pokemons.getPokemonSprite(255)[0])));
        addGameObjectToScene(new ButtonObject(1050,300, "",  () -> setPokemon(258), Sprite.resize(1.5f, Pokemons.getPokemonSprite(258)[0])));
        addGameObjectToScene(new ButtonObject(150, 200, "",  () -> setPokemon(387), Sprite.resize(1.5f, Pokemons.getPokemonSprite(387)[0])));
        addGameObjectToScene(new ButtonObject(250, 200, "",  () -> setPokemon(390), Sprite.resize(1.5f, Pokemons.getPokemonSprite(390)[0])));
        addGameObjectToScene(new ButtonObject(350, 200, "",  () -> setPokemon(393), Sprite.resize(1.5f, Pokemons.getPokemonSprite(393)[0])));
        addGameObjectToScene(new ButtonObject(500, 200, "",  () -> setPokemon(495), Sprite.resize(1.5f, Pokemons.getPokemonSprite(495)[0])));
        addGameObjectToScene(new ButtonObject(600, 200, "",  () -> setPokemon(498), Sprite.resize(1.5f, Pokemons.getPokemonSprite(498)[0])));
        addGameObjectToScene(new ButtonObject(700, 200, "",  () -> setPokemon(501), Sprite.resize(1.5f, Pokemons.getPokemonSprite(501)[0])));
        addGameObjectToScene(new ButtonObject(850, 200, "",  () -> setPokemon(650), Sprite.resize(1.5f, Pokemons.getPokemonSprite(650)[0])));
        addGameObjectToScene(new ButtonObject(950, 200, "",  () -> setPokemon(653), Sprite.resize(1.5f, Pokemons.getPokemonSprite(653)[0])));
        addGameObjectToScene(new ButtonObject(1050,200, "",  () -> setPokemon(656), Sprite.resize(1.5f, Pokemons.getPokemonSprite(656)[0])));

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
        renderer.drawString("select a starter pokemon to start the game!", -350, 100, 0.25f,
                Fonts.ATARI_CLASSIC_FONT, ColorUtils.WHITE, false);

    }


}
