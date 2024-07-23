package events;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class KeyPressEvent extends Event{
    private int keyPressed;

    public KeyPressEvent(int key, int action){
        if(action == GLFW_PRESS){
            this.keyPressed = key;
        }
    }

    public int getKeyPressed() {
        return keyPressed;
    }
}
