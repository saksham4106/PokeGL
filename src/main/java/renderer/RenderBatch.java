package renderer;

import game.GameObject;
import game.Window;
import org.joml.Vector2f;
import org.joml.Vector4f;
import utils.Assets;
import utils.MathUtil;

import java.util.*;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class RenderBatch implements Comparable<RenderBatch> {
    // Vertex
    // ======
    // Pos               ColorUtils                         tex coords     tex id
    // float, float,     float, float, float, float    float, float   float
    protected final int POS_SIZE = 2;
    protected final int COLOR_SIZE = 4;
    protected final int TEX_COORDS_SIZE = 2;
    protected final int TEX_ID_SIZE = 1;

    protected final int UI_BOOL_SIZE = 1;

    protected final int POS_OFFSET = 0;
    protected final int COLOR_OFFSET = POS_OFFSET + POS_SIZE * Float.BYTES;
    protected final int TEX_COORDS_OFFSET = COLOR_OFFSET + COLOR_SIZE * Float.BYTES;
    protected final int TEX_ID_OFFSET = TEX_COORDS_OFFSET + TEX_COORDS_SIZE * Float.BYTES;
    protected final int UI_BOOL_OFFSET = TEX_ID_OFFSET + UI_BOOL_SIZE * Float.BYTES;
    protected final int VERTEX_SIZE = 10;
    protected final int VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;

    protected LinkedHashSet<GameObject> gameObjects;
    protected boolean hasRoom;
    protected float[] vertices;
    protected final int[] texSlots = {0, 1, 2, 3, 4, 5, 6, 7};

    public List<Texture> textures;
    protected int vaoID, vboID;
    protected int maxBatchSize;
    protected Shader shader;
    public int zIndex;
    private int numSprites;

    public RenderBatch(int maxBatchSize, int zIndex, Shader shader) {
        this.shader = shader;
        shader.compile();

        //this.gameObjects = new GameObject[maxBatchSize];
        this.gameObjects = new LinkedHashSet<>();
        this.maxBatchSize = maxBatchSize;

        // 4 vertices quads
        vertices = new float[maxBatchSize * 4 * VERTEX_SIZE];

        this.hasRoom = true;
        this.textures = new ArrayList<>();
        this.zIndex = zIndex;
        this.numSprites = 0;
    }

    public RenderBatch(int maxBatchSize, int zIndex) {
        this(maxBatchSize, zIndex, Assets.getShader("assets/shaders/vertexBatchShader.glsl"));
    }

    public void start() {
        // Generate and bind a Vertex Array Object
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Allocate space for vertices
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, (long) vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

        // Create and upload indices buffer
        int eboID = glGenBuffers();
        int[] indices = generateIndices();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
        bindVertexPointers();
    }

    protected void bindVertexPointers(){
        glVertexAttribPointer(0, POS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, POS_OFFSET);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, COLOR_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, COLOR_OFFSET);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, TEX_COORDS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, TEX_COORDS_OFFSET);
        glEnableVertexAttribArray(2);

        glVertexAttribPointer(3, TEX_ID_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, TEX_ID_OFFSET);
        glEnableVertexAttribArray(3);

        glVertexAttribPointer(4, UI_BOOL_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, UI_BOOL_OFFSET);
        glEnableVertexAttribArray(4);
    }


    public void addSprite(GameObject gameObject) {

        this.gameObjects.add(gameObject);

        if (gameObject.getSprite().texture != null) {
            if (!textures.contains(gameObject.getSprite().texture)) {
                textures.add(gameObject.getSprite().texture);
            }
        }

        // Add properties to local vertices array
        loadVertexProperties(gameObjects.size() - 1, gameObject);
        this.numSprites++;

        // maxBatchSize - 1
        if (this.numSprites >= this.maxBatchSize) {
            this.hasRoom = false;
        }
    }

    public boolean removeGameObject(GameObject gameObject){
        this.numSprites--;
        return gameObjects.remove(gameObject);
    }

    public boolean containsGameObject(GameObject gameObject){
        return gameObjects.contains(gameObject);
    }

    public void render() {
        boolean rebuffer = false;
        int j = 0;

        for(GameObject gameObject : gameObjects){
            if (gameObject.isDirty()) {
                if (gameObject.getSprite().texture != null && !this.textures.contains(gameObject.getSprite().texture)) {
                    this.textures.add(gameObject.getSprite().texture);
                }
                loadVertexProperties(j, gameObject);
                gameObject.markDirty(false);

                rebuffer = true;
            }
            j++;
        }

        if (rebuffer) {
            glBindBuffer(GL_ARRAY_BUFFER, vboID);
            glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        }


        // Use shader
        shader.use();
        shader.uploadMat4f("uProjection", Window.getCurrentScene().camera.getProjectionMatrix());
        shader.uploadMat4f("uView", Window.getCurrentScene().camera.getViewMatrix());
        for (int i = 0; i < textures.size(); i++) {
            glActiveTexture(GL_TEXTURE0 + i + 1);
            textures.get(i).bind();
        }
        shader.uploadIntArray("uTextures", texSlots);

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, gameObjects.size() * 6, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        for (Texture texture : textures) {
            texture.unbind();
        }
        shader.detach();
    }

    protected void loadVertexProperties(int index, GameObject gameObject) {
        // Find offset within array (4 vertices per sprite)
        int offset = index  * 4 * VERTEX_SIZE;

        Vector4f color = gameObject.getSprite().color;
        Vector2f[] texCoords = gameObject.getSprite().getTexCoords();


        int texId = 0;
        if (gameObject.getSprite().texture != null) {
            for (int i = 0; i < textures.size(); i++) {
                if (textures.get(i) == gameObject.getSprite().texture) {
                    texId = i + 1;
                    break;
                }
            }
        }

        // Add vertices with the appropriate properties

        for (int i = 0; i < 4; i++) {
            float xAdd = 0.0f;
            float yAdd = 0.0f;
            if (i == 1) {
                xAdd = 1.0f;
            } else if (i == 2) {
                xAdd = 1.0f;
                yAdd = 1.0f;
            } else if (i == 3) {
                yAdd = 1.0f;
            }

            // Load position
            Vector2f pos = new Vector2f(gameObject.getTransform().position.x + (xAdd * gameObject.getTransform().scale.x),
                    gameObject.getTransform().position.y + (yAdd * gameObject.getTransform().scale.y));
            if(gameObject.isUIElement()){
                pos = MathUtil.normalizePosition(pos).sub(1, 1);
            }
            vertices[offset] = pos.x;
            vertices[offset + 1] = pos.y;

            // Load color
            vertices[offset + 2] = color.x;
            vertices[offset + 3] = color.y;
            vertices[offset + 4] = color.z;
            vertices[offset + 5] = color.w;

            // Load texture coordinates
            vertices[offset + 6] = texCoords[i].x;
            vertices[offset + 7] = texCoords[i].y;

            // Load texture id
            vertices[offset + 8] = texId;

            vertices[offset + 9] = gameObject.isUIElement() ? 1 : 0;
                

            offset += VERTEX_SIZE;
        }
    }

    protected int[] generateIndices() {
        // 6 indices per quad (3 per triangle)
        int[] elements = new int[6 * maxBatchSize];
        for (int i = 0; i < maxBatchSize; i++) {
            loadElementIndices(elements, i);
        }

        return elements;
    }

    protected void loadElementIndices(int[] elements, int index) {
        int offsetArrayIndex = 6 * index;
        int offset = 4 * index;

        // 3, 2, 0, 0, 2, 1        7, 6, 4, 4, 6, 5
        // Triangle 1
        elements[offsetArrayIndex] = offset + 3;
        elements[offsetArrayIndex + 1] = offset + 2;
        elements[offsetArrayIndex + 2] = offset + 0;

        // Triangle 2
        elements[offsetArrayIndex + 3] = offset + 0;
        elements[offsetArrayIndex + 4] = offset + 2;
        elements[offsetArrayIndex + 5] = offset + 1;
    }

    public boolean hasRoom() {
        return this.hasRoom;
    }

    @Override
    public int compareTo(RenderBatch o) {
        return Integer.compare(this.zIndex, o.zIndex);
    }
}
