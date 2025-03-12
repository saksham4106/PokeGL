package serialization;

import entity.PlayerEntity;
import entity.PokemonEntity;
import game.Transform;
import pokemon.properties.PokemonMove;
import pokemon.properties.PokemonType;
import utils.Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class SaveGame {

    public static void savePlayer(PlayerEntity player) {
        String name = player.name;
        Transform transform = player.getTransform();
        int zIndex = player.zIndex;
        List<PokemonEntity> pokeTeam = player.poketeam;
        List<PokemonEntity> pokemons = player.pokemons;


        try {
            Utils.createFileIfDoesntExist("data/player_data.poke");
            FileWriter fileWriter = new FileWriter("data/player_data.poke");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("name:" + name);
            printWriter.printf("position:%f|%f" + System.lineSeparator(), transform.position.x, transform.position.y);
            printWriter.println("zIndex:" + zIndex);

            StringBuilder team = new StringBuilder();
            for (PokemonEntity p : pokeTeam) {
                team.append(serializePokemon(p)).append("~");
            }
            if(!team.isEmpty()) {
                team.deleteCharAt(team.length() - 1);
            }

            printWriter.printf("poketeam:{%s}" + System.lineSeparator(), team);

            StringBuilder ps = new StringBuilder();
            for(PokemonEntity p: pokemons){
                ps.append(serializePokemon(p)).append("~");
            }
            if(!ps.isEmpty()){
                ps.deleteCharAt(ps.length() - 1);
            }

            printWriter.printf("pokemons:{%s}" + System.lineSeparator(), ps);
            printWriter.printf("pokeballs:%d,%d,%d,%d" + System.lineSeparator(), player.poke_balls, player.super_balls, player.ultra_balls, player.master_balls);


            printWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String serializePokemon(PokemonEntity pokemon){
        StringBuilder output = new StringBuilder();
        int id = pokemon.id;
        String name = pokemon.name;
        int max_hp = pokemon.max_hp;
        int attack = pokemon.attack;
        int defence = pokemon.defence;
        int speed = pokemon.speed;
        int xp = pokemon.xp;
        int level = pokemon.level;

        output.append(String.format("id:%d,", id));
        output.append(String.format("name:%s,", name));
        output.append(String.format("max_hp:%d,", max_hp));
        output.append(String.format("attack:%d,", attack));
        output.append(String.format("defence:%d,", defence));
        output.append(String.format("speed:%d,", speed));
        output.append(String.format("xp:%d,", xp));
        output.append(String.format("level:%d,", level));

        output.append("types:");
        StringBuilder ty = new StringBuilder();
        for(PokemonType t: pokemon.types){
            ty.append(t.name).append("|");
        }
        if(!ty.isEmpty()) ty.deleteCharAt(ty.length() - 1);
        output.append(ty);


        output.append(",moves:");
        StringBuilder pm = new StringBuilder();
        for(PokemonMove m : pokemon.moves){
            pm.append(m.name).append("|");
        }
        if(!pm.isEmpty()) pm.deleteCharAt(pm.length() - 1);
        output.append(pm);

        return  output.toString();
    }
}
