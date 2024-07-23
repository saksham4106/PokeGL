package ui;

import callback.MouseEventListener;
import events.Event;
import events.EventBus;
import events.KeyPressEvent;
import events.TypeEvent;
import fonts.Fonts;
import game.GameObject;
import game.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Renderer;
import renderer.Sprite;
import utils.ColorUtils;
import utils.MathUtil;
import utils.Utils;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;

public class InputObject extends GameObject {
    public boolean focused;
    public GameObject caret;
    private boolean caretVisible = true;
    private String text = "";
    private String toWrite = "";
    private Renderer renderer;
    private boolean canType = true;
    private final float originalX;
    private final float originalY;

    public InputObject(float x, float y, float width, float height){
        EventBus.addListener(this);

        this.sprite = new Sprite(width, height, new Vector4f(0.9f, 0.9f, 0.9f, 1));
        this.setUIElement(true);
        this.setTransform(new Transform(x,y,width,height));
        this.markDirty(true);
        this.zIndex = 1;
        this.originalX = x;
        this.originalY = y;

        this.caret = new GameObject();
        this.caret.setUIElement(true);
        this.caret.setSprite(new Sprite(2, height-2, ColorUtils.BLACK));
        this.caret.setTransform(new Transform(x + 2, y + 1, 2, height - 2));
        this.caret.zIndex = this.zIndex + 1;
    }

    @Override
    public void update(float dt, Renderer renderer) {
        super.update(dt, renderer);
        this.renderer = renderer;
        if(MouseEventListener.isMouseButtonPressed(GLFW_MOUSE_BUTTON_1)){
            if(Utils.cursorInTransform(this.transform)){
                this.focused = true;
                renderer.add(caret);
                caretVisible = true;
            }else{
                this.focused= false;
                renderer.removeGameObject(caret);
                caretVisible = false;
            }
        }

        if(!this.toWrite.isEmpty()){
            this.text += this.toWrite;
            float offset = Fonts.OPEN_SANS_FONT.getStringDimensions(this.toWrite, 0.2f).x;
            this.toWrite = "";
            this.caret.getTransform().position.add(MathUtil.normalizePosition(new Vector2f(offset, 0)));
            this.caret.markDirty(true);
        }
        renderer.drawString(this.text, this.originalX + 2, this.originalY + 4, 0.2f, Fonts.OPEN_SANS_FONT, ColorUtils.BLACK, false);
    }

    int counter = 500;
    @Override
    public void tick() {
        this.canType = !(this.caret.getTransform().position.x > this.transform.position.x + this.transform.scale.x);
        super.tick();
        if(focused && this.toWrite.isEmpty()){
            counter --;
            if(counter < 0){
                if(caretVisible){
//                    caret.getSprite().color.w = 0;
//                    caret.markDirty(true);
                    renderer.removeGameObject(caret);
                    caretVisible = false;
                } else{
//                    caret.getSprite().color.w = 1;
//                    caret.markDirty(true);
                    renderer.add(caret);
                    caretVisible = true;
                }
                counter = 500;
            }
        }
    }

    @Event.EventHandler
    public void onCharPressed(TypeEvent event){
        if(focused && canType){
            this.toWrite += event.getText();
        }
    }

    @Event.EventHandler
    public void onKeyPressed(KeyPressEvent event){
        if(event.getKeyPressed() == GLFW_KEY_BACKSPACE){
            if(!this.text.isEmpty()){
                String last = this.text.substring(this.text.length() - 1);
                float offset = Fonts.OPEN_SANS_FONT.getStringDimensions(last, 0.2f).x;
                this.caret.getTransform().position.sub(MathUtil.normalizePosition(new Vector2f(offset, 0)));
                this.caret.markDirty(true);

                this.text = this.text.substring(0, this.text.length() - 1);
            }
        }
    }

    public String getText() {
        return text;
    }
}
