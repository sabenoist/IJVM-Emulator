package pad.ijvm;

import java.io.PrintStream;
import java.io.InputStream;


public class Processor {
    private PrintStream out;
    private InputStream in;

    private BinaryLoader bytes;
    private FrameList frames;

    private byte currentInstruction;
    private boolean running;
    
    public Processor(BinaryLoader bLoader) {
        currentInstruction = InstructionSet.NOP;
        running = false;

        bytes = bLoader;
        out = System.out;
        in = System.in;
        
        frames = new FrameList();
        frames.addFrame(new Frame());
    }

    public void biPush(Word word) {
        frames.getStack().setTopOfStack(word);
        frames.getStack().incStackPointer(1);
    }

    public void dup() {
        biPush(frames.getStack().getTopOfStack());
    }

    public void err() {
        out.println("A happy little accident has occurred.");
        running = false;
    }

    public void goTo(int offset) {
        frames.incProgramCounter(offset);
    }

    public void halt() {
        running = false;
    }

    public void iadd() {
        Word var1 = pop();
        Word var2 = pop();

        int sum = var1.toInteger() + var2.toInteger();
        biPush(new Word(Conversion.intToBytes(sum)));
    }

    public void iand() {
        int var1 = pop().toInteger();
        int var2 = pop().toInteger();

        biPush(new Word(Conversion.intToBytes(var1 & var2)));
    } 
    
    public void ifeq() {
        int var = pop().toInteger();

        if (var == 0) {
            goTo(Conversion.shortToInt(frames.getProgramCounter() + 1, bytes.getText()));
        }
        else {
            frames.incProgramCounter(3);
        }
    }

    public void iflt() {
        int var = pop().toInteger();

        if (var < 0) {
            goTo(Conversion.shortToInt(frames.getProgramCounter() + 1, bytes.getText()));
        }
        else {
            frames.incProgramCounter(3);
        }
    }

    public void ifIcmpeq() {
        int var1 = pop().toInteger();
        int var2 = pop().toInteger();

        if (var1 == var2) {
            goTo(Conversion.shortToInt(frames.getProgramCounter() + 1, bytes.getText()));
        }
        else {
            frames.incProgramCounter(3);
        }
    } 

    public void iinc() {
        int programCounter = frames.getProgramCounter();
        int position = bytes.getText()[programCounter + 1];
       
        int value1 = frames.getLocalVariable(position).toInteger();
        int value2 = bytes.getText()[programCounter + 2];

        Word result = new Word(Conversion.intToBytes(value1 + value2));

        frames.setLocalVariable(position, result);
    } 

    public void iload() {
        int programCounter = frames.getProgramCounter();
        int position = (bytes.getText()[programCounter + 1] & 0xFF);

        biPush(frames.getLocalVariable(position));
    }

    public void in() {
        try {   
            if (in.available() == 0) {
                biPush(new Word(Conversion.intToBytes(0)));
                return;
            }

            int character = (in.read() & 0xFF);
            biPush(new Word(Conversion.intToBytes(character)));
        } 
        catch (Exception e) {
            out.println("ERROR: The input could not be read.");
        }
    }

    public void invokevirtual() {
        ldcW(); //reads the method location constant and pushes it to the stack.
        int newProgramCounter = pop().toInteger();
        int argumentsAmount = Conversion.unsignedShortToInt(newProgramCounter, bytes.getText());
        int localVariablesAmount = Conversion.unsignedShortToInt(newProgramCounter + 2, bytes.getText());

        //store the arguments in a buffer
        Word[] argsBuffer = new Word[argumentsAmount];
        for (int i = argumentsAmount - 1; i >= 0; i--) {
            argsBuffer[i] = pop();
        }
        
        frames.incProgramCounter(3);

        //switch to new frame
        frames.addFrame(new Frame());
        frames.incFramePointer();

        //store the parameters in the LocalVariables of the new frame
        for (int i = 0; i < argumentsAmount; i++) {
            frames.setLocalVariable(i, argsBuffer[i]);
        }

        //continue the program from the first instruction in the method
        frames.setProgramCounter(newProgramCounter + 4);
    }

    public void ior() {
        int var1 = pop().toInteger();
        int var2 = pop().toInteger();

        biPush(new Word(Conversion.intToBytes(var1 | var2)));
    }

    public void ireturn() {
        if (frames.getFramePointer() == 0) {
            running = false;
        }

        else {
            Word returnValue = pop();
            
            frames.decFramePointer();
            biPush(returnValue);
        }
    }

