package ui;

import audio.Sound;
import callback.MouseEventListener;
import fonts.FontLoader;
import fonts.Fonts;
import game.GameObject;
import game.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Renderer;
import renderer.Sprite;
import utils.Assets;
import collision.CollisionDetection;
import utils.ColorUtils;
import utils.MathUtil;
import utils.Utils;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;

public class ButtonObject extends GameObject {

    private final Vector4f darkerColor;
    private float shade = 2/3f;

    public TextObject textObject;
    public IClickable onClick;
    public boolean disabled = false;

    public ButtonObject(float startX, float startY, String label, IClickable onClick){
        this(startX, startY, label, onClick, new Sprite(0, 0, new Vector4f(0.5f, 0.5f, 0.5f, 0.7f)), 1);
    }

    public ButtonObject(float startX, float startY, String label, IClickable onClick, Sprite sprite){
        this(startX, startY, label, onClick, sprite, 25);
    }

    public ButtonObject(float startX, float startY, IClickable onClick, Sprite sprite, int zIndex) {
        this(startX, startY, "", onClick, sprite, zIndex);
    }

    public ButtonObject(float startX, float startY, String label, IClickable onClick, Sprite sprite, int zIndex){
        this(startX, startY, label, 0.5f, onClick, sprite, zIndex);
    }

    public  ButtonObject(float startX, float startY, String label, float scale, IClickable onClick, Sprite sprite, int zIndex){
        this(startX, startY, label, scale, onClick, sprite, zIndex, Fonts.ATARI_CLASSIC_FONT, ColorUtils.BLACK);
    }

    public ButtonObject(float startX, float startY, String label, float scale, IClickable onClick, Sprite sprite, int zIndex,
                        FontLoader fontLoader, Vector4f textColor){
        int xPadding = 5;
        int yPadding = 5;
        float buttonWidth = 0;
        float buttonHeight = 0;

        if(!label.isEmpty()) {
            TextObject textObject = new TextObject(startX + xPadding, startY + yPadding,
                    scale, label, fontLoader, zIndex + 1, true, textColor);

            textObject.addPos(new Vector2f(0, textObject.bottomEnd));

            buttonWidth = textObject.totalWidth;
            buttonHeight = textObject.totalHeight + textObject.bottomEnd;

            if (sprite.width > buttonWidth) {
                textObject.addPos(new Vector2f((sprite.width - buttonWidth) / 2f, 0));
            }
            if (sprite.height > buttonHeight) {
                textObject.addPos( new Vector2f(0,(sprite.height - buttonHeight) / 2f));
            }

            this.textObject = textObject;
        }

        buttonWidth = Math.max(buttonWidth, sprite.width) + 2 * xPadding;
        buttonHeight = Math.max(buttonHeight, sprite.height) + 2 * yPadding;

        Transform transform = new Transform(startX, startY, buttonWidth, buttonHeight);
        sprite.width = buttonWidth;
        sprite.height = buttonHeight;

        this.sprite = sprite;
        this.setUIElement(true);
        this.zIndex = zIndex;
        this.setTransform(transform);

        this.onClick = onClick;
        this.darkerColor = ColorUtils.shadeColorWithoutAlpha(this.sprite.color, shade);
    }


    @Override
    public void update(float dt, Renderer renderer) {
        if(disabled){
            this.setColor(new Vector4f(0.5f, 0.5f, 0.5f, 1));
            return;
        }

        if(Utils.cursorInTransform(this.transform)){
            onHover();
        }else{
            if(this.darkerColor.equals(this.sprite.color)){
                this.setColor(ColorUtils.shadeColorWithoutAlpha(this.sprite.color, 1/shade));
            }
        }
    }

    int cooldown = 10;
    @Override
    public void tick() {
        super.tick();
        cooldown --;

    }

    public void onClick(){
        if(cooldown < 0){
            this.onClick.onClick();
            Sound sound = Assets.getSound("assets/sounds/buttonpress.ogg", new Sound("assets/sounds/buttonpress.ogg", false));
            sound.play();
            cooldown = 10;
        }

    }

    public void onHover(){
        if(this.darkerColor.x != this.sprite.color.x){
            this.setColor(this.darkerColor);
        }

        if(MouseEventListener.isMouseButtonPressed(GLFW_MOUSE_BUTTON_1)){
            onClick();
        }
    }

    public void setShade(float shade){
        this.shade = shade;
    }

    public interface IClickable{
        void onClick();
    }

    public void disable(boolean disabled){
        this.disabled = disabled;
    }
}
