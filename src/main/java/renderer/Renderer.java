package renderer;

import fonts.FontLoader;
import game.GameObject;
import org.joml.Vector4f;
import ui.ButtonObject;
import ui.TextObject;
import utils.Assets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Renderer {
    private final int batchSize = 10000;
    private final List<RenderBatch> batches;
    private FontRenderer fontRenderer;

    private final Shader defaultShader = Assets.getShader("assets/shaders/vertexBatchShader.glsl");

    public Renderer() {
        this.batches = new ArrayList<>();
        this.fontRenderer = new FontRenderer();
    }

    public void add(GameObject gameObject, Shader shader) {
        boolean added = false;
        for (RenderBatch batch : batches) {
            if (batch.hasRoom() && batch.zIndex == gameObject.zIndex) {
                if (gameObject.getSprite().texture == null || batch.textures.contains(gameObject.getSprite().texture) || batch.textures.size() < 8) {
                    if(batch.shader.equals(shader)){
                        batch.addSprite(gameObject);
                        added = true;
                        break;
                    }
                }
            }
        }

        if (!added) {
            RenderBatch newBatch;
            newBatch = new RenderBatch(batchSize, gameObject.zIndex, shader);

            newBatch.start();
            batches.add(newBatch);
            newBatch.addSprite(gameObject);
            Collections.sort(batches);
        }
    }

    public void add(GameObject gameObject){
        this.add(gameObject, defaultShader);
    }

    public void removeGameObject(GameObject gameObject){
        for(RenderBatch batch : batches){
            if(gameObject instanceof ButtonObject buttonObject){
                this.removeText(buttonObject.textObject);
            }
            if(batch.removeGameObject(gameObject)) break;
        }
    }

    /**
     * Renders string in Retained Mode. TextObject persists till isn't manually removed
     * from Renderer using {@link #removeGameObject(GameObject) removeGameObject}
     */
    public void addText(TextObject textObject){
        textObject.charObjects.forEach(this::add);
    }

    public void removeText(TextObject textObject){
        textObject.charObjects.forEach(this::removeGameObject);
    }

    /**
     * Renders string in Immediate Mode. Is reset every frame
     */
    public void drawString(String string, float x, float y, float scale, FontLoader fontLoader, Vector4f color,
                           boolean isSDFEnabled){
        this.fontRenderer.drawString(string, x, y, scale, fontLoader, color, isSDFEnabled);
    }

    public void addButton(ButtonObject buttonObject){
        this.add(buttonObject);
        this.addText(buttonObject.textObject);
    }

    public void render() {
        for (RenderBatch batch : batches) {
            batch.render();
        }
        this.fontRenderer.render();
    }

    public void clear() {
        this.batches.clear();
    }
}
