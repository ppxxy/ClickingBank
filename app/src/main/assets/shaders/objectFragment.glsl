precision mediump float;

varying vec2 pass_textureCoordinates;

uniform sampler2D diffuseMap;

void main() {

    vec4 textureColour = texture2D(diffuseMap, pass_textureCoordinates);

    if(textureColour.a < 0.5){
        discard;
    }

    gl_FragColor = textureColour;
}
