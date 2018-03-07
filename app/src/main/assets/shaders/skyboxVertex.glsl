attribute vec3 position;

varying vec3 textureCoords;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main() {

    mat4 tempViewMatrix = viewMatrix;
    tempViewMatrix[3].xyz = vec3(0.0, 0.0, 0.0);
    gl_Position = projectionMatrix * tempViewMatrix * vec4(position, 1.0);
    textureCoords = position;

}
