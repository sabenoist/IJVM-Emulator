package pad.ijvm;

import pad.ijvm.interfaces.IJVMInterface;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.FileInputStream;

import java.nio.file.Files;
import java.nio.ByteBuffer;

import java.math.BigInteger;


public class IJVM implements IJVMInterface {
	static final byte NOP = (byte)0x00;
    static final byte OUT = (byte)0xFD;
    static final byte BIPUSH = (byte)0x10;
    static final byte DUP = (byte)0x59;
    static final byte GOTO = (byte)0xA7;
    static final byte IADD = (byte)0x60;
    static final byte IAND = (byte)0x7E;
    static final byte IFEQ = (byte)0x99;
    static final byte IFLT = (byte)0x9B;
    static final byte IF_ICMPEQ = (byte)0x9F;
    static final byte IINC = (byte)0x84;
    static final byte ILOAD = (byte)0x15;
    static final byte INVOKEVIRTUAL = (byte)0xB6;
    static final byte IOR = (byte)0x80;
    static final byte IRETURN = (byte)0xAC;
    static final byte ISTORE = (byte)0x36;
    static final byte ISUB = (byte)0x64;
    static final byte LDC_W = (byte)0x13;
    static final byte POP = (byte)0x57;
    static final byte SWAP = (byte)0x5F;
    static final byte WIDE = (byte)0xC4;

    private byte[] bytes;
    private byte[] constants;
    private byte[] text;

    private byte[] programMemoryAddress;
    private byte[] constantsMemoryAddress;
    private byte[] textMemoryAddress;

	private byte currentInstruction;

	private InputStream in;
	private PrintStream out;

    private int textPosition;
	private int programCounter;

	public IJVM(File input) {
        try {
            bytes = new byte[(int) input.length()];
            programMemoryAddress = new byte[4];
            constantsMemoryAddress = new byte[4];
            textMemoryAddress = new byte[4];

            FileInputStream fileIn = new FileInputStream(input);
            fileIn.read(bytes);
            fileIn.close();

            textPosition = 0;

            programCounter = 0;
            currentInstruction = NOP;
        }
        catch(Exception e) {
            System.err.printf("%s\n", e.getMessage());
        }

        //byteBlocksParser();
	}

    private void byteBlocksParser() {
        //Stores the location of the program in memory
        int position = 0;
        System.arraycopy(bytes, position, programMemoryAddress, 0, 4);

        System.out.println("PASSED PROGRAM ADDRESS");

        //Stores the location of the constants in memory
        position += 4; 
        System.arraycopy(bytes, position, constantsMemoryAddress, 0, 4);

        System.out.println("PASSED CONSTANTS ADDRESS");

        //Determines the size of the constants block
        position += 4;
        byte[] constantsSizeArray = new byte[4];

        System.arraycopy(bytes, position, constantsSizeArray, 0, 4);
        //int constantsSize = ByteBuffer.wrap(constantsSizeArray).getInt();
        //int constantsSize = new BigInteger(constantsSizeArray).intValue();
        //constantsSize /= 8;       

        String constantsSizeHex = javax.xml.bind.DatatypeConverter.printHexBinary(constantsSizeArray);
        System.out.println("constantsSizeHex = " + constantsSizeHex);
        int constantsSize = Integer.parseInt(constantsSizeHex, 16) / 8;
        
        System.out.println("constantsSize = " + constantsSize + " bytes.");

        //Stores the constants block into the constants array
        position += 4;
        constants = new byte[constantsSize];
        System.arraycopy(bytes, position, constants, 0, constantsSize);

        System.out.println("PASSED STORE CONSTANTS BLOCK");

        //Stores the location of the text in memory
        position += constantsSize;
        System.arraycopy(bytes, position, textMemoryAddress, 0, 4);

        System.out.println("PASSED TEXT ADDRESS");

        //Determines the size of the text block
        position += 4;
        byte[] textSizeArray = new byte[4];

        System.arraycopy(bytes, position, textSizeArray, 0, 4);
        //int textSize = ByteBuffer.wrap(textSizeArray).getInt();
        //int textSize = new BigInteger(textSizeArray).intValue();
        //textSize /= 8;

        String textSizeHex = javax.xml.bind.DatatypeConverter.printHexBinary(textSizeArray);
        System.out.println("textSizeHex = " + textSizeHex);
        int textSize = Integer.parseInt(textSizeHex, 16) / 8;
        
        System.out.println("textSize = " + textSize + " bytes.");

        //Stores the text block into the text array
        position += 4;
        text = new byte[textSize];
        System.arraycopy(bytes, position, text, 0, textSize);

        System.out.println("PASSED TEXT BLOCK");
    }

