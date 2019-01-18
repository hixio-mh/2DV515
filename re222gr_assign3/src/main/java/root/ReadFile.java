package root;

import static java.util.Arrays.asList;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import root.models.PageModel;

@Component
public class ReadFile {
	private static final String WIKI_WORDS = "wikipedia/Words";
	private static final String WIKI_LINKS = "wikipedia/Links";
	private Map<String, PageModel> pageNames;

	public Collection<PageModel> getPages() {
		Collection<PageModel> allPages = pageNames.values();
		return allPages;
	}

	@PostConstruct
	private void getFiles() throws RuntimeException, IOException {
		pageNames = new HashMap<>();
		String line;

			File wikiWords = new File(getClass().getClassLoader().getResource(WIKI_WORDS).getPath());
			for (String wordCategory : wikiWords.list()) {
				String data = wikiWords.getAbsolutePath() + "/" + wordCategory;
				for (File file : new File(data).listFiles()) {

					String pageTitle = getPageName(file.getName());
					BufferedReader br = new BufferedReader(new FileReader(file));
					PageModel pageModel = new PageModel(pageTitle);
					
					while (nonNull(line = br.readLine())) {
						pageModel
						.addWords(asList(line.split("\\s+"))
								.stream().map(String::hashCode)
								.collect(toList()));
					}

					pageNames.put(pageTitle, pageModel);
					br.close();
				}
			}

			File wikiLinks = new File(getClass().getClassLoader().getResource(WIKI_LINKS).getPath());
			for (String linkCategory : wikiLinks.list()) {
				String data = wikiLinks.getAbsolutePath() + "/" + linkCategory;
				for (File file : new File(data).listFiles()) {

					String title = getPageName(file.getName());
					BufferedReader br = new BufferedReader(new FileReader(file));

					PageModel pageModel = pageNames.get(title);
					if (nonNull(pageModel)) {
						while (nonNull(line = br.readLine())) {
							pageModel.addOutgoingLink(line);
						}
					}
					br.close();
				}
			}
			calculatePR();
			normalizePR();
	}

	private String getPageName(String name) {
		return name;
	}
	
	private void calculatePR() {
		Map<PageModel, Double> ranking = new HashMap<>();
		for(int i = 0; i < 20; i++){
			getPages().forEach(page -> ranking.put(page, iteratePageRank(page)));
			ranking
			.forEach(PageModel::setPageRank);
		}	
	}

	private double iteratePageRank(PageModel pageModel) {
		double pr = getPages()
				.stream()
				.filter(page -> page.hasLinkTo(pageModel))
				.mapToDouble(page -> page.getPageRank() / page.linksSize())
				.sum();

		return pr * 0.85 + 0.15;
	}

	private void normalizePR() {
		double max = getPages()
				.stream()
				.map(PageModel::getPageRank)
				.max(Double::compareTo)
				.get();
		
				getPages()
				.stream()
				.forEach(p -> p.setPageRank(p.getPageRank() / max));
	}
}