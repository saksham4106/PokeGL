package ui;

import fonts.FontLoader;
import fonts.Fonts;
import game.GameObject;
import game.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Renderer;
import renderer.Sprite;
import utils.ColorUtils;
import utils.MathUtil;

import java.awt.*;

public class ButtonObj extends GameObject {
    private final float x;
    private final float y;
    private final String text;
    private final FontLoader font;
    private final float width;
    private float height;
    private final float scale;

    public ButtonObj(float x, float y, String text, float scale){
        super(new Transform(x, y, 0 ,0), new Sprite(0, 0, new Vector4f()));
        this.x =x;
        this.y = y;
        this.text = text;
        this.scale = scale;
        this.font = Fonts.LEAGUE_SPARTA_FONT;
        Vector2f dimensions = this.font.getStringDimensions(text, scale);
        this.width = dimensions.x / 20;
        this.height = dimensions.y / 2;
        this.setSprite(new Sprite(this.width, this.height , ColorUtils.getColor(Color.GRAY)));
    }

    @Override
    public void update(float dt, Renderer renderer) {
        super.update(dt, renderer);
        renderer.drawString(text, x, y, this.scale, this.font, ColorUtils.WHITE, false);
    }

}
