package entity;

import animation.AnimationState;
import callback.KeyEventListener;
import collision.CollisionDetection;
import entity.pokemon.Pokemons;
import events.Event;
import events.PokemonDespawnEvent;
import game.Camera;
import game.GameObject;
import game.Transform;
import game.Window;
import org.joml.Vector2f;
import renderer.Renderer;
import renderer.Sprite;
import renderer.Spritesheet;
import renderer.Texture;
import scenes.BattleScene;
import scenes.World;
import ui.ButtonObject;
import utils.Assets;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerEntity extends GameObject {
    private EntityFacing facing = EntityFacing.DOWN;
    public Spritesheet upSpriteSheet;
    public Spritesheet downSpriteSheet;
    public Spritesheet leftSpriteSheet;
    public Spritesheet rightSpriteSheet;
    public boolean isMoving;
    public Spritesheet currentSpriteSheet;
    public AnimationState animationState;
    public Transform hitbox;
    public final float speed = 50;
    public World world;
    public String name;
    private List<PokemonEntity> poketeam;
    private List<PokemonEntity> pokemons;


    ButtonObject battleButton;

    private final Random random = new Random();

    public PlayerEntity(int width, int height, Vector2f position, Texture texture, World world) {
        this(width, height, position, 0, texture, world);
    }

    public PlayerEntity(int width, int height, Vector2f position, int zIndex, Texture texture, World world){
        super(new Transform(position, new Vector2f(width, height)), new Sprite(width, height, texture), zIndex);
        upSpriteSheet = Assets.getSpritesheet("", new Spritesheet(Assets.getTexture("assets/textures/playerUp.png"),
                68, 48, 4));

        downSpriteSheet = Assets.getSpritesheet("", new Spritesheet(Assets.getTexture("assets/textures/playerDown.png"),
                68, 48, 4));

        leftSpriteSheet = Assets.getSpritesheet("", new Spritesheet(Assets.getTexture("assets/textures/playerLeft.png"),
                68, 48, 4));

        rightSpriteSheet = Assets.getSpritesheet("", new Spritesheet(Assets.getTexture("assets/textures/playerRight.png"),
                68, 48, 4));

        currentSpriteSheet = downSpriteSheet;
        this.hitbox = new Transform(position.x - 1, position.y + 2, width - 2, height/(float)2);
        this.world = world;
        this.name = "Player 1";
        this.poketeam = new ArrayList<>();
        this.pokemons = new ArrayList<>();
        this.init();
    }

    @Override
    public void init() {
        this.animationState = new AnimationState(this);
        this.sprite = currentSpriteSheet.sprites.get(0);
        Window.getCurrentScene().camera = new Camera(new Vector2f(this.transform.position));
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

            if(isColliding(this.hitbox)){
                this.transform.position.sub(velocity);
                this.hitbox.position.sub(velocity);
                this.isMoving = false;
            }else{
                this.isMoving = true;
                this.markDirty(true);
                Window.getCurrentScene().camera.position = this.transform.position;
            }

        }else{
            this.isMoving = false;
            this.sprite = currentSpriteSheet.sprites.get(0);
            this.markDirty(true);
        }

        if(isMoving){
            this.animationState.update(dt);
        }

    }

    public boolean isColliding(Transform transform){
        for (GameObject collidableTile : world.collidableBlocks) {
            if(CollisionDetection.boxInBox(transform, collidableTile.getTransform())) return true;
        }
        return false;
    }


    PokemonEntity targetPokemon = null;
    Spritesheet pokemonSpritesheet = Assets.getSpritesheet("assets/frontFaces.png",
            new Spritesheet(Assets.getTexture("assets/frontFaces.png"), 96, 96, 721));
    Spritesheet backSpritesheet = Assets.getSpritesheet("assets/backFaces.png",
            new Spritesheet(Assets.getTexture("assets/backFaces.png"), 96, 96, 721));
    int counter = 20;

    @Override
    public void tick(){
        counter--;
        if(counter <= 0){
            if(isMoving){
                if(targetPokemon == null){
                    if(random.nextInt(50) == 1){
                        // Pokemon Spawning
                        targetPokemon = new PokemonEntity(Pokemons.pokemons.get(random.nextInt(Pokemons.pokemons.size() - 1)));
                        targetPokemon.setSprite(pokemonSpritesheet.sprites.get(targetPokemon.id - 1));

                        Vector2f direction = new Vector2f(this.facing == EntityFacing.LEFT ? -1 :
                                this.facing == EntityFacing.RIGHT ? 1 : 0,  this.facing == EntityFacing.UP ? 1 :
                                this.facing == EntityFacing.DOWN ? -1 : 0);
                        Vector2f position = new Vector2f(this.transform.position);
                        position.add(direction.normalize().mul(80));
                        targetPokemon.setTransform(new Transform(position.x, position.y,
                                64, 64));

                        this.world.pokemons.add(targetPokemon);
                        Window.getCurrentScene().addGameObjectToScene(targetPokemon);

                        counter = 20;
                    }
                }
            }

            if(targetPokemon != null){
                float x1 = targetPokemon.getTransform().position.x;
                float y1 = targetPokemon.getTransform().position.y;
                float x2 = this.transform.position.x;
                float y2 = this.transform.position.y;

                float dist = (float) (Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
                if(dist > 80 * 80){
                    if(Window.getCurrentScene().containsGameObject(battleButton)){
                        Window.getCurrentScene().removeGameObjectFromScene(battleButton);
                    }

                }else{
                    if(!Window.getCurrentScene().containsGameObject(battleButton)){
                        battleButton = new ButtonObject(200, 50, "\t\tBattle " + targetPokemon.name + "!\t\t", (button) -> {
                            Window.getCurrentScene().removeGameObjectFromScene(targetPokemon);
                            Window.getCurrentScene().removeGameObjectFromScene(battleButton);

                            Window.setScene("battle", new BattleScene(targetPokemon, this));


                        });
                        Window.getCurrentScene().addGameObjectToScene(battleButton);
                    }
                }

            }
        }
    }

    private void switchFacing(EntityFacing facing){
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

    public void addPokemonToTeam(PokemonEntity pokemon){
    }

    public void addPokemonToInventory(PokemonEntity pokemon){

    }

    @Event.EventHandler
    public void onPokemonDespawn(PokemonDespawnEvent event){
        this.targetPokemon = null;
        Window.getCurrentScene().removeGameObjectFromScene(battleButton);
    }
}
