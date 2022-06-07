package entity.pokemon;

public class PokemonTypes {
    public static final PokemonType FLYING = new PokemonType("flying");
    public static final PokemonType GROUND = new PokemonType("ground");
    public static final PokemonType NORMAL = new PokemonType("normal");
    public static final PokemonType FIGHTING = new PokemonType("fighting");
    public static final PokemonType POISON = new PokemonType("poison");
    public static final PokemonType ROCK = new PokemonType("rock");
    public static final PokemonType BUG = new PokemonType("bug");
    public static final PokemonType GHOST = new PokemonType("ghost");
    public static final PokemonType STEEL = new PokemonType("steel");
    public static final PokemonType FIRE = new PokemonType("fire");
    public static final PokemonType GRASS = new PokemonType("grass");
    public static final PokemonType ELECTRIC = new PokemonType("electric");
    public static final PokemonType ICE = new PokemonType("ice");
    public static final PokemonType DRAGON = new PokemonType("dragon");
    public static final PokemonType FAIRY = new PokemonType("fairy");
    public static final PokemonType PSYCHIC = new PokemonType("psychic");
    public static final PokemonType WATER = new PokemonType("water");
    public static final PokemonType DARK = new PokemonType("dark");

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
}
