package pad.ijvm;

import java.util.LinkedList;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.io.PrintStream;

public class Processor {
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
    static final byte IOR = (byte)0xB0;
    static final byte IRETURN = (byte)0xAC;
    static final byte ISTORE = (byte)0x36;
    static final byte ISUB = (byte)0x64;
    static final byte LDC_W = (byte)0x13;
    static final byte POP = (byte)0x57;
    static final byte SWAP = (byte)0x5F;
    static final byte WIDE = (byte)0xC4;

    private PrintStream out;
    private BinaryLoader bytes;
    private FrameList frames;

    private byte currentInstruction;
    
    public Processor(BinaryLoader bLoader) {
        bytes = bLoader;
        frames = new FrameList();
        frames.addFrame(new Frame());

        currentInstruction = NOP;
        out = System.out;
    }

    public void biPush(Word word) {
        frames.getStack().setTopOfStack(word);
        frames.getStack().incStackPointer(1);
    }

    public Word pop() {
        Word word = frames.getStack().getTopOfStack();
        frames.getStack().decStackPointer(1);

        return word;
    }

    public void goTo(int offset) {
        frames.incProgramCounter(offset);
    }

    public void ifeq() {
        int var = pop().toInteger();

        if (var == 0) {
            goTo(getShortAsInt(frames.getProgramCounter() + 1, bytes.getText()));
        }
        else {
            frames.incProgramCounter(3);
        }
    }

    public void iflt() {
        int var = pop().toInteger();

        if (var < 0) {
            goTo(getShortAsInt(frames.getProgramCounter() + 1, bytes.getText()));
        }
        else {
            frames.incProgramCounter(3);
        }
    }

    public void ifIcmpeq() {
        int var1 = pop().toInteger();
        int var2 = pop().toInteger();

        if (var1 == var2) {
            goTo(getShortAsInt(frames.getProgramCounter() + 1, bytes.getText()));
        }
        else {
            frames.incProgramCounter(3);
        }
    }

    public void iadd() {
        Word var1 = pop();
        Word var2 = pop();

        int sum = var1.toInteger() + var2.toInteger();
        biPush(new Word(getIntAsBytes(sum)));
    }

    public void isub() {
        Word var1 = pop();
        Word var2 = pop();

        int subResult = var2.toInteger() - var1.toInteger();
        biPush(new Word(getIntAsBytes(subResult)));
    }

    public void iand() {
        int var1 = pop().toInteger();
        int var2 = pop().toInteger();

        biPush(new Word(getIntAsBytes(var1 & var2)));
    }

    public void ior() {
        int var1 = pop().toInteger();
        int var2 = pop().toInteger();

        biPush(new Word(getIntAsBytes(var1 | var2)));
    }

    public void swap() {
        Word var1 = pop();
        Word var2 = pop();

        biPush(var1);
        biPush(var2);
    }

    public void ldcW() {
        int programCounter = frames.getProgramCounter();
        int position = getUnsignedShortAsInt(programCounter + 1, bytes.getText()) * 4;

        byte[] constant = new byte[4];
        System.arraycopy(bytes.getConstants(), position, constant, 0, 4);

        biPush(new Word(constant));
    }

    public void iload() {
        int programCounter = frames.getProgramCounter();
        int position = (bytes.getText()[programCounter + 1] & 0xFF);

        biPush(frames.getLocalVariable(position));
    }

    public void istore() {
        int programCounter = frames.getProgramCounter();
        int position = (bytes.getText()[programCounter + 1] & 0xFF);

        frames.setLocalVariable(position, pop());
    }

    public void iinc() {
        int programCounter = frames.getProgramCounter();
        int position = bytes.getText()[programCounter + 1];
       
        int value1 = frames.getLocalVariable(position).toInteger();
        int value2 = bytes.getText()[programCounter + 2];

        Word result = new Word(getIntAsBytes(value1 + value2));

        frames.setLocalVariable(position, result);
    }

    public int getShortAsInt(int pos, byte[] bytes) {
        return ((bytes[pos] << 8) | bytes[pos + 1]);
    }

    public int getUnsignedShortAsInt(int pos, byte[] bytes) {
        return (((bytes[pos] & 0xFF) << 8) | (bytes[pos + 1] & 0xFF));
    }

    public int getWordAsInt(int pos, byte[] bytes) {
        return ((bytes[pos] & 0xFF) << 24) | ((bytes[pos + 1] & 0xFF) << 16) | ((bytes[pos + 2] & 0xFF) << 8) | (bytes[pos + 3] & 0xFF);
    }

    public byte[] getIntAsBytes(int var) {
        return ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(var).array();
    }

    public void interpretTextByte() {
        int programCounter = frames.getProgramCounter();
        byte input = bytes.getText()[programCounter];
        currentInstruction = input;

        switch (input) {
            case NOP: 
                break; //do nothing
            case OUT: 
                int value = pop().toInteger();
                out.print((char) value);

                frames.incProgramCounter();

                break;
            case BIPUSH: 
                int var = bytes.getText()[programCounter + 1];
                biPush(new Word(getIntAsBytes(var)));

                frames.incProgramCounter(2);

                break;
            case DUP: 
                biPush(frames.getStack().getTopOfStack());
                frames.incProgramCounter();
                
                break;
            case GOTO:
                goTo(getShortAsInt(programCounter + 1, bytes.getText()));

                break;
            case IADD:
                iadd();
                frames.incProgramCounter();

                break;
            case IAND:
                iand();
                frames.incProgramCounter();

                break;
            case IFEQ:
                ifeq();

                break;
            case IFLT:
                iflt();

                break;
            case IF_ICMPEQ:
                ifIcmpeq();

                break;
            case IINC:
                iinc();
                frames.incProgramCounter(3);

                break;
            case ILOAD:
                iload();
                frames.incProgramCounter(2);

                break;
            case INVOKEVIRTUAL:
                frames.incProgramCounter();

                break;
            case IOR:
                ior();
                frames.incProgramCounter();

                break;
            case IRETURN:
                frames.incProgramCounter();

                break;
            case ISTORE:
                istore();
                frames.incProgramCounter(2);

                break;
            case ISUB:
                isub();
                frames.incProgramCounter();

                break;
            case LDC_W:
                ldcW();
                frames.incProgramCounter(3);

                break;
            case POP:
                frames.getStack().decStackPointer(1);
                frames.incProgramCounter();

                break;
            case SWAP:
                swap();
                frames.incProgramCounter();

                break;
            case WIDE:
                frames.incProgramCounter();

                break;
            default:
                frames.incProgramCounter();

                break;
        }
    }

    public int[] getStackContents() {
        return frames.getStack().getIntegerArray();
    }

    public int getTopOfStack() {
        return frames.getStack().getTopOfStack().toInteger();
    }

    public int getStackPointer() {
        return frames.getStack().getStackPointer();
    }

    public int getProgramCounter() {
        return frames.getProgramCounter();
    }

    public byte getCurrentInstruction() {
        return currentInstruction;
    }

    public int getLocalVariable(int pos) {
        return frames.getLocalVariables()[pos].toInteger();
    }

    public void setOutput(PrintStream output) {
        out = output;
    }
}
