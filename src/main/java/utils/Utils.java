package utils;

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

}
