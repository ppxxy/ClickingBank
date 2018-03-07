package net.viraalit.clickingbank.models;

import net.viraalit.clickingbank.tools.Vector2f;
import net.viraalit.clickingbank.tools.Vector3f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Kim on 25.12.2017.
 */

public class WavefrontLoader {

	private static final String COLORIZATION_IDENTIFIER = "colorized";

	private InputStream stream;

	public WavefrontLoader(InputStream stream){
		this.stream = stream;
	}

	public VAO loadToVao(){
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(stream));
			String line = reader.readLine();
			List<?> textures;
			boolean colorized = line.contains(COLORIZATION_IDENTIFIER);
			if(colorized){
				textures = new ArrayList<Vector3f>();
			}
			else{
				textures = new ArrayList<Vector2f>();
			}
			List<Vertex> vertices = new ArrayList<Vertex>();
			List<Vector3f> normals = new ArrayList<Vector3f>();
			List<Integer> indices = new ArrayList<Integer>();
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(" ");
				switch(values[0]) {
					case "v":
						Vector3f position = new Vector3f(Float.valueOf(values[1]), Float.valueOf(values[2]), Float.valueOf(values[3]));
						Vertex newVertex = new Vertex(vertices.size(), position);
						vertices.add(newVertex);
						break;
					case "vt":
						Vector2f texture = new Vector2f(Float.valueOf(values[1]), Float.valueOf(values[2]));
						((List<Vector2f>)textures).add(texture);
						break;
					case "fc":
						Vector3f color = new Vector3f(Float.valueOf(values[1]), Float.valueOf(values[2]), Float.valueOf(values[3]));
						((List<Vector3f>) textures).add(color);
					case "vn":
						Vector3f normal = new Vector3f(Float.valueOf(values[1]), Float.valueOf(values[2]), Float.valueOf(values[3]));
						normals.add(normal);
						break;
					case "f":
						String[] vertex1 = values[1].split("/");
						String[] vertex2 = values[2].split("/");
						String[] vertex3 = values[3].split("/");
						processVertex(vertex1, vertices, indices);
						processVertex(vertex2, vertices, indices);
						processVertex(vertex3, vertices, indices);
						break;
				}
			}
			removeUnusedVertices(vertices);
			float[] verticesArray = new float[vertices.size() * 3];
			float[] texturesArray = new float[vertices.size() * (colorized ? 3 : 2)];
			float[] normalsArray = new float[vertices.size() * 3];
			if(colorized){
				convertListsToArraysColor(vertices, (List<Vector3f>)textures, normals, verticesArray, texturesArray, normalsArray);
			}
			else{
				convertListsToArraysTexture(vertices, (List<Vector2f>)textures, normals, verticesArray, texturesArray, normalsArray);
			}
			int[] indicesArray = convertIndicesListToArray(indices);
			return storeToVAO(verticesArray, texturesArray, normalsArray, indicesArray, colorized);
		}
		catch(IOException e){
			e.printStackTrace();
		} finally{
			try {
				reader.close();
			} catch(IOException e){
				e.printStackTrace();
			}
		}
		return null;
	}

	private static int[] convertIndicesListToArray(List<Integer> indices){
		int[] array = new int[indices.size()];
		for(int i = 0; i < array.length; i++){
			array[i] = indices.get(i);
		}
		return array;
	}

	private static void processVertex(String[] vertex, List<Vertex> vertices, List<Integer> indices) {
		int index = Integer.parseInt(vertex[0]) - 1;
		Vertex currentVertex = vertices.get(index);
		int textureIndex = Integer.parseInt(vertex[1]) - 1;
		int normalIndex = Integer.parseInt(vertex[2]) - 1;
		if (!currentVertex.isSet()) {
			currentVertex.setTextureIndex(textureIndex);
			currentVertex.setNormalIndex(normalIndex);
			indices.add(index);
		} else {
			dealWithAlreadyProcessedVertex(currentVertex, textureIndex, normalIndex, indices,
					vertices);
		}
	}

	private static void dealWithAlreadyProcessedVertex(Vertex previousVertex, int newTextureIndex,
	                                                   int newNormalIndex, List<Integer> indices, List<Vertex> vertices) {
		if (previousVertex.hasSameTextureAndNormal(newTextureIndex, newNormalIndex)) {
			indices.add(previousVertex.getIndex());
		} else {
			Vertex anotherVertex = previousVertex.getDuplicateVertex();
			if (anotherVertex != null) {
				dealWithAlreadyProcessedVertex(anotherVertex, newTextureIndex, newNormalIndex,
						indices, vertices);
			} else {
				Vertex duplicateVertex = new Vertex(vertices.size(), previousVertex.getPosition());
				duplicateVertex.setTextureIndex(newTextureIndex);
				duplicateVertex.setNormalIndex(newNormalIndex);
				previousVertex.setDuplicateVertex(duplicateVertex);
				vertices.add(duplicateVertex);
				indices.add(duplicateVertex.getIndex());
			}

		}
	}

	private static void removeUnusedVertices(List<Vertex> vertices){
		Iterator<Vertex> iterator = vertices.iterator();
		while(iterator.hasNext()){
			Vertex v = iterator.next();
			if(!v.isSet()){
				iterator.remove();
			}
		}
	}

	private static void convertListsToArraysTexture(List<Vertex> vertexList, List<Vector2f> textureList, List<Vector3f> normalsList,
	                                                float[] verticesArray, float[] texturesArray, float[] normalsArray){
		int vec3index = 0;
		int vec2index = 0;
		Iterator<Vertex> iterator = vertexList.iterator();
		while(iterator.hasNext()){
			Vertex v = iterator.next();
			Vector3f position = v.getPosition();
			Vector2f textureCoords = textureList.get(v.getTextureIndex());
			Vector3f normal = normalsList.get(v.getNormalIndex());
			for(int i = 0; i < 3; i++){
				verticesArray[vec3index] = position.getValue(i);
				normalsArray[vec3index++] = normal.getValue(i);
			}
			texturesArray[vec2index++] = textureCoords.x;
			texturesArray[vec2index++] = 1-textureCoords.y;
		}

	}

	private static void convertListsToArraysColor(List<Vertex> vertexList, List<Vector3f> colorList, List<Vector3f> normalsList,
	                                              float[] verticesArray, float[] colorsArray, float[] normalsArray){
		int vec3index = 0;
		Iterator<Vertex> iterator = vertexList.iterator();
		while(iterator.hasNext()){
			Vertex v = iterator.next();
			Vector3f position = v.getPosition();
			Vector3f colors = colorList.get(v.getTextureIndex());
			Vector3f normal = normalsList.get(v.getNormalIndex());
			for(int i = 0; i < 3; i++) {
				verticesArray[vec3index] = position.getValue(i);
				colorsArray[vec3index] = colors.getValue(i);
				normalsArray[vec3index++] = normal.getValue(i);
			}
		}

	}

	private static VAO storeToVAO(float[] vertexArray, float[] texturesArray, float[] normalsArray, int[] indicesArray, boolean colorized){
		VAO vao = VAO.create();
		vao.createIndexBuffer(indicesArray);
		vao.createAttribute(0, vertexArray, 3);
		vao.createAttribute(1, texturesArray, colorized ? 3 : 2);
		vao.createAttribute(2, normalsArray, 3);
		vao.unbind();
		return vao;
	}

	private static class Vertex{

		private static final int UNDEFINED = -1;

		private Vector3f position;
		private int textureIndex = UNDEFINED;
		private int normalIndex = UNDEFINED;
		private Vertex duplicateVertex;
		private int index;

		private Vertex(int index, Vector3f position){
			this.index = index;
			this.position = position;
		}

		public int getIndex(){
			return this.index;
		}

		public boolean isSet(){
			return (this.normalIndex != UNDEFINED && this.textureIndex != UNDEFINED);
		}

		public boolean hasSameTextureAndNormal(int textureIndex, int normalIndex){
			return this.textureIndex == textureIndex && this.normalIndex == normalIndex;
		}

		public void setTextureIndex(int textureIndex){
			this.textureIndex = textureIndex;
		}

		public void setNormalIndex(int normalIndex){
			this.normalIndex = normalIndex;
		}

		public Vector3f getPosition(){
			return position;
		}

		public int getTextureIndex(){
			return textureIndex;
		}

		public int getNormalIndex(){
			return normalIndex;
		}

		public Vertex getDuplicateVertex(){
			return duplicateVertex;
		}

		public void setDuplicateVertex(Vertex duplicateVertex){
			this.duplicateVertex = duplicateVertex;
		}
	}

}
