package pad.ijvm;

public class FrameList {
	private static final int INIT_SIZE = 256;
	
	private Frame[] frames;
	private int framePointer, frameIndex;

	public FrameList() {
		frames = new Frame[INIT_SIZE];
		framePointer = 0;
		frameIndex = 0;
	}

	public void addFrame(Frame frame) {
		if (frameIndex >= frames.length - 1) {
            doubleListSize();
        }

		frames[frameIndex] = frame;
		frameIndex++;
	}

	public void doubleListSize() {
        Frame[] newList = new Frame[frames.length * 2];

        System.arraycopy(frames, 0, newList, 0, frames.length);
        frames = newList;
    }

	public int getFramePointer() {
		return framePointer;
	}

	public int getProgramCounter() {
		return frames[framePointer].getProgramCounter();
	}

	public void incProgramCounter() {
		frames[framePointer].incProgramCounter();
	}
	
	public void incProgramCounter(int offset) {
		frames[framePointer].incProgramCounter(offset);
	}

	public void setProgramCounter(int i) {
		frames[framePointer].setProgramCounter(i);
	}

	public Stack getStack() {
		return frames[framePointer].getStack();
	}

	public Word getLocalVariable(int pos) {
		return frames[framePointer].getLocalVariable(pos);
	}

	public void setLocalVariable(int pos, Word word) {
		frames[framePointer].setLocalVariable(pos, word);
	}

	public Word[] getLocalVariables() {
		return frames[framePointer].getLocalVariables();
	}
}