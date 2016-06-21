package pad.ijvm;

public class Word {

	private byte[] bytes;

    public Word(byte[] input) {
    	bytes = input;
    }

    public int toInteger() {
    	return ((bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);
    }

    public int getByte(int pos) {
        return bytes[pos];
    }
}
