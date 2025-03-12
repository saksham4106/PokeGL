package pokemon.properties;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PokemonMove {
    public int id;
    public String name;
    public PokemonType type;
    public int accuracy;
    public int power;
    public int pp;
    public int priority;
    public String desc;

    public PokemonMove(int id, String name, PokemonType type, int accuracy, int power, int pp, int priority, String desc) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.accuracy = accuracy;
        this.power = power;
        this.pp = pp;
        this.priority = priority;
        this.desc = desc;
    }

    public String getFormattedName(){
        String format = this.name.replace('_', ' ').replace('-', ' ');
        return Arrays.stream(format.split(" ")).map(c -> Character.toUpperCase(c.charAt(0)) + c.substring(1))
                .collect(Collectors.joining(" "));
    }
}
