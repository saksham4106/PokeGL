package entity;

import entity.pokemon.PokemonType;
import events.EventBus;
import events.PokemonDespawnEvent;
import game.GameObject;
import game.Window;

public class PokemonEntity extends GameObject {

    public String name;
    public int id;
    public boolean isPersistent = false;
    public PokemonType type;

    public PokemonEntity(String name, PokemonType type, int id) {
        this.name = name;
        this.type = type;
        //this.sprite = new Sprite(64, 64, new Texture("assets/pokemonImages/" + id + "_frontFace.png"));
        this.id = id;
    }

    public PokemonEntity(PokemonEntity pokemon){
        this.name = pokemon.name;
        this.type = pokemon.type;
        this.sprite = pokemon.sprite;
        this.id = pokemon.id;
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