package root.models;

import java.util.Map;

public class BlogModel implements UserModel {
	private String blogName;
	private Map<String, Double> wordsToAmountUsed;

	public BlogModel() {
		
	}
	
	public BlogModel(String blogName, Map<String, Double> wordsToAmountUsed){
		this.blogName = blogName;
		this.wordsToAmountUsed = wordsToAmountUsed;
	}

	public String getBlogName() {
		return blogName;
	}

	@Override
	public Map<String, Double> getWordsToAmountUsed() {
		return wordsToAmountUsed;
	}
}
