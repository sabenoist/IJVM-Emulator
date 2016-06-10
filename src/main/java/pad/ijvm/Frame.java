package pad.ijvm;

public class Frame {
	private int programCounter;
	private Stack stack;
	private Word[] localVariables;

	public Frame(int pc, Stack stck, Word[] lv) {
		programCounter = pc;
		stack = stck;
		localVariables = lv;
	}
}