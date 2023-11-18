#version 410 core

in vec3 fragColor;
in vec3 fragNormal;

out vec4 color;

void
main()
{
    vec3 ambientColor = vec3(0.2, 0.2, 0.2);
    vec3 lightDirection = normalize(vec3(1.0, 1.0, 1.0));
    float diffuseFactor = max(dot(fragNormal, lightDirection), 0.0);
    vec3 diffuseColor = vec3(1.0, 1.0, 1.0);
    vec3 finalColor = fragColor * (ambientColor + diffuseFactor * diffuseColor);

    color = vec4(finalColor, 1.0);
}