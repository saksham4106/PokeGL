package fonts;

import org.joml.Vector4f;

public class Text {
    public String text;
    public FontLoader fontLoader;
    public Vector4f color;
    public float scale;

    public Text(String text, FontLoader fontLoader, Vector4f color, float scale) {
        this.text = text;
        this.fontLoader = fontLoader;
        this.color = color;
        this.scale = scale;
    }

    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }

    public FontLoader getFontLoader() {
        return fontLoader;
    }

    public void setFontLoader(FontLoader fontLoader) {
        this.fontLoader = fontLoader;
    }

    public Vector4f getColor() {
        return color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }
}
