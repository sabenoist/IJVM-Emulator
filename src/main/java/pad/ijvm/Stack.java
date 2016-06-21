package pad.ijvm;

public class Stack {
    static final int INIT_STACK_SIZE = 256;

	private Word[] stack;
    private int stackPointer;

    public Stack() {
    	stack = new Word[INIT_STACK_SIZE];
        stackPointer = 0;
    }

    public void doubleStackSize() {
        Word[] newStack = new Word[stack.length * 2];

        System.arraycopy(stack, 0, newStack, 0, stack.length);
        stack = newStack;
    }

    public void incStackPointer(int amount) {
        if (stackPointer >= stack.length - 1) {
            doubleStackSize();
        }
        stackPointer++;
    }

    public void decStackPointer(int amount) {
        if ((stackPointer - amount) > 0) {
            stackPointer -= amount;
        }
    }

    public void setTopOfStack(Word word) {
    	stack[stackPointer] = word;
    }

    public Word getTopOfStack() {
    	return stack[stackPointer - 1];
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
