attribute vec2 position;
attribute vec2 textureCoords;

varying vec2 pass_textureCoords;

void main() {

    gl_Position = vec4(position.x, position.y+0.7, 0.0, 1.0);
    pass_textureCoords = textureCoords;

}
