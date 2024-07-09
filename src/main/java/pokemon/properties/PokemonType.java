package pokemon.properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"doesNoDamageTo", "doesLessDamageTo", "doesHighDamageTo"})
public class PokemonType {
    public String name;

    public PokemonType[] doesNoDamageTo;
    public PokemonType[] doesLessDamageTo;
    public PokemonType[] doesHighDamageTo;

    public PokemonType(String name, PokemonType[] doesNoDamageTo, PokemonType[] doesLessDamageTo,
                       PokemonType[] doesHighDamageTo) {
        this.name = name;
        this.doesNoDamageTo = doesNoDamageTo;
        this.doesLessDamageTo = doesLessDamageTo;
        this.doesHighDamageTo = doesHighDamageTo;
    }

    public PokemonType(String name){
        this.name = name;
    }

    public PokemonType setDoesNoDamageTo(PokemonType... pokemonTypes) {
        this.doesNoDamageTo = pokemonTypes;
        return this;
    }

    public PokemonType setDoesLessDamageTo(PokemonType... doesLessDamageTo) {
        this.doesLessDamageTo = doesLessDamageTo;
        return this;
    }

    public PokemonType setDoesHighDamageTo(PokemonType... doesHighDamageTo) {
        this.doesHighDamageTo = doesHighDamageTo;
        return this;
    }
}
