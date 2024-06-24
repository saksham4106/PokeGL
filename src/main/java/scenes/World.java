package scenes;

import entity.PokemonEntity;
import game.GameObject;
import game.Transform;
import game.Window;
import renderer.Sprite;
import tiles.TilePosition;
import tiles.TiledMapParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World{

    public Map<TilePosition, GameObject> worldmap;
    public List<GameObject> collidableBlocks;
    public List<PokemonEntity> pokemons;

    public GameObject getTile(TilePosition pos){
        return worldmap.getOrDefault(pos, null);
    }

    public void setTile(GameObject tile, TilePosition pos){
        this.worldmap.replace(pos, tile);
    }

    public World(int startX, int startY, int tileWidth, int tileHeight, TiledMapParser parser){
        this.collidableBlocks = new ArrayList<>();
        this.worldmap = new HashMap<>();
        this.pokemons = new ArrayList<>();
        this.generateTiles(startX, startY, tileWidth, tileHeight, parser);
    }

    public void generateTiles(int startX, int startY, int tileWidth, int tileHeight, TiledMapParser parser){
        //world = new TilePosition[parser.mapXTiles * parser.mapYTiles * parser.layers];
        int x = 0;
        int y = 0;
        int currentLayer = 0;
        int index = 0;
        for (int tileID : parser.data) {
            Sprite sprite = new Sprite(parser.tileWidth, parser.tileHeight, parser.texture);
            sprite.setTexCoords(parser.texCoordGenerator(tileID - 1));
            int zIndex = !parser.zIndices.isEmpty() ? parser.zIndices.get(currentLayer) : 0;
            GameObject tile = new GameObject(new Transform(startX + x * tileWidth, startY + y * tileHeight, tileWidth, tileHeight), sprite, zIndex);
            if ((tileID == 1)) {
                tile.setCollidable(true);
                this.collidableBlocks.add(tile);
            }

            if ((tileID) == Integer.MIN_VALUE) {
                currentLayer++;
                x = 0;
                y = 0;
                continue;
            }

            this.worldmap.put( new TilePosition(index % parser.mapXTiles, index / parser.mapXTiles,
                            index / (parser.mapXTiles * parser.mapYTiles)), tile);
            Window.getCurrentScene().addGameObjectToScene(tile);

            x++;
            if (x == parser.mapXTiles) {
                x = 0;
                y--;
            }
            index++;
        }
    }
}
