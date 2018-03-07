package net.viraalit.clickingbank.tools;

/**
 * Created by Kim on 19.6.2017.
 */

public class Vector3f {

    public float x, y, z;

    public Vector3f(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float length() {
        return (float) Math.sqrt(pow(x)+pow(y)+pow(z));
    }

    private float pow(float a){
        return a*a;
    }

    public Vector3f set(Vector3f src){
        this.x = src.x;
        this.y = src.y;
        this.z = src.z;
        return this;
    }

    public Vector3f add(Vector3f increase){
        this.x += increase.x;
        this.y += increase.y;
        this.z += increase.z;
        return this;
    }

    public static Vector3f add(Vector3f left, Vector3f right, Vector3f dest) {
        dest.x = left.x + right.x;
        dest.y = left.y + right.y;
        dest.z = left.z + right.z;
        return dest;
    }

    public void normalise() {
        float length = length();
        this.x /= length;
        this.y /= length;
        this.z /= length;
    }

    public float getValue(int i){
        switch(i){
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
        }
        throw new IllegalArgumentException("Vector3f only has 3 dimensions. Only valid values are 0, 1 and 2.");
    }

    public float getX() {
        return x;
    }

    public float getY() { return y; }

    public float getZ() {
        return z;
    }

    public void multiply(float factor) {
        this.x *= factor;
        this.y *= factor;
        this.z *= factor;
    }

    @Override
    public String toString(){
        return new String(this.x +", " +this.y +", " +this.z);
    }
}
