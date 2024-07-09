package utils;

import audio.Sound;
import renderer.Shader;
import renderer.Spritesheet;
import renderer.Texture;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Assets {

    public static Map<String, Shader> shaders = new HashMap<>();
    public static Map<String, Spritesheet> spritesheets = new HashMap<>();
    public static Map<String, Texture> textures = new HashMap<>();
    public static Map<String, Sound> sounds = new HashMap<>();

    public static Shader getShader(String name) {
        if(shaders.containsKey(name)) {
            return shaders.get(name);
        }else {
            Shader shader = new Shader(name);
            shaders.put(name, shader);
            return shader;
        }
    }

    public static Texture getTexture(String name) {
        if (textures.containsKey(name)) {
            return textures.get(name);
        } else {
            Texture texture = new Texture(name);
            textures.put(name, texture);
            return texture;
        }
    }

    public static Sound getSound(String name) {
        return sounds.getOrDefault(name, null);
    }

    public static Sound getSound(String name, Sound sound) {
        if (getSound(name) != null) {
            return getSound(name);
        }
        sounds.put(name, sound);
        return sound;
    }

    public static Spritesheet getSpritesheet(String name) {
        return spritesheets.getOrDefault(name, null);
    }

    public static Spritesheet getSpritesheet(String name, Spritesheet spritesheet) {
        if(name.isEmpty()) name = spritesheet.texture.filepath;
        if (getSpritesheet(name) != null) {
            return getSpritesheet(name);
        }

        spritesheets.put(name, spritesheet);
        return spritesheet;
    }

    public static String readFileAsString(String path) {
        return new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(path))).lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public static InputStream readFileAsInputStream(String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }

}
