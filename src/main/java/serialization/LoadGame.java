package serialization;

import entity.PlayerEntity;
import entity.PokemonEntity;
import org.joml.Vector2f;
import pokemon.Pokemons;
import pokemon.properties.PokemonMove;
import pokemon.properties.PokemonMoves;
import pokemon.properties.PokemonType;
import pokemon.properties.PokemonTypes;
import utils.Utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoadGame {

    public static PlayerEntity loadPlayer(PlayerEntity player){
        try {
            Utils.createFileIfDoesntExist("data/player_data.poke");
            String data = new String(Files.readAllBytes(Paths.get("data/player_data.poke")));

            String[] lines = data.split(System.lineSeparator());
            if(lines.length < 2){
                return null;
            }
            String name = lines[0].split(":")[1];
            Vector2f pos = new Vector2f(Float.parseFloat(lines[1].split(":")[1].split("\\|")[0]), Float.parseFloat(lines[1].split(":")[1].split("\\|")[1]));
            int zIndex = Integer.parseInt(lines[2].split(":")[1]);

            String team = lines[3].split(":", 2)[1];
            team = team.substring(1, team.length() - 1);

            String ps = lines[4].split(":", 2)[1];
            ps = ps.substring(1, ps.length() - 1);

            List<PokemonEntity> pokeTeam = new ArrayList<>();
            List<PokemonEntity> pokemons = new ArrayList<>();

            for(String pokemon: team.split("~")){
                if(!pokemon.isBlank()){
                    PokemonEntity p = deserialisePokemon(pokemon);
                    pokeTeam.add(p);
                }
            }

            for(String pokemon: ps.split("~")){
                if(!pokemon.isBlank()){
                    PokemonEntity p = deserialisePokemon(pokemon);
                    pokemons.add(p);
                }
            }

            String[] pokeballs_s = lines[5].split(":", 2)[1].split(",");
            int[] pokeballs = Arrays.stream(pokeballs_s).mapToInt(Integer::parseInt).toArray();

            player.poketeam = pokeTeam;
            player.pokemons = pokemons;
            player.name = name;
            player.setPosition(pos.x , pos.y);
            player.zIndex = zIndex;
            player.poke_balls = pokeballs[0];
            player.super_balls = pokeballs[1];
            player.ultra_balls = pokeballs[2];
            player.master_balls = pokeballs[3];

            return player;

        } catch (Exception e) {
//            return null;
            throw new RuntimeException(e);
        }
    }

    private static PokemonEntity deserialisePokemon(String pokemons){
        String[] attributes = pokemons.split(",");

        int id = Integer.parseInt(attributes[0].split(":")[1]);
        String name = attributes[1].split(":")[1];
        int max_hp = Integer.parseInt(attributes[2].split(":")[1]);
        int attack = Integer.parseInt(attributes[3].split(":")[1]);
        int defence = Integer.parseInt(attributes[4].split(":")[1]);
        int speed = Integer.parseInt(attributes[5].split(":")[1]);
        int xp = Integer.parseInt(attributes[6].split(":")[1]);
        int level = Integer.parseInt(attributes[7].split(":")[1]);
        List<PokemonType> types = new ArrayList<>();
        for(String type: attributes[8].split(":")[1].split("\\|")){
            types.add(PokemonTypes.get(type));
        }

        List<PokemonMove> moves = new ArrayList<>();
        for(String move: attributes[9].split(":")[1].split("\\|")){
            moves.add(PokemonMoves.get(move));
        }

        PokemonEntity e = new PokemonEntity(Pokemons.get(id));
        e.max_hp = max_hp;
        e.attack = attack;
        e.defence = defence;
        e.speed = speed;
        e.xp = xp;
        e.level = level;
        e.moves = moves;
        return e;
    }
}
