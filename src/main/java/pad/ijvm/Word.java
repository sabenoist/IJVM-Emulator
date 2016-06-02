package pad.ijvm;

public class Word {

	byte[] bytes;

    public void Word(byte[] input) {
    	bytes = input;
    }

    public int getIntValue() {
    	return ((bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);
    }

    public String getHexString() {
    	return javax.xml.bind.DatatypeConverter.printHexBinary(bytes);
    }
}
