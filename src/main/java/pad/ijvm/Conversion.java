package pad.ijvm;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Conversion {

	public static int shortToInt(int pos, byte[] bytes) {
        return ((bytes[pos] << 8) | bytes[pos + 1]);
    }

    public static int unsignedShortToInt(int pos, byte[] bytes) {
        return (((bytes[pos] & 0xFF) << 8) | (bytes[pos + 1] & 0xFF));
    }

    public static int unsignedWordToInt(int pos, byte[] bytes) {
        return ((bytes[pos] & 0xFF) << 24) | ((bytes[pos + 1] & 0xFF) << 16) | ((bytes[pos + 2] & 0xFF) << 8) | (bytes[pos + 3] & 0xFF);
    }

    public static int signedWordToInt(int pos, byte[] bytes) {
        return (bytes[pos] << 24) | (bytes[pos + 1] << 16) | (bytes[pos + 2] << 8) | bytes[pos + 3];
    }

    public static byte[] intToBytes(int var) {
        return ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(var).array();
    }
}