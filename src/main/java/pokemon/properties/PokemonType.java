package pokemon.properties;

import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

public class PokemonType {
    public String name;

    public List<PokemonType> doesNoDamageTo;
    public List<PokemonType> doesLessDamageTo;
    public List<PokemonType> doesHighDamageTo;

    public Vector4f color;

    public PokemonType(String name, List<PokemonType> doesNoDamageTo, List<PokemonType> doesLessDamageTo,
                       List<PokemonType> doesHighDamageTo) {
        this.name = name;
        this.doesNoDamageTo = doesNoDamageTo;
        this.doesLessDamageTo = doesLessDamageTo;
        this.doesHighDamageTo = doesHighDamageTo;
    }

    public PokemonType(String name){
        this.name = name;
        this.doesNoDamageTo = new ArrayList<>();
        this.doesLessDamageTo = new ArrayList<>();
        this.doesHighDamageTo = new ArrayList<>();
    }


    public PokemonType setDoesNoDamageTo(PokemonType... pokemonTypes) {
        this.doesNoDamageTo = List.of(pokemonTypes);
        return this;
    }

    public PokemonType setDoesLessDamageTo(PokemonType... doesLessDamageTo) {
        this.doesLessDamageTo = List.of(doesLessDamageTo);
        return this;
    }

    public PokemonType setDoesHighDamageTo(PokemonType... doesHighDamageTo) {
        this.doesHighDamageTo = List.of(doesHighDamageTo);
        return this;
    }
}
