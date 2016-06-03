package pad.ijvm;

import pad.ijvm.interfaces.IJVMInterface;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.FileInputStream;


public class IJVM implements IJVMInterface {
    static final byte[] IJVM_HEAD = new byte[] {(byte)0x1D, (byte)0xEA, (byte)0xDF, (byte)0xAD};

    private BinaryLoader bytes;
    private Processor processor;

	private InputStream in;
	private PrintStream out;

    private int textPosition;
    private byte currentInstruction;

	public IJVM(File input) {
        try {
            bytes = new BinaryLoader(input);
            processor = new Processor();
            textPosition = 0;
        }
        catch(Exception e) {
            System.err.printf("%s\n", e.getMessage());
            System.exit(1);
        }
	}

	/**
     *
     * @return The word at the top of the stack of the current frame, 
     * interpreted as a signed integer.
     */
    public int topOfStack() {
    	return processor.getTopOfStack();
    }

    /**
     *
     * @return Returns the stack of the current frame as an array of integers.
     */
    public int[] getStackContents() {
    	return processor.getStackContents();
    }

    /**
     *
     * @return The current loaded program text as an byte array.
     */
    public byte[] getText() {
    	return bytes.getText();
    }

    /**
     *
     * @return The value of the program counter (as an offset from the first instruction).
     */
    public int getProgramCounter() {
    	return processor.getProgramCounter();
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
        processor.byteInterpreter(bytes.getText());

        textPosition++;
    }

    /**
     * Run the vm with the current state until the machine halts.
     */
    public void run() {
        //reads through the bytes.
        if (bytes.getProgramIdentifier().equals(IJVM_HEAD)) {
            while (processor.getProgramCounter() < bytes.getText().length) {
                step();
            }
        }
        else {
            System.err.printf("ERROR: This program is not an IJVM program!");
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