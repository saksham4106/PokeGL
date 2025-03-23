package callback;

import events.EventBus;
import events.MouseClickEvent;
import org.joml.Vector2f;
import utils.MathUtil;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class MouseEventListener {

    public static boolean[] mouseKeys = new boolean[GLFW_MOUSE_BUTTON_LAST];
    public static float xPos, yPos;
    public static double xScrollOffset, yScrollOffset;

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        mouseKeys[button] = action == GLFW_PRESS;
        EventBus.invoke(new MouseClickEvent(button, action, mods));
    }

    public static boolean isMouseButtonPressed(int button){
        if(button > GLFW_MOUSE_BUTTON_LAST) return false;
        return mouseKeys[button];
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        MouseEventListener.xScrollOffset = xOffset;
        MouseEventListener.yScrollOffset = yOffset;
    }

    public static void mousePosCallback(long window, double xPos, double yPos) {
        MouseEventListener.xPos = (float)xPos;
        MouseEventListener.yPos = (float)yPos;
    }

    public static Vector2f getWorld() {
        return MathUtil.screenToWorld(getScreenCoord());
    }

    public static Vector2f getScreenCoord(){
        return new Vector2f(xPos, yPos);
    }


}
