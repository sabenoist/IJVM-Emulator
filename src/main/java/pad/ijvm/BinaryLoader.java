package pad.ijvm;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.FileInputStream;

import java.nio.file.Files;
import java.util.Arrays;

public class BinaryLoader {
    private static final byte[] MAGIC_NUMBER = new byte[] {(byte)0x1D, (byte)0xEA, (byte)0xDF, (byte)0xAD};
    private static final int BLOCK_SIZE = 4;

    private byte[] bytes;
    private byte[] constants;
    private byte[] text;

    private byte[] programIdentifier;
    private byte[] constantsMemoryAddress;
    private byte[] textMemoryAddress;

    private int constantsPosition;
    private int textPosition;

	public BinaryLoader(File input) throws Exception {
        bytes = new byte[(int) input.length()];
            
        programIdentifier = new byte[4];
        constantsMemoryAddress = new byte[4];
        textMemoryAddress = new byte[4];

        constantsPosition = 0;
        textPosition = 0;

        FileInputStream fileIn = new FileInputStream(input);
        fileIn.read(bytes);
        fileIn.close();
        
        byteBlocksParser();
	}

    private void byteBlocksParser() {
        //Stores the magicnumber of the program
        int position = 0;
        System.arraycopy(bytes, position, programIdentifier, 0, BLOCK_SIZE);

        //Stores the location of the constants in memory
        position += BLOCK_SIZE; 
        System.arraycopy(bytes, position, constantsMemoryAddress, 0, BLOCK_SIZE);

        //Determines the size of the constants data block
        position += BLOCK_SIZE;
        byte[] constantsSizeArray = new byte[BLOCK_SIZE];

        System.arraycopy(bytes, position, constantsSizeArray, 0, BLOCK_SIZE);
        int constantsSize = Conversion.unsignedWordToInt(0, constantsSizeArray);

        //Stores the constants block into the constants array
        position += BLOCK_SIZE;
        constants = new byte[constantsSize];
        System.arraycopy(bytes, position, constants, 0, constantsSize);

        //Stores the location of the text in memory
        position += constantsSize;
        System.arraycopy(bytes, position, textMemoryAddress, 0, BLOCK_SIZE);

        //Determines the size of the text data block
        position += BLOCK_SIZE;
        byte[] textSizeArray = new byte[BLOCK_SIZE];

        System.arraycopy(bytes, position, textSizeArray, 0, BLOCK_SIZE);
        int textSize = Conversion.unsignedWordToInt(0, textSizeArray);

        //Stores the text block into the text array
        position += BLOCK_SIZE;
        text = new byte[textSize];
        System.arraycopy(bytes, position, text, 0, textSize);
    }

    public boolean isIJVM() {
        return Arrays.equals(programIdentifier, MAGIC_NUMBER);
    }

    public byte[] getText() {
    	return text;
    }

    public byte[] getConstants() {
        return constants;
    }   

    public byte[] getBytes() {
        return bytes;
    }

    public byte[] getProgramIdentifier() {
        return programIdentifier;
    }      

    public byte[] getConstantsMemoryAddress() {
        return constantsMemoryAddress;
    }      

    public byte[] getTextMemoryAddress() {
        return textMemoryAddress;
    }      
}