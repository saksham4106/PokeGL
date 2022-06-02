package ui;

import audio.Sound;
import callback.MouseEventListener;
import fonts.Fonts;
import fonts.Text;
import game.GameObject;
import game.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Sprite;
import utils.Assets;
import utils.CollisionDetection;
import utils.ColorUtils;
import utils.MathUtil;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;

public class ButtonObject extends GameObject {
//    protected final ButtonObject.IClickable onClick;
//    public TextObject textObject;
//
//    private int x;
//    private int y;
//    private float width;
//    private float height;
//    private Vector4f darkerColor;
//    private float shade = 2/3f;
//
//    public float xPadding = 5;
//    public float yPadding = 5;
//
//    public ButtonObject(int x, int y, Text text, ButtonObject.IClickable onClick, int zIndex, Sprite sprite){
//        this(x, y, new TextObject(x, y, text, zIndex + 1, false), onClick, zIndex, sprite, false);
//    }
//
//    public ButtonObject(int x, int y, Text text, ButtonObject.IClickable onClick, int zIndex, Sprite sprite, boolean isUIElement){
//        this(x, y, new TextObject(x, y, text, zIndex + 1, isUIElement), onClick, zIndex, sprite, isUIElement);
//    }
//
//    public ButtonObject(int x, int y, TextObject textObject, ButtonObject.IClickable onClick, int zIndex, Sprite sprite, boolean isUIElement){
//        this.setUIElement(isUIElement);
//        this.width = textObject.totalWidth + 2 * xPadding;
//        this.height = textObject.totalHeight + 2 * yPadding;
//
//        if(sprite.width != 0){
//            this.width = Math.max(this.width, sprite.width + 2 * xPadding);
//        }
//        if(sprite.height != 0){
//            this.height = Math.max(this.height, sprite.height + 2 * yPadding);
//        }
//
//
//        this.setTransform(new Transform(x, y, width, height));
//        this.sprite = new Sprite((int)width, (int)height, sprite.texture,
//                    sprite.color);
//        this.zIndex = zIndex;
//
//        this.x = x;
//        this.y = y;
//
//        this.textObject = textObject;
//
////        this.textObject.addY(this.textObject.bottomEnd + yPadding);
////        this.textObject.addX(xPadding);
//        Vector2f add = normalizePosition(new Vector2f(xPadding, this.textObject.bottomEnd + yPadding));
//        System.out.println(add.x + " " + add.y);
//        this.textObject.addX(add.x);
//        this.textObject.addY(add.y);
//        //this.textObject.addY(this.textObject.bottomEnd + yPadding + (this.height - this.textObject.totalHeight) / 2f);
//        //this.textObject.addX(xPadding + (this.width - this.textObject.totalWidth) / 2f);
//
//        this.markDirty(true);
//        this.onClick = onClick;
//
//        this.darkerColor = ColorUtils.shadeColorWithoutAlpha(this.sprite.color, shade);
//    }
//
//
//    public void onClick(){
//        Sound sound = Assets.getSound("assets/sounds/buttonpress.ogg", new Sound("assets/sounds/buttonpress.ogg", false));
//        sound.play();
//        this.onClick.onClick(this);
//    }
//
//    public void onHover(){
//        if(this.darkerColor.x != this.sprite.color.x){
//            this.sprite.color.set(this.darkerColor);
//            this.textObject.setColor(ColorUtils.shadeColorWithoutAlpha(this.textObject.color, shade));
//            this.markDirty(true);
//        }
//
//        if(MouseEventListener.isMouseButtonPressed(GLFW_MOUSE_BUTTON_1)){
//            onClick();
//        }
//    }
//
//    @Override
//    public void update(float dt) {
//        super.update(dt);
//        //Vector2f mousePoint = MouseEventListener.getWorld();
//        Vector2f mousePoint = normalizePosition(new Vector2f(MouseEventListener.xPos, MouseEventListener.yPos));
//
//        System.out.println(mousePoint.x + " " + mousePoint.y);
//        if(CollisionDetection.pointInBox(mousePoint.x, mousePoint.y, this.x, this.y, this.width, this.height)){
//            onHover();
//        }else{
//            if(this.darkerColor.equals(this.sprite.color)){
//                this.sprite.color.set(ColorUtils.shadeColorWithoutAlpha(this.sprite.color, 1/shade));
//                this.textObject.setColor(ColorUtils.shadeColorWithoutAlpha(this.textObject.color, 1/shade));
//                this.markDirty(true);
//            }
//        }
//    }
//
//    public interface IClickable{
//        void onClick(ButtonObject button);
//    }

