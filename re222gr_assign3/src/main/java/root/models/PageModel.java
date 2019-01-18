package root.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PageModel {
	private String page;
	private String link;
	private List<Integer> words = new ArrayList<>();
	private Set<String> links;
	private double pageRank = 1.0;

	public PageModel(String page){
		this.page = page;
		link = "/wiki/" + page;
		links = new HashSet<>();
		
	}

	/**
	 * List of words hash codes in order mentioned in the wiki page.
	 * @return
	 */
	public List<Integer> getWords() {
		return words;
	}
	
	public String getLink() {
		return link;
	}

	public void addWord(String word){
		words.add(word.hashCode());
	}

	public String getPage() {
		return page;
	}


	public void addWords(List<Integer> collect) {
		this.words.addAll(collect);	
	}

	public void addOutgoingLink(String line) {
		links.add(line);
	}
	
	public void setPageRank(double pageRank) {
		this.pageRank = pageRank;
	}

	public boolean hasLinkTo(PageModel pageModel) {
		return links.contains(pageModel.getLink());
	}

	public int linksSize() {
		return links.size();
	}

	public double getPageRank() {
		return pageRank;
	}
}
