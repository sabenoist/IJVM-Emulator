package pad.ijvm;

public class Processor {
    static final int STACK_SIZE = 256

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

    private Stack stack;
    private int programCounter
    private int stackPointer;
    private byte currentInstruction;

    public void Processor() {
        stack = new Stack(STACK_SIZE);
        programCounter = 0;
        stackPointer = 0;
        currentInstruction = NOP;
    }

    public void byteInterpreter(byte input) {
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

    public int getStackPointer() {
        return stackPointer;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public byte getCurrentInstruction() {
        return currentInstruction;
    }
}