    int xPadding = 5;
    private Vector4f darkerColor;
    private float shade = 2/3f;

    int yPadding = 5;
    public TextObject textObject;
    IClickable onClick;

    public ButtonObject(float startX, float startY, String label, IClickable onClick){
        this(startX, startY, label, onClick, new Sprite(0, 0, new Vector4f(0.5f, 0.5f, 0.5f, 0.7f)));

    }

    public ButtonObject(float startX, float startY, String label, IClickable onClick, Sprite sprite){
        // Create and Position Text Object
        TextObject textObject = new TextObject(startX + xPadding, startY + yPadding,
                0.5f, label, Fonts.OPEN_SANS_FONT, 26, true, new Vector4f(1, 1, 1, 1));
        textObject.addY(MathUtil.normalizePosition(new Vector2f(0, textObject.bottomEnd)).y);


        float buttonWidth = textObject.totalWidth + 2 * xPadding;
        float buttonHeight = textObject.totalHeight + 2 * yPadding + textObject.bottomEnd;

        //Re-evaluate Button size
        buttonWidth = Math.max(buttonWidth, sprite.width + 2 * xPadding);
        buttonHeight = Math.max(buttonHeight, sprite.height + 2 * yPadding + textObject.bottomEnd);
        Transform transform = new Transform(startX, startY, buttonWidth, buttonHeight);
        sprite.width = buttonWidth;
        sprite.height = buttonHeight;

        this.sprite = sprite;
        this.setUIElement(true);
        this.zIndex = 25;
        this.setTransform(transform);
        this.markDirty(true);
        this.textObject = textObject;

        this.onClick = onClick;
        this.darkerColor = ColorUtils.shadeColorWithoutAlpha(this.sprite.color, shade);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        Transform transform = new Transform(MouseEventListener.getScreenCoord(), new Vector2f(0, 0));
        Vector2f position = this.transform.position;
        Vector2f scale = this.transform.scale;
        Vector2f mousePosition = MathUtil.normalizeTransform(transform).position;
        if(CollisionDetection.pointInBox(mousePosition.x, -mousePosition.y,
               position.x, position.y, scale.x, scale.y )){
            onHover();
        }else{
            if(this.darkerColor.equals(this.sprite.color)){
                this.sprite.color.set(ColorUtils.shadeColorWithoutAlpha(this.sprite.color, 1/shade));
                this.textObject.setColor(ColorUtils.shadeColorWithoutAlpha(this.textObject.color, 1/shade));
                this.markDirty(true);
            }
        }
    }

    public void onClick(){
        this.onClick.onClick(this);
        Sound sound = Assets.getSound("assets/sounds/buttonpress.ogg", new Sound("assets/sounds/buttonpress.ogg", false));
        sound.play();

    }

    public void onHover(){
        if(this.darkerColor.x != this.sprite.color.x){
            this.sprite.color.set(this.darkerColor);
            this.textObject.setColor(ColorUtils.shadeColorWithoutAlpha(this.textObject.color, shade));
            this.markDirty(true);
        }

        if(MouseEventListener.isMouseButtonPressed(GLFW_MOUSE_BUTTON_1)){
            onClick();
        }
    }

    public interface IClickable{
        void onClick(ButtonObject button);
    }


}
