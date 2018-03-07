package net.viraalit.clickingbank.models;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Kim on 20.6.2017.
 */

public class Texture {

    public final int id;
    private final int type;

    /**
     * Creates normal 2D texture with given id and size.
     * @param id Id this texture is associated with.
     */
    protected Texture(int id){
        this.id = id;
        this.type = GLES20.GL_TEXTURE_2D;
    }

    /**
     * Creates texture with given id and type.
     * @param id Id this texture is associated with.
     * @param type Texture type. GLES20 has different final type variables.
     */
    protected Texture(int id, int type){
        this.id = id;
        this.type = type;
    }

    /**
     * Binds this texture to be used on OpenGL rendering.
     * @param unit Unit value to bind this texture to.
     */
    public void bindToUnit(int unit){
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + unit);
        GLES20.glBindTexture(type, id);
    }

    public void bind(){
        GLES20.glBindTexture(type, id);
    }

    public static Texture createTexture(int id){
        return new Texture(id);
    }

    /**
     * Removes this texture from the memory.
     */
    public void delete(){
        GLES20.glDeleteTextures(1, new int[]{id}, 0);
    }


    public static class Loader{

        private boolean clampEdges = false, mipmap = false, nearest = false;

        public boolean isClampEdges() {
            return clampEdges;
        }

        public boolean isMipmap() {
            return mipmap;
        }

        public boolean isNearest() {
            return nearest;
        }

        /**
         * Loads texture data from the path. Saves Texture to OpenGL and makes it into a Texture object.
         * @return Texture object containing texture.
         */
        public Texture load(Bitmap bitmap){
            if(bitmap == null){
                throw new NullPointerException();
            }
            int[] ids = new int[1];
            GLES20.glGenTextures(1, ids, 0);
            //if(ids[0] != 0){
                Texture texture = new Texture(ids[0], GLES20.GL_TEXTURE_2D);
                texture.bindToUnit(0);
                if (isMipmap()) {
                    GLES20.glGenerateMipmap(texture.type);
                    GLES20.glTexParameteri(texture.type, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
                    GLES20.glTexParameteri(texture.type, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
                } else if (isNearest()) {
                    GLES20.glTexParameteri(texture.type, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
                    GLES20.glTexParameteri(texture.type, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
                } else {
                    GLES20.glTexParameteri(texture.type, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
                    GLES20.glTexParameteri(texture.type, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
                }
                if (isClampEdges()) {
                    GLES20.glTexParameteri(texture.type, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
                    GLES20.glTexParameteri(texture.type, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
                } else {
                    GLES20.glTexParameteri(texture.type, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
                    GLES20.glTexParameteri(texture.type, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
                }
                GLUtils.texImage2D(texture.type, 0, bitmap, 0);

                bitmap.recycle();
                return texture;
            /*}
            else{
                throw new RuntimeException("Can't generate OpenGL id for the requested texture.");
            }*/
        }

        public Texture loadCubeMap(String[] textureFiles, AssetManager assets) throws IOException {
	        int[] ids = new int[1];
	        GLES20.glGenTextures(1, ids, 0);
	        Texture texture = new Texture(ids[0], GLES20.GL_TEXTURE_CUBE_MAP);
	        texture.bindToUnit(0);
	        for(int i = 0; i < textureFiles.length; i++){
	        	InputStream stream = assets.open(textureFiles[i]);
		        Bitmap bitmap = BitmapFactory.decodeStream(stream);
		        try {
			        stream.close();
		        } catch(IOException e){
		        	e.printStackTrace();
		        }
		        GLUtils.texImage2D(GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, bitmap, 0);
		        bitmap.recycle();
	        }
	        GLES20.glTexParameteri(GLES20.GL_TEXTURE_CUBE_MAP, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
	        GLES20.glTexParameteri(GLES20.GL_TEXTURE_CUBE_MAP, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
	        return texture;
        }

        /**
         * Clamp texture edges.
         * @return This loader object with ClampEdges filter.
         */
        public Loader clampEdges(){
            this.clampEdges = true;
            return this;
        }

        /**
         * Use MipMap to make objects further away look better.
         * @return This loader with normal MipMapping activated.
         */
        public Loader normalMipMap(){
            this.mipmap = true;
            return this;
        }

        /**
         * Nearest filtering makes pixels pop out more.
         * @return This loader with nearestFiltering activated.
         */
        public Loader nearestFiltering(){
            this.mipmap = false;
            this.nearest = true;
            return this;
        }
    }
}
