package entity.pokemon;

import renderer.Sprite;

import java.util.List;

public class Pokemon {
    public String name;
    public int maxHP;
    public Sprite frontSprite;
    public Sprite backSprite;

    public List<PokemonMoves> initialMoves;
    public Pokemon nextStage;


}
