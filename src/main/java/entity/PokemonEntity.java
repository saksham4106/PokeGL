package entity;

import pokemon.*;
import game.GameObject;
import pokemon.properties.PokemonMove;
import pokemon.properties.PokemonType;
import renderer.Sprite;
import utils.Utils;

import java.util.List;

public class PokemonEntity extends GameObject {

    public int id;
    public String name;
    public int max_hp;
    public int hp;
    public int attack;
    public int defence;
    public int speed;
    public int xp;
    public int level;
    public List<PokemonType> types;
    public List<PokemonMove> moves;

//    private EntityFacing facing;
    private Sprite[] sprites;

    public PokemonEntity(Pokemon pokemon){
        this.name = pokemon.name;
        this.types = pokemon.types;
        this.moves = pokemon.generateMoves();
        this.id = pokemon.id;
        this.sprites = Pokemons.getPokemonSprite(pokemon);
        this.hp = pokemon.hp;
        this.max_hp = pokemon.hp;
        this.defence = pokemon.defence;
//        this.level = (int) Math.round(Math.sqrt(pokemon.base_exp)) + Utils.getRandomElement(List.of(-1, 0, 0, 0, 1));
        this.level = pokemon.base_exp / (6 + Utils.getRandomElement(List.of(-1, 0, 0, 0, 1)));
        this.xp = (int) (this.level * this.level * this.level * 1.25f);

        switchFacing(EntityFacing.DOWN);
        reEvaluateStats(pokemon);
    }

    public void reEvaluateStats(Pokemon pokemon){

    }


    public void switchFacing(EntityFacing facing){

        if(facing == EntityFacing.UP){
            this.sprite = sprites[1];
        }else{
            this.sprite = sprites[0];
        }
    }

    private int counter = 5 * 20;
    @Override
    public void tick() {
        // If despawning should be in PokemonEntity;

//        counter--;
//        if(counter <= 0){
//            if(!isPersistent){
//                Window.getCurrentScene().removeGameObjectFromScene(this);
//                EventBus.invoke(new PokemonDespawnEvent());
//            }
//        }
    }

    @Override
    public String toString() {
        return name + "," + id;
    }
}
