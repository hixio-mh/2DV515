package root;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Spider {
	private static final int MAX_PAGES_TO_REACH = 200;
	private Set<String> pagesVisited = new HashSet<String>();
	private List<String> pagesToVisit = new LinkedList<String>();
//	private SortedSet<String> pagesToVisit = new TreeSet<String>();

	public void getURL(String url) {
		while (this.pagesVisited.size() <= MAX_PAGES_TO_REACH) {
			String currentURL;
			SpiderLeg leg = new SpiderLeg();
			this.pagesToVisit.add(url);
			currentURL = this.nextURL();
			leg.crawl(currentURL);
			if(this.pagesVisited.size() <= MAX_PAGES_TO_REACH) {				
				this.pagesToVisit.addAll(leg.getLinks());
			}
		}
	}

	private String nextURL(){
		String nextURL;
		do{
			nextURL = this.pagesToVisit.remove(0);
//			nextURL = this.pagesToVisit.first();
//			this.pagesToVisit.remove(this.pagesToVisit.first());
		} 
		while(this.pagesVisited.contains(nextURL));
		this.pagesVisited.add(nextURL);
		return nextURL;
	}
}
