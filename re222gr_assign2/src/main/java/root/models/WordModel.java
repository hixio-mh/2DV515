package root.models;

import static java.lang.Integer.MAX_VALUE;

public class WordModel {
	private String word;
	private int min;
	private int max;

	public WordModel(){
		
	}

	public WordModel(String word){
		this.word = word;
		min = MAX_VALUE;
		max = 0;
	}

	public String getWord() {
		return word;
	}

	public int getMax() {
		return max;
	}

	public int getMin() {
		return min;
	}

	public void updateWordMinMaxUsage(int usage){
		if (min > usage){
			min = usage;
		}
		if (max < usage){
			max = usage;
		}
	}
}
