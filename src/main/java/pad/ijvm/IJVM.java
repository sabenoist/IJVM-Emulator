package pad.ijvm;

import pad.ijvm.interfaces.IJVMInterface;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.FileInputStream;

public class IJVM implements IJVMInterface {
    private BinaryLoader bytes;
    private Processor processor;
    private PrintStream out;

    private boolean binaryLoaded;

	public IJVM(File input) {
        out = System.out;

        try {
            bytes = new BinaryLoader(input);
            processor = new Processor(bytes);

            binaryLoaded = true;
        }
        catch(Exception e) {
            binaryLoaded = false;
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
    	return processor.getLocalVariable(i);
    }

    /**
     *
     * @param i, index of the constant to obtain
     * @return The constant at location i in the constant pool.
     */
    public int getConstant(int i) {
    	return bytes.getConstants()[i * 4];
    }

    /**
     * Step (perform) one instruction and return.
     * In the case of WIDE, perform the whole WIDE_ISTORE or WIDE_ILOAD.
     */
    public void step() {
        processor.interpretTextByte();
    }

    /**
     * Run the vm with the current state until the machine halts.
     */
    public void run() {
        if (!binaryLoaded) {
            out.println("ERROR: The binary file could not be read or found!");
            return;
        }

        if (!bytes.isIJVM()) {
            out.println("ERROR: This is not an IJVM program!");
            return;
        }

        processor.setRunning(true);

        while (processor.isRunning() && (processor.getProgramCounter() < bytes.getText().length)) {
            step();
        }
    }

    /**
     * @return The value of the current instruction represented as a byte.
     */
    public byte getInstruction() {
    	return processor.getCurrentInstruction();
    }

    /**
     * Sets the standard output of the IJVM instance.
     * @param out, PrintStream to be used for OUT instruction.
     */
    public void setOutput(PrintStream output) {
        out = output;
        processor.setOutput(output);
    }

    /**
     * Sets the standard input of the IJVM instance.
     * @param in, InputStream to be used for IN instruction.
     */
    public void setInput(InputStream input) {
    	processor.setInput(input);

    }
}