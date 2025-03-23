package utils;

import org.joml.Vector4f;

import java.awt.*;
import java.util.Vector;

public class ColorUtils {

    public static final Vector4f WHITE = getColor(Color.WHITE);
    public static final Vector4f BLACK = getColor(Color.BLACK);

    /* Pokemon Types Colors */
    public static final Vector4f Normal = getColor(Color.decode("#A8A77A"));
    public static final Vector4f Fire = getColor(Color.decode("#EE8130"));
    public static final Vector4f Water = getColor(Color.decode("#6390F0"));
    public static final Vector4f Electric = getColor(Color.decode("#F7D02C"));
    public static final Vector4f Grass = getColor(Color.decode("#7AC74C"));
    public static final Vector4f Ice = getColor(Color.decode("#96D9D6"));
    public static final Vector4f Fighting = getColor(Color.decode("#C22E28"));
    public static final Vector4f Poison = getColor(Color.decode("#A33EA1"));
    public static final Vector4f Ground = getColor(Color.decode("#E2BF65"));
    public static final Vector4f Flying = getColor(Color.decode("#A98FF3"));
    public static final Vector4f Psychic = getColor(Color.decode("#F95587"));
    public static final Vector4f Bug = getColor(Color.decode("#A6B91A"));
    public static final Vector4f Rock = getColor(Color.decode("#B6A136"));
    public static final Vector4f Ghost = getColor(Color.decode("#735797"));
    public static final Vector4f Dragon = getColor(Color.decode("#6F35FC"));
    public static final Vector4f Dark = getColor(Color.decode("#705746"));
    public static final Vector4f Steel = getColor(Color.decode("#B7B7CE"));
    public static final Vector4f Fairy = getColor(Color.decode("#D685AD"));


    public static Vector4f getColor(int r, int g, int b, int a){
        return new Vector4f(r, g, b, a).div(255f);
    }

    /**
        Java.awt.Color -> Vector4f
     **/
    public static Vector4f getColor(Color color){
        return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    /**
     * Grayscale colors
     */
    public static Vector4f getColor(float f){
        return new Vector4f(f,f,f, 1);
    }

    public static Vector4f getColor(float h, float s, float v){
        return getColor(Color.getHSBColor(h / 360f,s,v));
    }

    public static Vector4f shadeColorWithoutAlpha(Vector4f color, float shade){
        Vector4f shadedColor = new Vector4f(color).mul(shade);
        shadedColor.w = color.w();
        return shadedColor;
    }
}
