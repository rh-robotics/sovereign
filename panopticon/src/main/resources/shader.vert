#version 410 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 color;
layout(location = 2) in vec3 normal;

out vec3 fragColor;
out vec3 fragNormal;

uniform mat4 modelViewProjection;

void
main()
{
    gl_Position = modelViewProjection * vec4(position, 1.0);
    fragColor = color;
    fragNormal = normalize(mat3(transpose(inverse(modelViewProjection))) * normal);
}