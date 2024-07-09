package pokemon;

import pokemon.properties.PokemonMove;
import pokemon.properties.PokemonType;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {
    public int id;
    public String name;
    public int height;
    public int hp;
    public int attack;
    public int defence;
    public int speed;
    public int base_exp;
    public List<PokemonType> types;
    public List<PokemonMove> normal_moves;
    public List<PokemonMove> t1_moves;
    public List<PokemonMove> t2_moves;

    public Pokemon(int id, String name, int height, int hp, int attack, int defence, int speed, int base_exp,
                   List<PokemonType> types, List<PokemonMove> normal_moves, List<PokemonMove> t1_moves, List<PokemonMove> t2_moves) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.hp = hp;
        this.attack = attack;
        this.defence = defence;
        this.speed = speed;
        this.base_exp = base_exp;
        this.types = types;
        this.normal_moves = normal_moves;
        this.t1_moves = t1_moves;
        this.t2_moves = t2_moves;
    }

    public List<PokemonMove> generateMoves(){
        return this.normal_moves;
    }
}
