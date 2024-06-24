package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextureAtlasCreator {

    public static void main(String[] args) throws IOException {
        int pokemonNum = 721;
        File imagesDir = new File("src/main/resources/assets/pokemonImages/");
        BufferedImage textureAtlas = new BufferedImage((int)Math.ceil(Math.sqrt(pokemonNum)) * 96,
                (int)Math.floor(Math.sqrt(pokemonNum)) * 96, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics2D = textureAtlas.createGraphics();
        int x = 0;
        int y = 0;
        List<File> pokemonFiles = new ArrayList<>();
        for(int i = 1; i <= 721; i++){
            File file = new File("src/main/resources/assets/pokemonImages/" + i + "_frontFace.png");
            pokemonFiles.add(file);
        }


        for (File file : pokemonFiles) {
                try{
                    BufferedImage pokemonImage = ImageIO.read(file);
                    graphics2D.drawImage(pokemonImage, x * 96, y * 96, 96, 96, (img, infoflags, x1, y1, width, height) -> false);
                    x += 1;
                    if(x > 27){
                        x = 0;
                        y += 1;
                    }
                }catch (Exception ignored){

                }


        }
//        BufferedImage bul = ImageIO.read(new File("src/main/resources/assets/pokemonImages/1_backFace.png"));
//        BufferedImage bul1 = ImageIO.read(new File("src/main/resources/assets/pokemonImages/1_frontFace.png"));

//        graphics2D.drawImage(bul1, 96, 0, 96,96,(img, infoflags, x, y, width, height) -> false );
        ImageIO.write(textureAtlas, "png", new File("frontFaces.png"));


//        IntBuffer width = BufferUtils.createIntBuffer(1);
//        IntBuffer height = BufferUtils.createIntBuffer(1);
//        IntBuffer channels = BufferUtils.createIntBuffer(1);
//
//        ByteBuffer image = stbi_load("src/main/resources/assets/pokemonImages/1_backFace.png",  width, height, channels, 4);
//        int width1 = width.get(0);
//        int height1 = height.get(0);
//
//        ByteBuffer image2 = stbi_load("src/main/resources/assets/pokemonImages/1_frontFace.png",  width, height, channels, 4);
//        int width2 = width.get(0);
//        int height2 = height.get(0);
//
//
//
//        ByteBuffer combinedImage = BufferUtils.createByteBuffer(image.capacity() + image2.capacity() * 4);
//        for (int i = 0; i < image.capacity(); i++) {
//            byte b = image.get(i);
//            combinedImage.put(b);
//        }
//
//        for (int i = 0; i < image2.capacity(); i++) {
//            byte b = image2.get(i);
//            combinedImage.put(b);
//        }
//
//
//
//        stbi_write_png("test.png", width1, height1, 4, combinedImage, 0);


    }
}
