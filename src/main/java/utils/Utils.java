package utils;

import callback.MouseEventListener;
import collision.CollisionDetection;
import game.Transform;
import org.joml.Vector2f;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Utils {
    public static Random random = new Random();

    public static <T> T getRandomElement(List<T> list){
        return list.get(random.nextInt(list.size()));
    }

    public static void createFileIfDoesntExist(String path) throws IOException {
        File file = new File(path);
        if(!file.exists()){
            file.createNewFile();
        }
    }

    public static boolean cursorInTransform(Transform t){
        Transform transform = new Transform(MouseEventListener.getScreenCoord(), new Vector2f(0, 0));
        Vector2f position = t.position;
        Vector2f scale = t.scale;
        Vector2f mousePosition = MathUtil.normalizeTransform(transform).position;
        return CollisionDetection.pointInBox(mousePosition.x, -mousePosition.y,
                position.x, position.y, scale.x, scale.y );
    }

    public static String capitalize(String str){
        String[] words = str.split(" ");
        for (int i = 0; i < words.length; i++){
            words[i] = words[i].substring(0,1).toUpperCase() + words[i].substring(1);
        }
        return String.join(" ", words);
    }


}
