package pokemon;

import entity.PokemonEntity;
import pokemon.pokemons.*;
import renderer.Sprite;
import renderer.Spritesheet;
import utils.Assets;
import utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Pokemons{
    
    public static List<Pokemon> pokemons = new ArrayList<>();

    private static List<Integer> noBackSprite = List.of(657, 660, 661, 662, 664, 666, 668, 669, 670, 671, 673, 676, 678, 679, 680, 681, 687, 689, 691, 693, 695, 696, 698, 699, 700, 705, 706, 709, 711, 713, 714, 715, 716, 717, 718, 719, 721);

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
    public static Pokemon get(PokemonEntity pokemon){return Pokemons.get(pokemon.id);}

    public static Sprite[] getPokemonSprite(Pokemon pokemon){
        return  Pokemons.getPokemonSprite(pokemon.id);
    }

    public static Sprite[] getPokemonSprite(int id){
        Sprite backSprite = backSpritesheet.sprites.get(id - 1);
        Sprite frontSprite = frontSpritesheet.sprites.get(id - 1);
        if(noBackSprite.contains(id)){
            backSprite = Utils.flip(frontSprite);
        }
        return new Sprite[]{frontSprite, backSprite};
    }

}

