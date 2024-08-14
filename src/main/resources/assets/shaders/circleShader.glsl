#version 330 core
layout (location=0) in vec3 aPos;
layout (location=1) in vec4 aColor;
layout (location=2) in float aThickness;
layout (location=3) in float aFade;
layout (location=4) in vec2 localPos;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 fColor;
out float fThickness;
out float fFade;
out vec2 fLocalPos;

void main()
{
    fColor = aColor;
    fThickness = aThickness;
    fFade = aFade;
    fLocalPos = localPos;

    gl_Position = uProjection * uView * vec4(aPos, 1.0);
}


#fragment
#version 330 core

in vec4 fColor;
in float fThickness;
in float fFade;
in vec2 fLocalPos;

out vec4 color;


void main()
{
    float distance = length(fLocalPos);
    if(distance < 0.5){
        color.rgb = vec3(0);
    }else{
        color.rgb = vec3(1);
    }

    distance = smoothstep(0.5, 0.5 + fFade, distance);
    color = vec4(distance) * fColor;

    //color.rgb = vec3(distance);
}
