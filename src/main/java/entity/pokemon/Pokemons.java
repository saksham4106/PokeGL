package entity.pokemon;


import entity.PokemonEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pokemons {

    public static final PokemonEntity PIKACHU = new PokemonEntity("pikachu");
    public static final PokemonEntity BULBASAUR = new PokemonEntity("bulbasaur");
    public static final PokemonEntity SQUIRTLE = new PokemonEntity("squirtle");
    public static final PokemonEntity CHARMANDER = new PokemonEntity("charmander");

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
