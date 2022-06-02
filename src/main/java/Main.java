import game.Window;

public class Main {
    public static void main(String[] args) {
        Window window = new Window();
        window.run();
    }

//    private static void generateData(){
//        int MAX_POKEMONS = 898;
//        for(int id = 1 ; id <= MAX_POKEMONS; id++){
//            try {
//                String jsonData = PokeAPI.getJsonData("https://pokeapi.co/api/v2/pokemon/" + id + "/");
//                JSONObject obj = new JSONObject(jsonData);
//                String name = obj.getString("name");
//
//                File file = new File("src/main/resources/assets/pokemons/" + id + "_" + name + "/" + name + ".json");
//                file.createNewFile();
//                FileWriter myWriter = new FileWriter(file.getPath());
//                myWriter.write(jsonData);
//                myWriter.close();
//
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//
//        }
//
//    }
}
