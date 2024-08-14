package scenes;

import entity.EntityFacing;
import entity.PlayerEntity;
import entity.PokemonEntity;
import fonts.Fonts;
import game.Camera;
import game.GameObject;
import game.Transform;
import game.Window;
import org.joml.Vector2f;
import org.joml.Vector4f;
import pokemon.Pokemons;
import pokemon.properties.Pokeball;
import pokemon.properties.PokemonMove;
import pokemon.properties.PokemonType;
import renderer.Sprite;
import renderer.Texture;
import ui.ButtonObject;
import ui.TextObject;
import utils.Assets;
import utils.ColorUtils;
import utils.Utils;

import javax.swing.*;
import java.util.List;

public class WildBattleScene extends Scene {
    PokemonEntity oppPok;
    PokemonEntity pok;
    PlayerEntity player;
    GameObject opp_hpBar;
    GameObject hpBar;
    String pokMsg = "";
    String oppMsg = "";
    String playerWon = "";
    boolean pokemonCaught = false;

    public WildBattleScene(PokemonEntity pokemonEntity, PlayerEntity player) {
        super();
        this.oppPok = pokemonEntity;
        this.pok = player.selectedPokemon;
        this.player = player;
        this.camera = new Camera(new Vector2f(0, 0));
        this.throwaway = true;
        this.sceneName = "wild_battle";
    }

    @Override
    public void init() {
        this.setBackground(new Texture("assets/textures/battleBackground.png"));

        player.switchFacing(EntityFacing.DOWN);

//        Sprite playerSprite = new Sprite(player.getSprite());
//        playerSprite.texture = player.downSpriteSheet.texture;
//        GameObject playerRender = new GameObject(new Transform(-135, -125, 64, 96), playerSprite, player.zIndex, false);
        GameObject playerRender = new GameObject(new Transform(-255, -125, 96*2.5f, 96*2.5f),
                Pokemons.getPokemonSprite(Pokemons.get(pok))[1], player.zIndex, false);

        GameObject hp_bg = new GameObject(new Transform(-245, -145, 100, 10),
                new Sprite(100, 10, new Vector4f(0.9f, 0.9f,0.9f, 1)), 15);
        hpBar = new GameObject(new Transform(-245, -145, 100, 10),
                new Sprite(100, 10, new Vector4f(0f, 0.9f,0f, 1)), 16);




        GameObject pokemonRender = new GameObject(new Transform(200, 70, 96 * 2, 96 * 2),
                oppPok.getSprite(), 10, false);

        GameObject opp_hp_bg = new GameObject(new Transform(250, 50, 100, 10),
                new Sprite(100, 10, new Vector4f(0.9f, 0.9f,0.9f, 1)), 15);
        opp_hpBar = new GameObject(new Transform(250, 50, 100 * ((float) oppPok.hp / oppPok.max_hp), 10),
                new Sprite(100, 10, new Vector4f(0f, 0.9f,0f, 1)), 16);

        List<PokemonMove> moves = pok.moves;

//        ButtonObject move1 = new ButtonObject(100, 10, () -> playerAttack(moves.get(0)), new Sprite(200, 75, new Vector4f(0.3f, 0.3f, 0.3f, 1)), 2);
//        TextObject t1 = new TextObject(120, 30, 0.2f, moves.get(0).name, Fonts.OPEN_SANS_FONT, 3, true, ColorUtils.BLACK);
//        ButtonObject move2 = new ButtonObject(300, 10, () -> playerAttack(moves.get(1)), new Sprite(200, 75, new Vector4f(0.3f, 0.3f, 0.3f, 1)), 2);
//        TextObject t2 = new TextObject(320, 30, 0.2f, moves.get(1).name, Fonts.OPEN_SANS_FONT, 3, true, ColorUtils.BLACK);
//        ButtonObject move3 = new ButtonObject(500, 10, () -> playerAttack(moves.get(2)), new Sprite(200, 75, new Vector4f(0.3f, 0.3f, 0.3f, 1)), 2);
//        TextObject t3 = new TextObject(520, 30, 0.2f, moves.get(2).name + " ", Fonts.OPEN_SANS_FONT, 3, true, ColorUtils.BLACK);
//        ButtonObject move4 = new ButtonObject(700, 10, () -> playerAttack(moves.get(3)), new Sprite(200, 75, new Vector4f(0.3f, 0.3f, 0.3f, 1)), 2);
//        TextObject t4 = new TextObject(720, 30, 0.2f, moves.get(3).name + " ", Fonts.OPEN_SANS_FONT, 3, true, ColorUtils.BLACK);
        float h = Fonts.OPEN_SANS_FONT.getMaxHeight(0.2f);
        ButtonObject move1 = new ButtonObject(50, 10, moves.get(0).name, 0.2f, () -> playerAttack(moves.get(0)), new Sprite(100,h, new Vector4f(0.7f, 0.7f, 0.7f, 1)), 3);
        ButtonObject move2 = new ButtonObject(200, 10, moves.get(1).name, 0.2f, () -> playerAttack(moves.get(1)), new Sprite(100,h, new Vector4f(0.7f, 0.7f, 0.7f, 1)), 3);
        ButtonObject move3 = new ButtonObject(350, 10, moves.get(2).name, 0.2f, () -> playerAttack(moves.get(2)), new Sprite(100,h, new Vector4f(0.7f, 0.7f, 0.7f, 1)), 3);
        ButtonObject move4 = new ButtonObject(500, 10, moves.get(3).name, 0.2f, () -> playerAttack(moves.get(3)), new Sprite(100,h, new Vector4f(0.7f, 0.7f, 0.7f, 1)), 3);
        addGameObjectstoScene(move1,move2,move3,move4);

        ButtonObject ball1 = new ButtonObject(800, 20, () -> throwPokeball(Pokeball.POKEBALL), new Sprite(30, 30, Assets.getTexture("assets/textures/poke_ball.png")), 3);
        ButtonObject ball2 = new ButtonObject(850, 20, () -> throwPokeball(Pokeball.SUPERBALL), new Sprite(30, 30, Assets.getTexture("assets/textures/super_ball.png")), 3);
        ButtonObject ball3 = new ButtonObject(900, 20, () -> throwPokeball(Pokeball.ULTRABALL), new Sprite(30, 30, Assets.getTexture("assets/textures/ultra_ball.png")), 3);
        ButtonObject ball4 = new ButtonObject(950, 20, () -> throwPokeball(Pokeball.MASTERBALL), new Sprite(30, 30, Assets.getTexture("assets/textures/master_ball.png")), 3);
        addGameObjectstoScene(ball1, ball2, ball3, ball4);

        ButtonObject defeatedButton = new ButtonObject(100, 500, "Resign ", this::exitBattle);



        addGameObjectstoScene(opp_hp_bg, opp_hpBar);
        addGameObjectstoScene(defeatedButton, hpBar, hp_bg);
        addGameObjectToScene(playerRender);
        addGameObjectToScene(pokemonRender);
    }

