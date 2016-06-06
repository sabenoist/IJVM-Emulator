package pad.ijvm;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Processor {
    static final int STACK_SIZE = 256;

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

    private Stack stack;
    private int programCounter;
    private byte currentInstruction;
    
    private Word topOfStackRegister;

    public Processor() {
        stack = new Stack(STACK_SIZE);
        programCounter = 0;
        currentInstruction = NOP;
    }

    public void bipush(Word word) {
        stack.setTopOfStack(word);
        stack.incStackPointer(1);
    }

    public Word pop() {
        Word word = stack.getTopOfStack();
        stack.decStackPointer(1);

        return word;
    }

    public void byteInterpreter(byte[] text) {
        byte input = text[programCounter];
        currentInstruction = input;

        switch (input) {
            case NOP: 
                break; //do nothing
            case OUT: 
                programCounter++;

                break;
            case BIPUSH: 
                int var = (text[programCounter + 1]);
                bipush(new Word(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(var).array()));

                programCounter += 2;

                break;
            case DUP: 
                programCounter++;
                
                break;
            case GOTO:
                programCounter++;

                break;
            case IADD:
                Word add1 = pop();
                Word add2 = pop();

                int sum = add1.toInteger() + add2.toInteger();
                bipush(new Word(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(sum).array()));

                programCounter++;

                break;
            case IAND:
                int iand1 = pop().toInteger();
                int iand2 = pop().toInteger();

                bipush(new Word(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(iand1 & iand2).array()));

                programCounter++;

                break;
            case IFEQ:
                programCounter++;

                break;
            case IFLT:
                programCounter++;

                break;
            case IF_ICMPEQ:
                programCounter++;

                break;
            case IINC:
                programCounter++;

                break;
            case ILOAD:
                programCounter++;

                break;
            case INVOKEVIRTUAL:
                programCounter++;

                break;
            case IOR:
                int ior1 = pop().toInteger();
                int ior2 = pop().toInteger();

                bipush(new Word(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(ior1 | ior2).array()));

                programCounter++;

                break;
            case IRETURN:
                programCounter++;

                break;
            case ISTORE:
                programCounter++;

                break;
            case ISUB:
                Word sub1 = pop();
                Word sub2 = pop();

                int subResult = sub2.toInteger() - sub1.toInteger();
                bipush(new Word(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(subResult).array()));

                programCounter++;

                programCounter++;

                break;
            case LDC_W:
                programCounter++;

                break;
            case POP:
                stack.decStackPointer(1);

                programCounter++;

                break;
            case SWAP:
                Word swap1 = pop();
                Word swap2 = pop();

                bipush(swap1);
                bipush(swap2);

                programCounter++;

                break;
            case WIDE:
                programCounter++;

                break;
        }

        topOfStackRegister = stack.getTopOfStack();
    }

    public int[] getStackContents() {
        return stack.getIntegerArray();
    }

    public int getTopOfStack() {
        return stack.getTopOfStack().toInteger();
    }

    public int getStackPointer() {
        return stack.getStackPointer();
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public byte getCurrentInstruction() {
        return currentInstruction;
    }
}