    public void istore() {
        int programCounter = frames.getProgramCounter();
        int position = (bytes.getText()[programCounter + 1] & 0xFF);

        frames.setLocalVariable(position, pop());
    }

    public void isub() {
        Word var1 = pop();
        Word var2 = pop();

        int subResult = var2.toInteger() - var1.toInteger();
        biPush(new Word(Conversion.intToBytes(subResult)));
    }

    public void ldcW() {
        int programCounter = frames.getProgramCounter();
        int position = Conversion.unsignedShortToInt(programCounter + 1, bytes.getText()) * 4;

        byte[] constant = new byte[4];
        System.arraycopy(bytes.getConstants(), position, constant, 0, 4);

        biPush(new Word(constant));
    }

    public void out() {
        out.print((char) pop().toInteger());
    }

    public Word pop() {
        Word word = frames.getStack().getTopOfStack();
        frames.getStack().decStackPointer(1);

        return word;
    }

    public void swap() {
        Word var1 = pop();
        Word var2 = pop();

        biPush(var1);
        biPush(var2);
    }

    public void wide() {
        int programCounter = frames.getProgramCounter();

        switch(bytes.getText()[programCounter + 1]) {
            case InstructionSet.ILOAD:
                wideIload();

                break;
            case InstructionSet.ISTORE:
                wideIstore();

                break;
        } 
    }

    public void wideIload() {
        int programCounter = frames.getProgramCounter();
        int position = Conversion.unsignedShortToInt(programCounter + 2, bytes.getText());

        biPush(frames.getLocalVariable(position));
    }

    public void wideIstore() {
        int programCounter = frames.getProgramCounter();
        int position = Conversion.unsignedShortToInt(programCounter + 2, bytes.getText());

        frames.setLocalVariable(position, pop());
    }
    
    public void interpretTextByte() {
        int programCounter = frames.getProgramCounter();
        byte input = bytes.getText()[programCounter];
        currentInstruction = input;

        switch (input) {
            case InstructionSet.BIPUSH: 
                int var = bytes.getText()[programCounter + 1];
                biPush(new Word(Conversion.intToBytes(var)));
                frames.incProgramCounter(2);

                break;
            case InstructionSet.DUP: 
                dup();
                frames.incProgramCounter();
                
                break;
            case InstructionSet.ERR:
                err();
                frames.incProgramCounter();

                break;
            case InstructionSet.GOTO:
                goTo(Conversion.shortToInt(programCounter + 1, bytes.getText()));

                break;
            case InstructionSet.HALT:
                halt();
                frames.incProgramCounter();

                break;
            case InstructionSet.IADD:
                iadd();
                frames.incProgramCounter();

                break;
            case InstructionSet.IAND:
                iand();
                frames.incProgramCounter();

                break;
            case InstructionSet.IFEQ:
                ifeq();

                break;
            case InstructionSet.IFLT:
                iflt();

                break;
            case InstructionSet.IF_ICMPEQ:
                ifIcmpeq();

                break;
            case InstructionSet.IINC:
                iinc();
                frames.incProgramCounter(3);

                break;
            case InstructionSet.ILOAD:
                iload();
                frames.incProgramCounter(2);

                break;
            case InstructionSet.IN:
                in();
                frames.incProgramCounter();

                break;
            case InstructionSet.INVOKEVIRTUAL:
                invokevirtual();

                break;
            case InstructionSet.IOR:
                ior();
                frames.incProgramCounter();

                break;
            case InstructionSet.IRETURN:
                ireturn();
                frames.incProgramCounter();

                break;
            case InstructionSet.ISTORE:
                istore();
                frames.incProgramCounter(2);

                break;
            case InstructionSet.ISUB:
                isub();
                frames.incProgramCounter();

                break;
            case InstructionSet.LDC_W:
                ldcW();
                frames.incProgramCounter(3);

                break;

            case InstructionSet.NOP: 
                frames.incProgramCounter();

                break; //do nothing
            case InstructionSet.OUT: 
                out();
                frames.incProgramCounter();

                break;
            case InstructionSet.POP:
                frames.getStack().decStackPointer(1);
                frames.incProgramCounter();

                break;
            case InstructionSet.SWAP:
                swap();
                frames.incProgramCounter();

                break;
            case InstructionSet.WIDE:
                wide();
                frames.incProgramCounter(4);

                break;
            default:
                frames.incProgramCounter();

                break;
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean bool) {
        running = bool;
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

    public void setInput(InputStream input) {
        in = input;
    }
}
