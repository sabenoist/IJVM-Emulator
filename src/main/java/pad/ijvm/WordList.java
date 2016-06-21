package pad.ijvm;

public class WordList {
    private static final int INIT_SIZE = 256;

	private Word[] words;

    public WordList() {
    	words = new Word[INIT_SIZE];
    }

    public void doubleListSize() {
        Word[] newList = new Word[words.length * 2];

        System.arraycopy(words, 0, newList, 0, words.length);
        words = newList;
    }

    public void setWord(int pos, Word word) {
        if (pos >= words.length) {
            doubleListSize();
            
            setWord(pos, word);            
        }
        else {
            words[pos] = word;
        }
    }

    public Word getWord(int pos) {
        //return 0 if word does not exist.
        if (pos >= words.length) {
            return new Word(Conversion.intToBytes(0)); 
        }

        return words[pos];
    }

    public Word[] getWords() {
        return words;
    }
}
