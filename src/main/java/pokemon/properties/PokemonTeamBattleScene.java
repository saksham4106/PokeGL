package pokemon.properties;

import entity.PlayerEntity;
import entity.PokemonEntity;
import scenes.Scene;

import java.util.List;

public class PokemonTeamBattleScene extends Scene {
    private PlayerEntity player;
    private List<PokemonEntity> enemyTeam;
    private String enemy;
    private List<PokemonEntity> playerTeam;

    public PokemonTeamBattleScene(PlayerEntity player, List<PokemonEntity> enemyTeam, String enemy){
        this.enemyTeam = enemyTeam;
        this.enemy = enemy;
        this.playerTeam = player.poketeam;
        this.player = player;
    }

    public class Damn{

    }

}
