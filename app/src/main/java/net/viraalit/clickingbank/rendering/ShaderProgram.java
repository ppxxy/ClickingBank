package net.viraalit.clickingbank.rendering;

import android.opengl.GLES20;

import net.viraalit.clickingbank.rendering.uniforms.Uniform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Kim on 18.12.2017.
 */

public abstract class ShaderProgram {

	private int programID;

	public ShaderProgram(InputStream vertexInput, InputStream fragmentInput, String... inVariables){
		int vertexShaderID = loadShader(vertexInput, GLES20.GL_VERTEX_SHADER);
		int fragmentShaderID = loadShader(fragmentInput, GLES20.GL_FRAGMENT_SHADER);
		programID = GLES20.glCreateProgram();
		if(programID == 0){
			throw new RuntimeException("Could not get valid id for shader program.");
		}
		GLES20.glAttachShader(programID, vertexShaderID);
		GLES20.glAttachShader(programID, fragmentShaderID);
		bindAttributes(inVariables);
		GLES20.glLinkProgram(programID);
		final int[] linkStatus = new int[1];
		GLES20.glGetProgramiv(programID, GLES20.GL_LINK_STATUS, linkStatus, 0);
		if(linkStatus[0] == 0){
			throw new RuntimeException("Could not link shaders.");
		}
		GLES20.glValidateProgram(programID);
		GLES20.glDetachShader(programID, vertexShaderID);
		GLES20.glDetachShader(programID, fragmentShaderID);
		GLES20.glDeleteShader(vertexShaderID);
		GLES20.glDeleteShader(fragmentShaderID);
		//GLES20.glDeleteProgram(programID);
	}

	private void bindAttributes(String[] inVariables){
		for(int i = 0; i < inVariables.length; i++){
			GLES20.glBindAttribLocation(programID, i, inVariables[i]);
		}
	}

	public void stop(){
		GLES20.glUseProgram(0);
	}

	/**
	 * Link Uniform objects defined in Uniform array to shader's uniform locations. After the changes program has to be validated.
	 * @param uniforms
	 */
	public void storeUniformLocations(Uniform... uniforms) {
		for(Uniform u : uniforms){
			u.storeUniformLocation(programID);
		}
		GLES20.glValidateProgram(programID);
	}

	private static int loadShader(InputStream inputStream, int shaderType){
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while((line = reader.readLine()) != null){
				shaderSource.append(line +"\n");
			}
		} catch(IOException e){
			e.printStackTrace();
			System.exit(0);
		} finally{
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		int shaderID = GLES20.glCreateShader(shaderType);
		if(shaderID != 0){
			String content = shaderSource.toString();
			GLES20.glShaderSource(shaderID, content);
			GLES20.glCompileShader(shaderID);
			final int[] compileStatus = new int[1];
			GLES20.glGetShaderiv(shaderID, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
			if(compileStatus[0] == GLES20.GL_FALSE){
				System.out.println(GLES20.glGetShaderInfoLog(shaderID));
				GLES20.glDeleteShader(shaderID);
				throw new RuntimeException("Failed compiling shader.");
			}
		}
		else{
			throw new RuntimeException("Can't get valid shader ID.");
		}
		return shaderID;
	}

	public void start(){
		GLES20.glUseProgram(programID);
	}

	public void cleanUp() {
		stop();
		GLES20.glDeleteProgram(programID);
	}
}
