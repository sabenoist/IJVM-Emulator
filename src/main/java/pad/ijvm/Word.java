package pad.ijvm;

public class Word {

	private byte[] bytes;

    public void Word(byte[] input) {
    	bytes = input;
    }

    public int toInteger() {
    	return ((bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        for(int i=0; i < bytes.length; i++) {
            buffer.append(Character.forDigit((bytes[i] >> 4) & 0xFF, 16));
            buffer.append(Character.forDigit((bytes[i] & 0xFF), 16));
        }

        return buffer.toString();
    }
}
