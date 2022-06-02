package entity;

import animation.AnimationState;
import callback.KeyEventListener;
import entity.pokemon.Pokemons;
import events.Event;
import events.PokemonDespawnEvent;
import game.Camera;
import game.GameObject;
import game.Transform;
import game.Window;
import org.joml.Vector2f;
import renderer.Sprite;
import renderer.Spritesheet;
import renderer.Texture;
import scenes.BattleScene;
import scenes.World;
import ui.ButtonObject;
import utils.Assets;
import utils.CollisionDetection;

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

    ButtonObject battleButton;

    private final Random random = new Random();

    public PlayerEntity(int width, int height, Vector2f position, Texture texture, World world) {
        this(width, height, position, 0, texture, world);
    }

    public PlayerEntity(int width, int height, Vector2f position, int zIndex, Texture texture, World world){
        super(new Transform(position, new Vector2f(width, height)), new Sprite(width, height, texture), zIndex);
        upSpriteSheet = Assets.getSpritesheet("assets/textures/playerUp.png",
                new Spritesheet(Assets.getTexture("assets/textures/playerUp.png"), 68, 48, 4));

        downSpriteSheet = Assets.getSpritesheet("assets/textures/playerDown.png",
                new Spritesheet(Assets.getTexture("assets/textures/playerDown.png"), 68, 48, 4));

        leftSpriteSheet = Assets.getSpritesheet("assets/textures/playerLeft.png",
                new Spritesheet(Assets.getTexture("assets/textures/playerLeft.png"), 68, 48, 4));

        rightSpriteSheet = Assets.getSpritesheet("assets/textures/playerRight.png",
                new Spritesheet(Assets.getTexture("assets/textures/playerRight.png"), 68, 48, 4));

        currentSpriteSheet = downSpriteSheet;
        this.hitbox = new Transform(position.x - 1, position.y + 2, width - 2, height/(float)2);
        this.world = world;
        this.name = "Player 1";
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
    public void update(float dt) {
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


    int counter = 20;
    PokemonEntity targetPokemon;
    @Override
    public void tick(){
        counter--;
        if(counter <= 0){
            if(isMoving){
                if(random.nextInt(100) == 1){
                    targetPokemon = new PokemonEntity(Pokemons.pokemons.get(random.nextInt(3)));
                    Vector2f direction = new Vector2f(this.facing == EntityFacing.LEFT ? -1 :
                            this.facing == EntityFacing.RIGHT ? 1 : 0,  this.facing == EntityFacing.UP ? 1 :
                            this.facing == EntityFacing.DOWN ? -1 : 0);
                    Vector2f position = new Vector2f(this.transform.position);
                    position.add(direction.normalize().mul(80));
                    targetPokemon.setTransform(new Transform(position.x, position.y,
                            32, 32));
                    this.world.pokemons.add(targetPokemon);
                    Window.getCurrentScene().addGameObjectToScene(targetPokemon);

//                    battleButton = new ButtonObject((int)this.transform.position.x, (int)this.transform.position.y - 20,
//                            new Text("Battle!", Fonts.OPEN_SANS_FONT, new Vector4f(1, 1, 1, 1),
//                                    0.1f), (button) -> {}, this.zIndex + 1, new Sprite(0, 0,
//                            new Vector4f(0.6f, 0.6f, 0.6f, 1)), true);
//                    Window.getCurrentScene().addGameObjectToScene(battleButton);
                    counter = 20;
                }
            }


//            float x1 = this.transform.position.x;
//            float y1 = this.transform.position.y;
//            Vector2f battleButtonPos = MathUtil.screenToWorld(MathUtil.NdcToScreen(battleButton.getTransform().position));
//            float x2 = battleButtonPos.x;
//            float y2 = battleButtonPos.y;
            if(targetPokemon != null){
                float x1 = targetPokemon.getTransform().position.x;
                float y1 = targetPokemon.getTransform().position.y;
                float x2 = this.transform.position.x;
                float y2 = this.transform.position.y;

                float dist = (float) (Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
                if(dist > 6400){
                    if(Window.getCurrentScene().containsGameObject(battleButton)){
                        Window.getCurrentScene().removeGameObjectFromScene(battleButton);
                    }

                }else{
                    if(!Window.getCurrentScene().containsGameObject(battleButton)){
                        battleButton = new ButtonObject(200, 50, "\t\tBattle Pokemon!\t\t", (button) -> {
                            Window.setScene(new BattleScene(targetPokemon, this));
                            Window.getCurrentScene().removeGameObjectFromScene(battleButton);
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


    @Event.EventHandler
    public void onPokemonDespawn(PokemonDespawnEvent event){
        this.targetPokemon = null;
        Window.getCurrentScene().removeGameObjectFromScene(battleButton);
    }
}
