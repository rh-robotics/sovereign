#version 410 core

in vec3 fragColor;
in vec3 fragNormal;
in vec3 fragPosition;

layout (location = 0) out vec4 color;

uniform vec3 viewPosition;

/* Implements the Phong reflection model. */
void
main()
{
    /* Setup. */
    vec3 lightColor = vec3(1.0, 1.0, 1.0);
    vec3 lightPosition = vec3(1.2, 1.0, 2.0);

    /* Ambient lighting calculations. */
    float ambientStregth = 0.05;
    vec3 ambient = lightColor * ambientStregth;

    /* Diffuse lighting calculations. */
    vec3 normal = normalize(fragNormal);
    vec3 lightDirection = normalize(lightPosition - fragPosition);
    float diff = max(dot(normal, lightDirection), 0.0);
    vec3 diffuse = diff * lightColor;

    /* Specular lighting calculations. */
    float specularStrength = 0.5;
    vec3 viewDirection = normalize(viewPosition - fragPosition);
    vec3 reflectionDirection = reflect(-lightDirection, normal);
    float spec = pow(max(dot(viewDirection, reflectionDirection), 0.0), 32);
    vec3 specular = specularStrength * spec * lightColor;

    /* Add it all together now! */
    /* color = vec4(fragColor + (ambient + diffuse + specular), 1.0); */
    color = vec4(fragColor + (ambient + diffuse), 1.0);
}