    private void byteInterpreter(byte input) {
        switch (input) {
            case NOP: 
                break; //do nothing
            case OUT: 
                currentInstruction = input;
                programCounter++;

                break;
            case BIPUSH: 
                currentInstruction = input;
                programCounter++;
                
                break;
            case DUP: 
                currentInstruction = input;
                programCounter++;
                
                break;
            case GOTO:
                currentInstruction = input;
                programCounter++;

                break;
            case IADD:
                currentInstruction = input;
                programCounter++;

                break;
            case IAND:
                currentInstruction = input;
                programCounter++;

                break;
            case IFEQ:
                currentInstruction = input;
                programCounter++;

                break;
            case IFLT:
                currentInstruction = input;
                programCounter++;

                break;
            case IF_ICMPEQ:
                currentInstruction = input;
                programCounter++;

                break;
            case IINC:
                currentInstruction = input;
                programCounter++;

                break;
            case ILOAD:
                currentInstruction = input;
                programCounter++;

                break;
            case INVOKEVIRTUAL:
                currentInstruction = input;
                programCounter++;

                break;
            case IOR:
                currentInstruction = input;
                programCounter++;

                break;
            case IRETURN:
                currentInstruction = input;
                programCounter++;

                break;
            case ISTORE:
                currentInstruction = input;
                programCounter++;

                break;
            case ISUB:
                currentInstruction = input;
                programCounter++;

                break;
            case LDC_W:
                currentInstruction = input;
                programCounter++;

                break;
            case POP:
                currentInstruction = input;
                programCounter++;

                break;
            case SWAP:
                currentInstruction = input;
                programCounter++;

                break;
            case WIDE:
                currentInstruction = input;
                programCounter++;

                break;
        }
    }
	/**
     *
     * @return The word at the top of the stack of the current frame, 
     * interpreted as a signed integer.
     */
    public int topOfStack() {
    	return 0;
    }

    /**
     *
     * @return Returns the stack of the current frame as an array of integers.
     */
    public int[] getStackContents() {
    	int[] temp = new int[1];
    	return temp;
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
     * @return The value of the program counter (as an offset from the first instruction).
     */
    public int getProgramCounter() {
    	return programCounter;
    }

    /**
     *
     * @param i, index of variable to obtain.
     * @return Returns the i:th local variable of the current frame.
     */
    public int getLocalVariable(int i) {
    	return 0;
    }

    /**
     *
     * @param i, index of the constant to obtain
     * @return The constant at location i in the constant pool.
     */
    public int getConstant(int i) {
    	return 0;
    }

    /**
     * Step (perform) one instruction and return.
     * In the case of WIDE, perform the whole WIDE_ISTORE or WIDE_ILOAD.
     */
    public void step() {
        //System.out.println("BYTE [" + bytesPosition + "]: " + bytes[bytesPosition]);

        byteInterpreter(text[textPosition]);

        textPosition++;
    }

    /**
     * Run the vm with the current state until the machine halts.
     */
    public void run() {
        //reads through the bytes.
        for (int i = textPosition; i < text.length; i++) {            
            step();
        }
    }

    /**
     * @return The value of the current instruction represented as a byte.
     */
    public byte getInstruction() {
    	return currentInstruction;
    }

    /**
     * Sets the standard output of the IJVM instance.
     * @param out, PrintStream to be used for OUT instruction.
     */
    public void setOutput(PrintStream output) {
    	out = output;
    }

    /**
     * Sets the standard input of the IJVM instance.
     * @param in, InputStream to be used for IN instruction.
     */
    public void setInput(InputStream input) {
    	in = input;

    }
}