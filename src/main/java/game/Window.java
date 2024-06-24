package game;

import audio.AudioContext;
import callback.KeyEventListener;
import callback.MouseEventListener;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scenes.MainScene;
import scenes.Scene;
import scenes.StartingMenuScene;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    public static int width, height;
    private final String title;
    private long window;
    public static Logger LOGGER = LoggerFactory.getLogger(Window.class);
    private static Scene currentScene;
    private static Map<String, Scene> scenes = new HashMap<>();
    public static AudioContext audioContext = new AudioContext();

    private boolean isResized = true;

    public Window() {
        width = 1366;
        height = 706;
        this.title = "PokeGL";
    }

    public void run(){
        init();
        loop();

        audioContext.destroy();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) LOGGER.error("Failed to initialize GLFW");


        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        window = glfwCreateWindow(width, height, this.title, NULL, NULL);
        if (window == NULL) {
            LOGGER.error("Failed to create GLFW window");
        }

        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(0);

        glfwShowWindow(window);

        glfwSetKeyCallback(window, KeyEventListener::keyCallback);
        glfwSetMouseButtonCallback(window, MouseEventListener::mouseButtonCallback);
        glfwSetScrollCallback(window, MouseEventListener::mouseScrollCallback);
        glfwSetCursorPosCallback(window, MouseEventListener::mousePosCallback);
        glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            Window.width = width;
            Window.height = height;
            this.isResized = true;
        });

        audioContext.initialise();
        GL.createCapabilities();

        populateScenes();
        setScene("starting_menu", new StartingMenuScene());

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    private void populateScenes(){
        scenes.put("starting_menu", null);
        scenes.put("main", null);
        scenes.put("battle", null);
    }

    private static void setScene(Scene scene, boolean initialise) {
        if (currentScene != null){
            if(currentScene.throwaway){
                currentScene.destroy();
            }
        }
        currentScene = scene;
        if(initialise){
            currentScene.init();
        }
    }

    public static void removeScene(String sceneName){
        scenes.put(sceneName, null);
    }

    // sceneInstance is nullable
    public static void setScene(String sceneName, Scene sceneInstance){
        Scene scene = scenes.get(sceneName);
        if(scene != null){
            setScene(scene, false);
        }else{
            if(sceneInstance != null){
                scenes.put(sceneInstance.sceneName, sceneInstance);
                setScene(sceneInstance, true);
            }
        }
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public void loop() {

        float beginTime = (float) glfwGetTime();

        double lastTime = glfwGetTime();

        float endTime;
        float dt = -1.0f;


        while (!glfwWindowShouldClose(window)) {
            double currentTime = glfwGetTime();
            if (currentTime - lastTime >= 0.05) {
                currentScene.tick();
            }
            glfwPollEvents();

            glClearColor(0, 0, 0, 1);
            glClear(GL_COLOR_BUFFER_BIT);

            if (dt >= 0) {
                if (isResized) {
                    glViewport(0, 0, width, height);
                    isResized = false;
                }
                currentScene.update(dt);
            }

            glfwSwapBuffers(window);

            endTime = (float) glfwGetTime();

            dt = endTime - beginTime;
            beginTime = endTime;
        }
        currentScene.destroy();
    }
}
