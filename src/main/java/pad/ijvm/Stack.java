package pad.ijvm;

public class Stack {
	private Word[] stack;
    private int stackPointer;

    public Stack(int size) {
    	stack = new Word[size];
        stackPointer = 0;
    }

    public void incStackPointer(int amount) {
        stackPointer += amount;
    }

    public void decStackPointer(int amount) {
        stackPointer -= amount;
    }

    public void setTopOfStack(Word word) {
    	stack[stackPointer] = word;
    }

    public Word getTopOfStack() {
    	return stack[stackPointer];
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
            result[i] = stack[i + 1].toInteger();
        }

        return result;
    }
}
