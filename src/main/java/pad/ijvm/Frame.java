package pad.ijvm;

public class Frame {
	private int programCounter;
	private Stack stack;
	private WordList localVariables;

	public Frame() {
		programCounter = 0;
		stack = new Stack();
		localVariables = new WordList();
	}

	public int getProgramCounter() {
		return programCounter;
	}

	public void incProgramCounter() {
		programCounter++;
	}

	public void incProgramCounter(int offset) { 
		programCounter += offset;
	}

	public void setProgramCounter(int i ) {
		programCounter = i;
	}

	public Stack getStack() {
		return stack;
	}

	public Word[] getLocalVariables() {
		return localVariables.getWords();
	}

	public Word getLocalVariable(int pos) {
		return localVariables.getWord(pos);
	}

	public void setLocalVariable(int pos, Word word) {
		localVariables.setWord(pos, word);
	}
}