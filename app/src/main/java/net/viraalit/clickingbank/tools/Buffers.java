package net.viraalit.clickingbank.tools;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by Kim on 19.12.2017.
 */

public class Buffers {

	public static final int BYTES_IN_INT = 4, BYTES_IN_FLOAT = 4;
	private static final ByteOrder order = ByteOrder.nativeOrder();

	public static IntBuffer createIntBuffer(final int size){
		return ByteBuffer.allocateDirect(size * BYTES_IN_INT).order(order).asIntBuffer();
	}

	public static FloatBuffer createFloatBuffer(final int size){
		return ByteBuffer.allocateDirect(size * BYTES_IN_FLOAT).order(order).asFloatBuffer();
	}

	public static int bytesIn(Buffer buffer) {
		if(buffer instanceof IntBuffer){
			return buffer.remaining() * BYTES_IN_INT;
		}
		else if(buffer instanceof FloatBuffer){
			return buffer.remaining() * BYTES_IN_FLOAT;
		}
		else{
			return buffer.remaining();
		}
	}
}
