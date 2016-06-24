package pad.ijvm;

public class Word {

	private byte[] bytes;

    public Word(byte[] input) {
    	bytes = input;
    }

    public int toInteger() {
        return Conversion.signedWordToInt(0, bytes);
    }

    public int getByte(int pos) {
        return bytes[pos];
    }
}