    private void playerAttack(PokemonMove move){
        if(playerWon.isEmpty()){
            int damage = generateAttackDamage(move, this.oppPok, this.pok);
            damageOpp(damage);
            this.pokMsg = this.pok.name + " played " + move.name + " dealing " + damage + " HP damage";
            evaluateWinner();
            if(playerWon.isEmpty()) oppAttack();
        }
    }

    private void oppAttack(){
        PokemonMove move = Utils.getRandomElement(oppPok.moves);
        int damage = generateAttackDamage(move, pok, oppPok);
        damagePlayer(damage);
        this.oppMsg = this.oppPok.name + " played " + move.name + " dealing " + damage + " HP damage";
        evaluateWinner();
    }

    private int generateAttackDamage(PokemonMove move, PokemonEntity enemy, PokemonEntity self){
        boolean high = false;
        boolean low = false;
        boolean no = false;

        for(PokemonType t: enemy.types){
            if(move.type.doesHighDamageTo.contains(t)){
                high = true;
            }else if(move.type.doesLessDamageTo.contains(t)){
                low = true;
            }else if(move.type.doesNoDamageTo.contains(t)){
                no = true;
            }
        }
        if(high) {
            return (int) ((self.attack + move.power) * 0.5f);
        }
        if(low) {
            return (int) ((self.attack + move.power) * 0.2f);
        }
        if(no) {
            return (int) ((self.attack + move.power) * 0.1f);
        }

        return((int) ((self.attack + move.power) * 0.3f));
    }

    private void damageOpp(int damage){
        if(this.oppPok.hp - damage <= 0){
            this.oppPok.hp = 0;

        }else{
            this.oppPok.hp -= damage;
        }
        this.opp_hpBar.setTransform(new Transform(250, 50, 100 * ((float) oppPok.hp / oppPok.max_hp), 10));
    }

    private void damagePlayer(int damage){
        if(this.pok.hp - damage <= 0){
            this.pok.hp = 0;

        }else{
            this.pok.hp -= damage;
        }
        this.hpBar.setTransform(new Transform(-245, -145, 100 * ((float) pok.hp / pok.max_hp), 10));
    }

    private void evaluateWinner(){
        if(pok.hp == 0){
            pokMsg += "\n" +  pok.name + " fainted";
            oppMsg += "\n" + oppPok.name + " won!";
            playerWon = "no";
            boolean lost = true;
            for(PokemonEntity p: player.poketeam){
                if (p.hp > 0) {
                    lost = false;
                    break;
                }
            }
            if(!lost){
                addGameObjectstoScene(new ButtonObject(1100, 50, "back", () -> {
                    Window.setScene("select_team", new SelectTeamScene(player, oppPok));
                }));
            }else{

            }


        }else if(oppPok.hp == 0){
            oppMsg += "\n" + oppPok.name + " fainted";
            pokMsg += "\n" + pok.name + " won!";
            playerWon = "yes";

            addGameObjectstoScene(new ButtonObject(1100, 50, "continue", () -> {
                Window.pushScene(new Scene() {
                    @Override
                    public void init() {
                        super.init();
                        GameObject bg = new GameObject(0, 0, Window.width, Window.height, new Sprite(ColorUtils.WHITE), true);
                        bg.zIndex = 10;
                        addGameObjectToScene(bg);
                        TextObject t = new TextObject(100, 400, 0.7f, "To catch the pokemon, do not defeat it", Fonts.LEAGUE_SPARTA_FONT,
                                11, true, ColorUtils.BLACK);
                        renderer.addText(t);
                        healPokemon();
                        addGameObjectToScene(new ButtonObject(300, 300, "Go back to main scene",
                                () -> exitBattle(), new Sprite(new Vector4f(0.5f)), 11));

                    }
                });
            }));
        }
    }


