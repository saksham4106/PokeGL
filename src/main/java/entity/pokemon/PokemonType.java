package entity.pokemon;

import java.util.function.Supplier;

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

    public void setDoesNoDamageTo(PokemonType[] doesNoDamageTo) {
        this.doesNoDamageTo = doesNoDamageTo;
    }

    public void setDoesLessDamageTo(PokemonType[] doesLessDamageTo) {
        this.doesLessDamageTo = doesLessDamageTo;
    }

    public void setDoesHighDamageTo(PokemonType[] doesHighDamageTo) {
        this.doesHighDamageTo = doesHighDamageTo;
    }
}
