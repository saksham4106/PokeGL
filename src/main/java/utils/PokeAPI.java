package utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class PokeAPI {

    public static void main(String[] args) throws IOException {


        //String data = Assets.readFileAsString("assets/pokemons/1_bulbasaur/bulbasaur.json");

        for(int i = 679; i <= 721; i++){
            String data = getJsonData("https://pokeapi.co/api/v2/pokemon/" + i);
            JSONObject obj = new JSONObject(data);

            JSONObject sprites = obj.getJSONObject("sprites");
            String frontFace = sprites.getString("front_default");
            String backFace = null;
            try {
                backFace = sprites.getString("back_default");
            } catch (JSONException e) {

            }
            if(backFace!= null) downloadFile(backFace,"src/main/resources/assets/pokemonImages/" + i + "_backFace.png");
            downloadFile(frontFace,"src/main/resources/assets/pokemonImages/" + i + "_frontFace.png");


        }
//
//        short id = (short) obj.getInt("id");
//        int base_experience = obj.getInt("base_experience");
//        String name = obj.getString("name");
//
//
//        JSONArray types = obj.getJSONArray("types");
//        String[] typesArray = new String[2];
//        for(int i = 0; i < types.length(); i++){
//            JSONObject typeObject = types.getJSONObject(i).getJSONObject("type");
//            if(i <= 2) typesArray[i] = typeObject.getString("name");
//        }
//
//        String baseType = typesArray[0];
//        String secondType = typesArray[1];

    }

    public static void downloadFile(String url, String fileName) {
        try (InputStream in = new URL(url).openStream()) {
            Files.copy(in, Paths.get(fileName));
        } catch (IOException e) {
            if(e instanceof FileAlreadyExistsException){

            }else{
                throw new RuntimeException(e);
            }

        }
    }

    public static String getJsonData(String url_string) throws IOException {
        URL url = new URL(url_string);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();
        if(responseCode != 200){
            throw new RuntimeException("HTTPResponseCode: " + responseCode);
        }

        return new BufferedReader(new InputStreamReader(url.openStream())).lines().collect(Collectors.joining(System.lineSeparator()));

    }
}