    private void throwPokeball(Pokeball pokeball){
        if(oppPok.hp <= 0 || pok.hp <= 0) return;

        float healthPercent = (float) this.oppPok.hp / this.oppPok.max_hp;

        switch (pokeball){
            case POKEBALL -> {
                if(this.player.poke_balls > 0)  {
                    this.player.poke_balls--;
                    if(healthPercent < 0.25f){
                        catchPokemon();
                    }
                }
            }
            case SUPERBALL -> {
                if(this.player.super_balls > 0)  {
                    this.player.super_balls--;
                    if(healthPercent < 0.35f){
                        catchPokemon();
                    }
                }
            }
            case ULTRABALL -> {
                if(this.player.ultra_balls > 0)  {
                    this.player.ultra_balls--;
                    if(healthPercent < 0.5f){
                        catchPokemon();
                    }
                }
            }
            case MASTERBALL -> {
                if(this.player.master_balls > 0) {
                    this.player.master_balls--;
                    catchPokemon();
                }
            }
        }
    }

    private void catchPokemon(){
        this.player.addPokemonToInventory(oppPok);
        Window.pushScene(new Scene() {
            @Override
            public void init() {
                super.init();
                GameObject bg = new GameObject(0, 0, Window.width, Window.height, new Sprite(ColorUtils.WHITE), true);
                bg.zIndex = 10;
                addGameObjectToScene(bg);
                TextObject t = new TextObject(100, 400, 0.7f, "Congratulations! You caught a " + oppPok.name, Fonts.LEAGUE_SPARTA_FONT,
                        11, true, ColorUtils.BLACK);
                renderer.addText(t);
                addGameObjectToScene(new ButtonObject(300, 300, "Go back to main scene",
                        () -> exitBattle(), new Sprite(new Vector4f(0.5f)), 11));

            }
        });

    }

    private void exitBattle(){
        Window.popScene();
        Window.setScene("main");
        healPokemon();
    }
    private void healPokemon(){
        for(PokemonEntity p : player.poketeam){
            int newHp = (int) (p.max_hp * 0.75f);
            p.hp = p.hp > newHp ? p.max_hp : newHp;
        }
    }


    @Override
    public void update(float dt) {
        super.update(dt);

//        this.renderer.drawString("BATTLE!!! between " + player.name + " and " + oppPok.name + "," + oppPok.level,
//                100, Window.height - 300, 0.3f, Fonts.LEAGUE_SPARTA_FONT, new Vector4f(0, 0, 0, 1), false);

        this.renderer.drawString("HP: " + oppPok.hp + "/" + oppPok.max_hp,
                 Window.width / 1.275f , Window.height /1.72f, 0.15f, Fonts.LEAGUE_SPARTA_FONT, ColorUtils.BLACK, false);

        this.renderer.drawString("HP: " + pok.hp + "/" + pok.max_hp,
                260 , 105, 0.15f, Fonts.LEAGUE_SPARTA_FONT, ColorUtils.BLACK, false);

        this.renderer.drawString(pokMsg, 800, 100, 0.2f, Fonts.LEAGUE_SPARTA_FONT, ColorUtils.BLACK, false);
        this.renderer.drawString(oppMsg, 100, 600, 0.2f, Fonts.LEAGUE_SPARTA_FONT, ColorUtils.BLACK, false);

        this.renderer.drawString(String.valueOf(player.poke_balls), 800 + 9f, 5, 0.15f, Fonts.LEAGUE_SPARTA_FONT, ColorUtils.BLACK, false);
        this.renderer.drawString(String.valueOf(player.super_balls), 850 + 9f, 5, 0.15f, Fonts.LEAGUE_SPARTA_FONT, ColorUtils.BLACK, false);
        this.renderer.drawString(String.valueOf(player.ultra_balls), 900 + 9f, 5, 0.15f, Fonts.LEAGUE_SPARTA_FONT, ColorUtils.BLACK, false);
        this.renderer.drawString(String.valueOf(player.master_balls), 950 + 9f, 5, 0.15f, Fonts.LEAGUE_SPARTA_FONT, ColorUtils.BLACK, false);



    }

    @Override
    public void destroy() {
        super.destroy();
//        for(PokemonEntity p : this.player.poketeam){
//            p.hp = p.max_hp;
//        }

        player.removeTarget();
        this.renderer.clear();
    }
}


