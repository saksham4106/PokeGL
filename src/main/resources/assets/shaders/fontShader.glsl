#version 330
layout(location=0) in vec2 aPos;
layout(location = 1) in vec4 aColor;
layout(location = 2) in vec2 aTexCoords;
layout(location = 3) in float aSDFEnabled;

out vec2 fTexCoords;
out vec4 fColor;
out float fSDFEnabled;

uniform mat4 uProjection;

void main() {
    fTexCoords = aTexCoords;
    fColor = aColor;
    fSDFEnabled = aSDFEnabled;
//    gl_Position = uProjection * vec4(aPos, -5,  1);
    gl_Position = vec4(aPos, 1, 1.0);
}

#fragment

#version 330

in vec2 fTexCoords;
in vec4 fColor;
in float fSDFEnabled;

out vec4 color;

uniform sampler2D uFontTexture;

const float width = 0.475;
const float edge = 0.1;

void main(){
    if(fSDFEnabled == 0){
        color = texture(uFontTexture, fTexCoords) * vec4(fColor);
    }else{
        float distance = 1.0 - texture(uFontTexture, fTexCoords).a;
        float alpha = 1.0 - smoothstep(width, width + edge, distance);
        color = vec4(fColor.rgb, alpha) * vec4(1, 1, 1, fColor.a);
    }
}
