package pad.ijvm;

public class Stack {
	private Word[] stack;

    public void Stack(int size) {
    	stack = new Word[size];
    }

    public void setPos(int pos, Word word) {
    	stack[pos] = word;
    }

    public Word getPos(int pos) {
    	return stack[pos];
    }

    public Word[] getWordArray() {
    	return stack;
    }
}
