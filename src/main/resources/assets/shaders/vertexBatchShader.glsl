#version 330 core
layout (location=0) in vec2 aPos;
layout (location=1) in vec4 aColor;
layout (location=2) in vec2 aTexCoords;
layout (location=3) in float aTexId;
layout (location=4) in int aUIBool;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 fColor;
out vec2 fTexCoords;
out float fTexId;

void main()
{
    fColor = aColor;
    fTexCoords = aTexCoords;
    fTexId = aTexId;
    gl_Position = uProjection * uView * vec4(aPos, 1, 1);
    if(aUIBool == 0){
        gl_Position = uProjection * uView * vec4(aPos, 1.0, 1.0);
    }else {
        gl_Position = vec4(aPos, 1.0, 1.0);
    }

}

#fragment
    #version 330 core

in vec4 fColor;
in vec2 fTexCoords;
in float fTexId;

uniform sampler2D uTextures[8];

out vec4 color;

void main()
{
    if (fTexId > 0) {
        int id = int(fTexId);
        switch (int(fTexId)) {
            case 1:
            color = fColor * texture(uTextures[1], fTexCoords);
            break;
            case 2:
            color = fColor * texture(uTextures[2], fTexCoords);
            break;
            case 3:
            color = fColor * texture(uTextures[3], fTexCoords);
            break;
            case 4:
            color = fColor * texture(uTextures[4], fTexCoords);
            break;
            case 5:
            color = fColor * texture(uTextures[5], fTexCoords);
            break;
            case 6:
            color = fColor * texture(uTextures[6], fTexCoords);
            break;
            case 7:
            color = fColor * texture(uTextures[7], fTexCoords);
            break;
        }
        //color = fColor * texture(uTextures[id], fTexCoords);

    } else {
        color = fColor;
        //color = vec4(fTexCoords, 0, 1);
    }
}

