package callback;

import static org.lwjgl.glfw.GLFW.*;

public class KeyEventListener {
    public static boolean[] keys = new boolean[GLFW_KEY_LAST];

    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW_KEY_UNKNOWN) { return; }

        if(action == GLFW_PRESS){
            keys[key] = true;
        }else if(action == GLFW_RELEASE){
            keys[key] = false;
        }
    }

    public static boolean isKeyPressed(int key){
        if(key > GLFW_KEY_LAST) return false;
        return keys[key];
    }
}
