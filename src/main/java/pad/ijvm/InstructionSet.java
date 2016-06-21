package pad.ijvm;

public class InstructionSet {
    static final byte BIPUSH = (byte)0x10;
    static final byte DUP = (byte)0x59;
    static final byte ERR = (byte)0xFE;
    static final byte GOTO = (byte)0xA7;
    static final byte HALT = (byte)0xFF;
    static final byte IADD = (byte)0x60;
    static final byte IAND = (byte)0x7E;
    static final byte IFEQ = (byte)0x99;
    static final byte IFLT = (byte)0x9B;
    static final byte IF_ICMPEQ = (byte)0x9F;
    static final byte IINC = (byte)0x84;
    static final byte ILOAD = (byte)0x15;
    static final byte IN = (byte)0xFC;
    static final byte INVOKEVIRTUAL = (byte)0xB6;
    static final byte IOR = (byte)0xB0;
    static final byte IRETURN = (byte)0xAC;
    static final byte ISTORE = (byte)0x36;
    static final byte ISUB = (byte)0x64;
    static final byte LDC_W = (byte)0x13;
    static final byte NOP = (byte)0x00;
    static final byte OUT = (byte)0xFD;
    static final byte POP = (byte)0x57;
    static final byte SWAP = (byte)0x5F;
    static final byte WIDE = (byte)0xC4;
}