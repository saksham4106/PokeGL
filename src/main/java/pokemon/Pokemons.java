package pokemon;

import pokemon.pokemons.*;
import renderer.Sprite;
import renderer.Spritesheet;
import renderer.Texture;
import utils.Assets;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Pokemons{
    
    public static List<Pokemon> pokemons = new ArrayList<>();

    private static final Spritesheet frontSpritesheet = Assets.getSpritesheet("assets/frontFaces.png",
            new Spritesheet(Assets.getTexture("assets/frontFaces.png"), 96, 96, 721));
    private static final Spritesheet backSpritesheet = Assets.getSpritesheet("assets/backFaces.png",
            new Spritesheet(Assets.getTexture("assets/backFaces.png"), 96, 96, 721));

    public static void init(){
        pokemons.addAll(KantoPokemon.kantoPokemons);
        pokemons.addAll(JohtoPokemon.johtoPokemons);
        pokemons.addAll(HoennPokemon.hoennPokemons);
        pokemons.addAll(SinnohPokemon.sinnohPokemons);
        pokemons.addAll(UnovaPokemon.unovaPokemons);
        pokemons.addAll(KalosPokemon.kalosPokemons);
        pokemons.sort(Comparator.comparingInt(a -> a.id));
    }

    public static Pokemon get(int id){
        return pokemons.get(id - 1);
    }

    public static Sprite[] getPokemonSprite(Pokemon pokemon){
        return new Sprite[]{frontSpritesheet.sprites.get(pokemon.id - 1), backSpritesheet.sprites.get(pokemon.id - 1)};
    }
    public static Sprite[] getPokemonSprite(int id){
        return new Sprite[]{frontSpritesheet.sprites.get(id - 1), backSpritesheet.sprites.get(id - 1)};
    }

}

