package pokemon.properties;

import org.joml.Vector4f;
import utils.ColorUtils;

import java.util.HashMap;
import java.util.Map;

public class PokemonTypes {
    public static final Map<String, PokemonType> types = new HashMap<>();

    public static final PokemonType FLYING = register("flying", ColorUtils.Flying);
    public static final PokemonType GROUND = register("ground", ColorUtils.Ground);
    public static final PokemonType NORMAL = register("normal", ColorUtils.Normal);
    public static final PokemonType FIGHTING = register("fighting", ColorUtils.Fighting);
    public static final PokemonType POISON = register("poison", ColorUtils.Poison);
    public static final PokemonType ROCK = register("rock", ColorUtils.Rock);
    public static final PokemonType BUG = register("bug", ColorUtils.Bug);
    public static final PokemonType GHOST = register("ghost", ColorUtils.Ghost);
    public static final PokemonType STEEL = register("steel", ColorUtils.Steel);
    public static final PokemonType FIRE = register("fire", ColorUtils.Fire);
    public static final PokemonType GRASS = register("grass", ColorUtils.Grass);
    public static final PokemonType ELECTRIC = register("electric", ColorUtils.Electric);
    public static final PokemonType ICE = register("ice", ColorUtils.Ice);
    public static final PokemonType DRAGON = register("dragon", ColorUtils.Dragon);
    public static final PokemonType FAIRY = register("fairy", ColorUtils.Fairy);
    public static final PokemonType PSYCHIC = register("psychic", ColorUtils.Psychic);
    public static final PokemonType WATER = register("water", ColorUtils.Water);
    public static final PokemonType DARK = register("dark", ColorUtils.Dark);



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

    public static PokemonType register(String name, Vector4f color){
        PokemonType t = new PokemonType(name);
        t.color = color;
        types.put(name, t);
        return t;
    }

    public static PokemonType get(String name){
        return types.get(name);
    }
}
