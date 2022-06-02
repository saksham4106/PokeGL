package renderer;


import fonts.Character;
import fonts.FontLoader;
import game.Window;
import org.joml.Vector2f;
import org.joml.Vector4f;
import utils.Assets;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15C.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15C.glGenBuffers;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class FontRenderer {
    FontBatch fontBatch = new FontBatch(1000, 100);
    public FontRenderer(){
        this.fontBatch.start();
    }
    static class FontBatch{

        public Texture texture;
        public float[] vertices;
        public boolean isSDFEnabled;

        public Shader shader;
        public int MAX_BATCH_SIZE;

        public int size;
        private int[] indices = {
                0, 1, 3,
                1, 2, 3
        };

        int vaoID, vboID;

        public FontBatch(int MAX_BATCH_SIZE, int zIndex){
            this.MAX_BATCH_SIZE = MAX_BATCH_SIZE;
            this.shader = Assets.getShader("assets/shaders/fontShader.glsl");
            this.shader.compile();
            this.vertices = new float[MAX_BATCH_SIZE * 8];
        }
        public void generateEbo() {
            int elementSize = MAX_BATCH_SIZE * 3;
            int[] elementBuffer = new int[elementSize];

            for (int i=0; i < elementSize; i++) {
                elementBuffer[i] = indices[(i % 6)] + ((i / 6) * 4);
            }

            int ebo = glGenBuffers();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);
        }
        public void start(){

            vaoID = glGenVertexArrays();
            glBindVertexArray(vaoID);

            vboID = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vboID);
            glBufferData(GL_ARRAY_BUFFER, Float.BYTES * 8 * MAX_BATCH_SIZE, GL_DYNAMIC_DRAW);

            generateEbo();

            int stride = 9 * Float.BYTES;
            glVertexAttribPointer(0, 2, GL_FLOAT, false, stride, 0);
            glEnableVertexAttribArray(0);

            glVertexAttribPointer(1, 4, GL_FLOAT, false, stride, 2 * Float.BYTES);
            glEnableVertexAttribArray(1);

            glVertexAttribPointer(2, 2, GL_FLOAT, false, stride, 6 * Float.BYTES);
            glEnableVertexAttribArray(2);

            glVertexAttribPointer(3, 1, GL_FLOAT, false, stride, 8 * Float.BYTES);
            glEnableVertexAttribArray(3);
        }

        public void addCharacter(float x, float y, float scale, float width, float height, Vector4f color,
                                 Vector2f[] texCoords){
            if (size >= MAX_BATCH_SIZE - 4) {
                render();
            }

            int offset = size * 9;
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
                vertices[offset] = x + (xAdd * width * scale);
                vertices[offset + 1] = y + (yAdd * height * scale);

                // Load color
                vertices[offset + 2] = color.x;
                vertices[offset + 3] = color.y;
                vertices[offset + 4] = color.z;
                vertices[offset + 5] = color.w;

                // Load texture coordinates
                vertices[offset + 6] = texCoords[i].x;
                vertices[offset + 7] = texCoords[i].y;

                vertices[offset + 8] = isSDFEnabled ? 1 : 0;

                offset += 9;
                size += 1;
            }

        }

        public void render(){
            if(size != 0){
                glBindBuffer(GL_ARRAY_BUFFER, vboID);
                glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

                // Use shader
                shader.use();
                shader.uploadMat4f("uProjection", Window.getCurrentScene().camera.getProjectionMatrix());
                //shader.uploadMat4f("uView", Window.getCurrentScene().camera.getViewMatrix());
                glActiveTexture(GL_TEXTURE0);
                texture.bind();

                glBindVertexArray(vaoID);
                glEnableVertexAttribArray(0);
                glEnableVertexAttribArray(1);

                glDrawElements(GL_TRIANGLES, size * 6, GL_UNSIGNED_INT, 0);
//
                glDisableVertexAttribArray(0);
                glDisableVertexAttribArray(1);
                glBindVertexArray(0);

                texture.unbind();
                shader.detach();

                size = 0;
            }

        }
    }

    public void drawString(String string, float x, float y, float scale, FontLoader fontLoader, Vector4f color,
                           boolean isSDFEnabled){
        float currentX = x;
        float currentY = y;

        // FIXME
        String downCharacters = "qypgj";
        String upCharacters = "\"'";
        String midCharacters = "-";

        float spacePadding = fontLoader.getCharacter(' ').xAdvance * scale;

        fontBatch.texture = fontLoader.texture;
        fontBatch.isSDFEnabled = isSDFEnabled;
        for(int charID = 0; charID < string.length(); charID++){

            char c = string.charAt(charID);
            Character character = fontLoader.getCharacter(c);
            if(c == '\n'){
                y -= fontLoader.lineHeight * scale;
                currentX = x;
                continue;
            }else if(c == '\t'){
                currentX += spacePadding * 4;
                continue;
            }

            float yOffset = downCharacters.contains(String.valueOf(c)) ? 20 * scale : 0;
            yOffset = upCharacters.contains(String.valueOf(c)) ? -40 * scale : yOffset;
            yOffset = midCharacters.contains(String.valueOf(c)) ? -20 * scale : yOffset;

            fontBatch.addCharacter(currentX, currentY - yOffset, scale, character.width, character.height, color,
                    character.getTexCoords(fontLoader.imageWidth, fontLoader.imageHeight));
            currentX += character.xAdvance * scale;
        }
    }

    public void render(){
        fontBatch.render();
    }

}