package pokemon.properties;

public class PokemonMove {
    public int id;
    public String name;

    public PokemonMove(int id, String name, PokemonType type, int accuracy, int power, int pp, int priority, String desc){
        this.name = name;
        this.id = id;
    }
}
