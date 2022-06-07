package entity;

import entity.pokemon.PokemonType;
import events.EventBus;
import events.PokemonDespawnEvent;
import game.GameObject;
import game.Window;
import renderer.Sprite;
import renderer.Texture;

public class PokemonEntity extends GameObject {

    public String name;
    public boolean isPersistent = false;
    public PokemonType type;

    public PokemonEntity(String name, PokemonType type) {
        this.name = name;
        this.type = type;
        this.sprite = new Sprite(64, 64, new Texture("assets/textures/pokemon/" + name + ".png"));
    }

    public PokemonEntity(PokemonEntity pokemon){
        this.name = pokemon.name;
        this.type = pokemon.type;
        this.sprite = pokemon.sprite;
        this.isPersistent = pokemon.isPersistent;
    }

    int counter = 5 * 20;

    @Override
    public void tick() {
        counter--;
        if(counter <= 0){
            if(!isPersistent){
                Window.getCurrentScene().removeGameObjectFromScene(this);
                EventBus.invoke(new PokemonDespawnEvent());
            }
        }
    }
}