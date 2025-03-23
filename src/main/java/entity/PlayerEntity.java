package entity;

import animation.AnimationState;
import callback.KeyEventListener;
import collision.CollisionDetection;
import events.Event;
import events.WindowCloseEvent;
import game.Camera;
import game.GameObject;
import game.Transform;
import game.Window;
import org.joml.Vector2f;
import pokemon.Pokemon;
import pokemon.Pokemons;
import renderer.Renderer;
import renderer.Sprite;
import renderer.Spritesheet;
import renderer.Texture;
import scenes.Scene;
import scenes.SelectTeamScene;
import scenes.WildBattleScene;
import scenes.World;
import serialization.SaveGame;
import ui.ButtonObject;
import utils.Assets;
import utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerEntity extends GameObject {

    public Spritesheet upSpriteSheet = Assets.getSpritesheet("", new Spritesheet(Assets.getTexture("assets/textures/playerUp.png"),
            68, 48, 4));
    public Spritesheet downSpriteSheet = Assets.getSpritesheet("", new Spritesheet(Assets.getTexture("assets/textures/playerDown.png"),
                68, 48, 4));
    public Spritesheet leftSpriteSheet = Assets.getSpritesheet("", new Spritesheet(Assets.getTexture("assets/textures/playerLeft.png"),
            68, 48, 4));
    public Spritesheet rightSpriteSheet = Assets.getSpritesheet("", new Spritesheet(Assets.getTexture("assets/textures/playerRight.png"),
            68, 48, 4));
    public Spritesheet currentSpriteSheet;

    public final float speed = 50;
    public String name;
    public List<PokemonEntity> poketeam = new ArrayList<>();
    public List<PokemonEntity> pokemons = new ArrayList<>();
    public PokemonEntity selectedPokemon;
    public int poke_balls;
    public int super_balls;
    public int ultra_balls;
    public int master_balls;

    private AnimationState animationState;
    private EntityFacing facing;
    private Transform hitbox;
    private boolean isMoving;
    private final World world;
    private PokemonEntity targetPokemon = null;
    private Scene scene;
    private ButtonObject battleButton;
    private final Random random = new Random();

    public PlayerEntity(String name, int width, int height, Vector2f position, int zIndex, Texture texture, World world){
        super(new Transform(position, new Vector2f(width, height)), new Sprite(width, height, texture), zIndex);
        this.world = world;
        this.name = name;
    }

    public PlayerEntity(String name, int width, int height, Vector2f position, Texture texture, World world) {
        this(name, width, height, position, 0, texture, world);
    }

    public PlayerEntity(String name, Transform transform, Texture texture, World world){
        this(name, (int)transform.scale.x, (int)transform.scale.y, transform.position, 0, texture, world);
    }


    @Override
    public void init() {
        this.hitbox = new Transform(this.transform.position.x - 1, this.transform.position.y + 2, this.transform.scale.x - 2, this.transform.scale.y/(float)2);
        this.animationState = new AnimationState(this);
        this.switchFacing(EntityFacing.DOWN);
        this.sprite = currentSpriteSheet.sprites.get(0);
        this.scene = Window.getCurrentScene();
        this.scene.camera = new Camera(new Vector2f(this.transform.position));
        super.init();
    }

    @Override
    public void update(float dt, Renderer renderer) {
        Vector2f velocity = new Vector2f();

        if(KeyEventListener.isKeyPressed(GLFW_KEY_W)){
            velocity.y += 1;
            switchFacing(EntityFacing.UP);

        }else if(KeyEventListener.isKeyPressed(GLFW_KEY_S)){
            velocity.y -= 1;
            switchFacing(EntityFacing.DOWN);

        }else if(KeyEventListener.isKeyPressed(GLFW_KEY_A)){
            velocity.x -= 1;
            switchFacing(EntityFacing.LEFT);

        }else if(KeyEventListener.isKeyPressed(GLFW_KEY_D)){
            velocity.x += 1;
            switchFacing(EntityFacing.RIGHT);
        }

        if(!velocity.equals(0, 0)){
            velocity = velocity.normalize().mul(speed * dt);
            this.transform.position.add(velocity);
            this.hitbox.position.add(velocity);

            // Move first, check later
            if(isColliding(this.hitbox)){
                this.transform.position.sub(velocity);
                this.hitbox.position.sub(velocity);
                this.isMoving = false;
            }else{
                this.isMoving = true;
                this.markDirty(true);
                scene.camera.position = this.transform.position;
            }

        }else{
            this.isMoving = false;
            this.sprite = currentSpriteSheet.sprites.get(0);
            this.markDirty(true);
        }

        if(isMoving){
            this.animationState.update(dt);
        }

        if(KeyEventListener.isKeyPressed(GLFW_KEY_B) && scene.containsGameObject(battleButton)){
            startBattle();
        }

        if(KeyEventListener.isKeyPressed(GLFW_KEY_SPACE)){
            // particles
        }
    }

    public boolean isColliding(Transform transform){
        for (GameObject collidableTile : world.collidableBlocks) {
            if(CollisionDetection.boxInBox(transform, collidableTile.getTransform())) return true;
        }
        return false;
    }

    int pokemonSpawnCounter = 3 * 20;
    int pokemonDespawnCounter = 5 * 20;

    @Override
    public void tick(){
        pokemonSpawnCounter--;

        if(pokemonSpawnCounter <= 0){
            if(isMoving && targetPokemon == null){
                if(random.nextInt(50) == 1){
                    spawnPokemon();
                    pokemonSpawnCounter = 3 * 20;
                }
            }

            if(targetPokemon != null){
                float x1 = targetPokemon.getTransform().position.x;
                float y1 = targetPokemon.getTransform().position.y;
                float x2 = this.transform.position.x;
                float y2 = this.transform.position.y;
                float dist = (float) (Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));

                if(dist > 80 * 80) {
                    scene.removeGameObjectFromScene(battleButton);

                    if(dist > 120 * 120){
                        scene.removeGameObjectFromScene(targetPokemon);
                        removeTarget();
                    }

                }else{
                    if(!scene.containsGameObject(battleButton)) {
                        battleButton = new ButtonObject(200, 50, "\t\tBattle " + targetPokemon.name + "!\t\t",
                                this::startBattle);
                        scene.addGameObjectToScene(battleButton);
                    }
                }
            }
        }

        //If pokemon despawning should be in PlayerEntity
        if(this.targetPokemon != null) {
            pokemonDespawnCounter--;
            if(pokemonDespawnCounter <= 0){
                scene.removeGameObjectFromScene(targetPokemon);
                scene.removeGameObjectFromScene(battleButton);
                removeTarget();
                pokemonDespawnCounter = 5 * 20;
            }
        }else {
            pokemonDespawnCounter = 5 * 20;
        }
    }

    private void spawnPokemon(){
        Pokemon pokemon = Pokemons.get(random.nextInt(Pokemons.pokemons.size() - 2 ) + 1);
        targetPokemon = new PokemonEntity(pokemon);

        Vector2f facingVector = new Vector2f(this.facing == EntityFacing.LEFT ? -1 :
                this.facing == EntityFacing.RIGHT ? 1 : 0,  this.facing == EntityFacing.UP ? 1 :
                this.facing == EntityFacing.DOWN ? -1 : 0);

        Vector2f position = new Vector2f(this.transform.position);
        position.add(facingVector.normalize().mul(80));

        targetPokemon.setTransform(new Transform(position.x, position.y,
                64, 64));

        if(!isColliding(targetPokemon.getTransform())){
//            this.world.pokemons.add(targetPokemon);
            scene.addGameObjectToScene(targetPokemon);
        }else{
            removeTarget();
        }
    }

    private void startBattle(){
        scene.removeGameObjectFromScene(targetPokemon);
        scene.removeGameObjectFromScene(battleButton);

//        Window.setScene("battle", new WildBattleScene(targetPokemon, this));
        Window.setScene("select_team", new SelectTeamScene(this, targetPokemon));
    }


    public void switchFacing(EntityFacing facing){
        if(this.facing == facing) return;
        this.facing = facing;
        switch (this.facing) {
            case UP -> this.currentSpriteSheet = upSpriteSheet;
            case DOWN -> this.currentSpriteSheet = downSpriteSheet;
            case LEFT -> this.currentSpriteSheet = leftSpriteSheet;
            case RIGHT -> this.currentSpriteSheet = rightSpriteSheet;
        }
        this.sprite = currentSpriteSheet.sprites.get(0);
        this.animationState.clear();
        this.animationState.addFrames(currentSpriteSheet.sprites, (1/ speed) * 20);
    }

    public void removeTarget(){
        this.targetPokemon = null;
    }

    public void addPokemonToTeam(PokemonEntity pokemon){
        this.poketeam.add(pokemon);
    }

    public void addPokemonToInventory(PokemonEntity pokemon){
        this.pokemons.add(pokemon);
        if(this.poketeam.size() < 6){
            addPokemonToTeam(pokemon);
        }
    }

    // If despawning should be in PokemonEntity
//    @Event.EventHandler
//    public void onPokemonDespawn(PokemonDespawnEvent event){
//        this.targetPokemon = null;
//        scene.removeGameObjectFromScene(battleButton);
//    }

    @Event.EventHandler
    public void onWindowClose(WindowCloseEvent event){
        SaveGame.savePlayer(this);
    }
}
