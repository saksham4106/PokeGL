package pokemon;

import game.Window;
import pokemon.properties.PokemonMove;
import pokemon.properties.PokemonType;
import pokemon.properties.PokemonTypes;
import utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
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
        List<PokemonMove> moves = new ArrayList<>();
        Collections.shuffle(normal_moves, Utils.random);
        Collections.shuffle(t1_moves, Utils.random);
        Collections.shuffle(t2_moves, Utils.random);

        if(t1_moves.isEmpty()){
            if(t2_moves.isEmpty()){
                return normal_moves;
            }
            if(normal_moves.isEmpty()){
                return t2_moves;
            }
            moves.addAll(List.of(t2_moves.get(0), t2_moves.get(1), normal_moves.get(0), normal_moves.get(1)));

        }else{
            if(t2_moves.isEmpty() && normal_moves.isEmpty()){
                return t1_moves;
            }
            moves.addAll(List.of(t1_moves.get(0), t1_moves.get(1)));
            if(!t2_moves.isEmpty() && !normal_moves.isEmpty()){
                moves.add(t2_moves.get(0));
                moves.add(normal_moves.get(0));
                return moves;
            }
            if(t2_moves.isEmpty()) moves.addAll(List.of(normal_moves.get(0), t1_moves.get(2)));
            if(normal_moves.isEmpty()) moves.addAll(List.of(t2_moves.get(0), t2_moves.get(1)));

        }

        return moves;

    }
}

