package callback;

import game.Camera;
import game.Window;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;
import utils.MathUtil;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class MouseEventListener {

    public static boolean[] mouseKeys = new boolean[GLFW_MOUSE_BUTTON_LAST];
    public static float xPos, yPos;
    public static double xScrollOffset, yScrollOffset;

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        mouseKeys[button] = action == GLFW_PRESS;
//        EventBus.invoke(new MouseClickEvent(button, action, mods));
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
//        float currentX = xPos;
//        currentX = (2.0f * (currentX / Window.width)) - 1.0f;
//        float currentY = yPos;
//        currentY = (2.0f * (1.0f - (currentY / Window.height))) - 1;
//
//        Camera camera = Window.getCurrentScene().camera;
//        Vector4f tmp = new Vector4f(currentX, currentY, 0, 1);
//        Matrix4f inverseView = new Matrix4f(camera.getInverseView());
//        Matrix4f inverseProjection = new Matrix4f(camera.getInverseProjection());
//        tmp.mul(inverseView.mul(inverseProjection));
//        return new Vector2f(tmp.x, tmp.y);

        return MathUtil.screenToWorld(getScreenCoord());
    }

    public static Vector2f getScreenCoord(){
        return new Vector2f(xPos, yPos);
    }


}
