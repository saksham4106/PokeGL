package utils;

import org.joml.Vector4f;

import java.awt.*;
import java.util.Vector;

public class ColorUtils {

    public static Vector4f WHITE = getColor(Color.WHITE);
    public static Vector4f BLACK = getColor(Color.BLACK);

    public static Vector4f getColor(int r, int g, int b, int a){
        return new Vector4f(r, g, b, a).div(255f);
    }

    public static Vector4f getColor(Color color){
        return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static Vector4f getColor(float f){
        return new Vector4f(f,f,f, 1);
    }


    public static Vector4f shadeColorWithoutAlpha(Vector4f color, float shade){
        Vector4f shadedColor = new Vector4f(color).mul(shade);
        shadedColor.w = color.w();
        return shadedColor;
    }

    public static Vector4f getColor(float h, float s, float v){
        return getColor(Color.getHSBColor(h / 360f,s,v));
    }
}
