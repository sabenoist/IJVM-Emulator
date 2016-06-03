package pad.ijvm;

public class Stack {
	private Word[] stack;
    private int stackPointer;
    private int basePointer;

    public Stack(int size) {
    	stack = new Word[size];
        stackPointer = 0;
        basePointer = 0;
    }

    public void incStackPointer(int amount) {
        if (stackPointer < stack.length - 1) {
            stackPointer += amount;
        }
    }

    public void decStackPointer(int amount) {
        if (stackPointer >= 0) {
            stackPointer -= amount;
        }
    }

    public void setTopOfStack(Word word) {
    	stack[stackPointer] = word;
    }

    public Word getTopOfStack() {
    	return stack[stackPointer - 1];
    }

    public int getBasePointer() {
        return basePointer;
    }

    public void setBasePointer(int value) {
        basePointer = value;
    }

    public int getStackPointer() {
        return stackPointer;
    }

    public Word[] getWordArray() {
    	return stack;
    }

    public int[] getIntegerArray() {
        int[] result = new int[stackPointer];

        for(int i = 0; i < stackPointer; i++) {
            result[i] = stack[i].toInteger();
        }

        return result;
    }
}
