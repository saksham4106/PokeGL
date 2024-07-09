package pokemon.properties;

import java.util.HashMap;
import java.util.Map;

public class PokemonTypes {
    public static final Map<String, PokemonType> types = new HashMap<>();

    public static final PokemonType FLYING = register("flying");
    public static final PokemonType GROUND = register("ground");
    public static final PokemonType NORMAL = register("normal");
    public static final PokemonType FIGHTING = register("fighting");
    public static final PokemonType POISON = register("poison");
    public static final PokemonType ROCK = register("rock");
    public static final PokemonType BUG = register("bug");
    public static final PokemonType GHOST = register("ghost");
    public static final PokemonType STEEL = register("steel");
    public static final PokemonType FIRE = register("fire");
    public static final PokemonType GRASS = register("grass");
    public static final PokemonType ELECTRIC = register("electric");
    public static final PokemonType ICE = register("ice");
    public static final PokemonType DRAGON = register("dragon");
    public static final PokemonType FAIRY = register("fairy");
    public static final PokemonType PSYCHIC = register("psychic");
    public static final PokemonType WATER = register("water");
    public static final PokemonType DARK = register("dark");
    


    static {
        NORMAL.setDoesNoDamageTo(GHOST).setDoesLessDamageTo(ROCK, STEEL);

        FIGHTING.setDoesNoDamageTo(GHOST).setDoesLessDamageTo(FLYING, POISON, BUG, PSYCHIC, FAIRY)
                .setDoesHighDamageTo(NORMAL, ROCK, STEEL, ICE, DARK);

        FLYING.setDoesLessDamageTo(ROCK, STEEL, ELECTRIC).setDoesHighDamageTo(FIGHTING, BUG, GRASS);

        POISON.setDoesNoDamageTo(STEEL).setDoesLessDamageTo(POISON, GROUND, ROCK, GHOST).
                setDoesHighDamageTo(GRASS, FAIRY);

        GROUND.setDoesNoDamageTo(FLYING).setDoesLessDamageTo(BUG, GRASS).
                setDoesHighDamageTo(POISON, ROCK, STEEL, FIRE, ELECTRIC);

        ROCK.setDoesHighDamageTo(FLYING, BUG, FIRE, ICE).setDoesLessDamageTo(FIGHTING, GROUND, STEEL);

        BUG.setDoesLessDamageTo(FIGHTING, FLYING, POISON, GHOST, STEEL, FIRE, FAIRY).
                setDoesHighDamageTo(GRASS, PSYCHIC, DARK);

        GHOST.setDoesNoDamageTo(NORMAL).setDoesLessDamageTo(DARK).setDoesHighDamageTo(GHOST, PSYCHIC);

        STEEL.setDoesLessDamageTo(FIRE, WATER, ELECTRIC).setDoesHighDamageTo(ROCK, ICE, FAIRY);

        FIRE.setDoesLessDamageTo(ROCK, FIRE, WATER, DRAGON).setDoesHighDamageTo(BUG, STEEL, GRASS, ICE);

        WATER.setDoesLessDamageTo(WATER, GRASS, DRAGON).setDoesHighDamageTo(GROUND, ROCK, FIRE);

        GRASS.setDoesLessDamageTo(FLYING, POISON, BUG, STEEL, FIRE, GRASS, DRAGON).setDoesHighDamageTo(GROUND, ROCK, WATER);

        ELECTRIC.setDoesNoDamageTo(GROUND).setDoesLessDamageTo(GRASS, ELECTRIC, DRAGON).setDoesHighDamageTo(FLYING, WATER);

        ICE.setDoesLessDamageTo(STEEL, FIRE, ICE, WATER).setDoesHighDamageTo(FLYING, GROUND, GRASS, DRAGON);

        DRAGON.setDoesNoDamageTo(FAIRY).setDoesLessDamageTo(STEEL).setDoesHighDamageTo(DRAGON);

        DARK.setDoesLessDamageTo(FIGHTING, DARK, FAIRY).setDoesHighDamageTo(GHOST, PSYCHIC);

        FAIRY.setDoesLessDamageTo(POISON, STEEL, FIRE).setDoesHighDamageTo(FIGHTING, DRAGON, DARK);

    }

    public static PokemonType register(String name){
        PokemonType t = new PokemonType(name);
        types.put(name, t);
        return t;
    }

    public static PokemonType get(String name){
        return types.get(name);
    }
}
