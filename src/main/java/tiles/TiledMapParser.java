package tiles;

import org.joml.Vector2f;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import renderer.Texture;
import utils.Assets;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TiledMapParser {
    String filename;
    public int tileWidth, tileHeight, mapXTiles, mapYTiles, imageWidth, imageHeight;
    public String tileSetImagePath;
    public int[] data;

    public int layers;
    public List<Integer> zIndices;
    public int total_rows;
    public Texture texture;

    public TiledMapParser(String filename){
        this.filename = filename;
        this.zIndices = new ArrayList<>();
        parseXML();
    }

    private void parseXML(){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try{
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(Assets.readFileAsInputStream(filename));
            String parentPath = Paths.get(filename).getParent() + "/";
            doc.getDocumentElement().normalize();

            this.tileWidth = Integer.parseInt(doc.getDocumentElement().getAttribute("tilewidth"));
            this.tileHeight = Integer.parseInt(doc.getDocumentElement().getAttribute("tileheight"));
            this.mapXTiles = Integer.parseInt(doc.getDocumentElement().getAttribute("width"));
            this.mapYTiles = Integer.parseInt(doc.getDocumentElement().getAttribute("height"));

            String tileSet = ((Element)doc.getElementsByTagName("tileset").item(0)).getAttribute("source");
            Document tileset = db.parse(Assets.readFileAsInputStream(parentPath + tileSet));
            Element imageTag = ((Element)tileset.getElementsByTagName("image").item(0));

            this.imageWidth = Integer.parseInt(imageTag.getAttribute("width"));
            this.imageHeight = Integer.parseInt(imageTag.getAttribute("height"));

            String tileSetImage = ((Element)tileset.getElementsByTagName("image").item(0)).getAttribute("source");
            List<Integer> cumulativeData = new ArrayList<>();
            NodeList properties = doc.getElementsByTagName("property");

            for(int property = 0; property < properties.getLength(); property++){
                zIndices.add(Integer.parseInt(((Element)properties.item(property)).getAttribute("value")));
            }

            for(int i = 0; i < doc.getElementsByTagName("data").getLength(); i++){
                String data = doc.getElementsByTagName("data").item(i).getTextContent().trim().replaceAll("\n", "");
                Collections.addAll(cumulativeData, Arrays.stream(Arrays.stream(data.split(",")).mapToInt(Integer::parseInt).toArray()).boxed().toArray(Integer[]::new));
                layers++;
                cumulativeData.add(Integer.MIN_VALUE);
            }

            this.data = cumulativeData.stream().mapToInt(i -> i).toArray();

            this.tileSetImagePath = tileSetImage.replaceAll("[.][.]",
                    String.valueOf(Paths.get(filename).getParent().getParent()));
            this.texture = Assets.getTexture(tileSetImagePath);

        }catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public Vector2f texCoordGenerator(int tileID){
        this.total_rows = this.imageHeight / this.tileHeight;
        int currentRow  = (int)Math.floor(tileID / (float)total_rows);
        int currentCol = tileID % total_rows;
        float bottom_left_tx = currentCol * tileWidth / (float)imageWidth;
        float bottom_left_ty = currentRow * tileHeight / (float)imageHeight;
        return new Vector2f(bottom_left_tx, bottom_left_ty);
    }

//    public List<GameObject> generateTiles(int startX, int startY, int tileWidth, int tileHeight){
//        int x = 0;
//        int y = 0;
//        int currentLayer = 0;
//        List<GameObject> tiles = new ArrayList<>();
//
//        for (int tileID : this.data) {
//            Sprite sprite = new Sprite(this.tileWidth, this.tileHeight, this.texture);
//            sprite.setTexCoords(this.texCoordGenerator(tileID - 1));
//            int zIndex = this.zIndices.size() > 0 ? this.zIndices.get(currentLayer) : 0;
//            GameObject tile = new GameObject(new Transform(startX + x * tileWidth, startY + y * tileHeight, tileWidth, tileHeight), sprite, zIndex);
//            if ((tileID == 1)) {
//                tile.setCollidable(true);
//            }
//
//            if ((tileID) == Integer.MIN_VALUE) {
//                currentLayer++;
//                x = 0;
//                y = 0;
//                continue;
//            }
//
//            tiles.add(tile);
//            x++;
//            if (x == this.mapXTiles) {
//                x = 0;
//                y--;
//            }
//
//        }
//
//        return tiles;
//    }
}
