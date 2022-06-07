package entity.pokemon;


import entity.PokemonEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static entity.pokemon.PokemonTypes.*;

public class Pokemons {

    public static final PokemonEntity PIKACHU = new PokemonEntity("pikachu", ELECTRIC);
    public static final PokemonEntity BULBASAUR = new PokemonEntity("bulbasaur", GRASS);
    public static final PokemonEntity SQUIRTLE = new PokemonEntity("squirtle", WATER);
    public static final PokemonEntity CHARMANDER = new PokemonEntity("charmander", FIRE);

    public static final List<PokemonEntity> pokemons = new ArrayList<>();

    static {
        Arrays.stream(Pokemons.class.getDeclaredFields()).forEach(field -> {
            if(field.getType().isAssignableFrom(PokemonEntity.class)){
                try {
                    pokemons.add((PokemonEntity) field.get(null));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
