precision highp float;

varying vec2 pass_textureCoords;

uniform vec3 colour;
uniform sampler2D texture;

void main() {

    vec4 color = texture2D(texture, pass_textureCoords);
    if(color.a < 1.0){
        color.rgb = colour.rgb;
        //discard;
    }
    /*else if(color.r == 1.0 && color.g == 1.0 && color.b == 1.0){
        color.rgb = colour.rgb;
    }
    else{
       color.rgb = vec3(0.0, 0.0, 0.0);
    }*/
    gl_FragColor = color;
}
