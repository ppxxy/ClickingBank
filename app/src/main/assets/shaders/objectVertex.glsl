attribute vec3 position;
attribute vec2 textureCoords;
attribute vec3 normals;

varying vec2 pass_textureCoordinates;

uniform mat4 projectionViewMatrix;
uniform mat4 transformationMatrix;

void main() {

    gl_Position = projectionViewMatrix * transformationMatrix * vec4(position.xyz, 1.0);
    pass_textureCoordinates = textureCoords;

}
