package root.models;
import root.models.PageModel;

public class PageDBModel implements Comparable<PageDBModel> {
	private final double wordFrequency;
	private final double documentLocation;
	private final double wordDistance;
	private final double pageRank;
	private final String page;
	private final double totSum;

	public PageDBModel(PageModel page, double wordFrequency, double documentLocation, double wordDistance){
		this.page = page.getPage();
		this.wordFrequency = wordFrequency;
		this.documentLocation = documentLocation;
		this.wordDistance = wordDistance;
		pageRank = page.getPageRank() * 0.5;

		totSum = pageRank + this.documentLocation + this.wordFrequency;
	}

	public String getPage() {
		return page;
	}

	public Double getTotSum() {
		return totSum;
	}

	public Double getDocumentLocation() {
		return documentLocation;
	}

	public Double getPageRank() {
		return pageRank;
	}

	public Double getWordFrequency() {
		return wordFrequency;
	}

	@Override
	public int compareTo(PageDBModel other) {
		int compare = other.getTotSum().compareTo(this.getTotSum());
		return compare;
	}
}
