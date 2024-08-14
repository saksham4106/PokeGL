package game;

import audio.AudioContext;
import callback.KeyEventListener;
import callback.MouseEventListener;
import events.EventBus;
import events.WindowCloseEvent;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scenes.Scene;
import scenes.StartingMenuScene;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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
    private static Stack<Scene> uiStack = new Stack<>();

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

    private void init() {
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
        glfwSetCharCallback(window, KeyEventListener::charCallback);

        glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            Window.width = width;
            Window.height = height;
            this.isResized = true;
        });

        audioContext.initialise();
        GL.createCapabilities();

        try {
            Files.createDirectories(Paths.get("data"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setScene("starting_menu", new StartingMenuScene());

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
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

    public static void setScene(String sceneName){
        setScene(sceneName, null);
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public static void pushScene(Scene scene){
        uiStack.push(scene);
        scene.init();
    }

    public static void popScene(){
        if(!uiStack.isEmpty()){
            uiStack.pop().clearScene();
        }
    }

    public static Scene getCurrentUIScene(){
        if(!uiStack.isEmpty()){
            return uiStack.peek();
        }
        return null;
    }


    private void loop() {

        float beginTime = (float) glfwGetTime();

        double lastTime = glfwGetTime();

        float endTime;
        float dt = -1.0f;


        while (!glfwWindowShouldClose(window)) {
            double currentTime = glfwGetTime();
            if (currentTime - lastTime >= 0.05) {
                currentScene.tick();
                if(!uiStack.isEmpty()){
                    uiStack.peek().tick();
                }
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

                if(!uiStack.isEmpty()){
                    uiStack.peek().update(dt);
                }
            }

            glfwSwapBuffers(window);

            endTime = (float) glfwGetTime();

            dt = endTime - beginTime;
            beginTime = endTime;
        }
        EventBus.invoke(new WindowCloseEvent());
//        currentScene.save();
        currentScene.destroy();
    }
}
