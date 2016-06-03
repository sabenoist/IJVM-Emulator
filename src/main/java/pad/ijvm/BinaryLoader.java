package pad.ijvm;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.FileInputStream;

import java.nio.file.Files;


public class BinaryLoader {
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
        System.arraycopy(bytes, position, programIdentifier, 0, 4);

        //Stores the location of the constants in memory
        position += 4; 
        System.arraycopy(bytes, position, constantsMemoryAddress, 0, 4);

        //Determines the size of the constants data block
        position += 4;
        byte[] constantsSizeArray = new byte[4];

        System.arraycopy(bytes, position, constantsSizeArray, 0, 4);
        int constantsSize = ((constantsSizeArray[0] & 0xFF) << 24) | ((constantsSizeArray[1] & 0xFF) << 16) | ((constantsSizeArray[2] & 0xFF) << 8) | (constantsSizeArray[3] & 0xFF);

        //Stores the constants block into the constants array
        position += 4;
        constants = new byte[constantsSize];
        System.arraycopy(bytes, position, constants, 0, constantsSize);

        //Stores the location of the text in memory
        position += constantsSize;
        System.arraycopy(bytes, position, textMemoryAddress, 0, 4);

        //Determines the size of the text data block
        position += 4;
        byte[] textSizeArray = new byte[4];

        System.arraycopy(bytes, position, textSizeArray, 0, 4);
        int textSize = ((textSizeArray[0] & 0xFF) << 24) | ((textSizeArray[1] & 0xFF) << 16) | ((textSizeArray[2] & 0xFF) << 8) | (textSizeArray[3] & 0xFF);

        //Stores the text block into the text array
        position += 4;
        text = new byte[textSize];
        System.arraycopy(bytes, position, text, 0, textSize);
    }

    /**
     *
     * @return The current loaded program text as an byte array.
     */
    public byte[] getText() {
    	return text;
    }

    /**
     *
     * @return The current loaded program constants as an byte array.
     */
    public byte[] getConstants() {
        return constants;
    }   

    /**
     *
     * @return The current loaded program bytes as an byte array.
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     *
     * @return The current loaded program identifier as an byte array.
     */
    public byte[] getProgramIdentifier() {
        return programIdentifier;
    }      

    /**
     *
     * @return The current loaded program identifier as an byte array.
     */
    public byte[] getConstantsMemoryAddress() {
        return constantsMemoryAddress;
    }      

    /**
     *
     * @return The current loaded program identifier as an byte array.
     */
    public byte[] getTextMemoryAddress() {
        return textMemoryAddress;
    }      
}