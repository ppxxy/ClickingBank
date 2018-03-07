precision mediump float;

varying vec3 textureCoords;

uniform samplerCube cubeMap;

void main() {
    vec4 textureColour = textureCube(cubeMap, textureCoords);
    gl_FragColor = textureColour;
}
