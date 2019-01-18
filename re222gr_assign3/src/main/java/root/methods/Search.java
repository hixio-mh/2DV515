package root.methods;

import static java.lang.Double.max;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import root.ReadFile;
import root.models.PageModel;
import root.models.PageDBModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class Search {
	@Autowired
	private ReadFile readFile;
	
	public Search() throws IOException{
		
	}

	public List<PageDBModel> search(List<String> queryWords){
		Map<PageModel, Double> wordFrequency = new HashMap<>();
		Map<PageModel, Double> documentLocation = new HashMap<>();
		Map<PageModel, Double> wordDistance = new HashMap<>();
		Collection<PageModel> allPages = readFile.getPages();
		
		List<Integer> query = queryWords
				.stream()
				.map(String::hashCode)
				.collect(toList());


		for(PageModel p : allPages){
			wordFrequency.put(p, wordFrequencyScore(query, p));
			documentLocation.put(p, documentLocationScore(query, p));
			wordDistance.put(p, wordDistanceScore(query, p));
		}

		normalize(wordFrequency, false);
		normalize(documentLocation, true);
		normalize(wordDistance, true);

		 List<PageDBModel> pages = allPages.stream()
				.map(page -> new PageDBModel(page,
						wordFrequency.get(page),
						0.8 * documentLocation.get(page),
						wordDistance.get(page)))
				.sorted()
				.collect(toList());
		
		return pages;
	}

	private Double wordDistanceScore(List<Integer> query, PageModel pageModel) {
		double score = 0;
		for(int i = 0; i < query.size(); i++) {
			for(int k = i + 1; k < query.size(); k++) {
				Double A = documentLocationScore(singletonList(query.get(i)), pageModel);
				Double B = documentLocationScore(singletonList(query.get(k)), pageModel);
				if(A > 1000 || B > 1000) {
					score = 1000;
				}else {
					double diff = A - B;
					if(diff < 0) {
						diff = diff * - 1.0;
					}
					score = score + diff;
				}
			}
			
		}
		return score;
	}

	private double wordFrequencyScore(List<Integer> query, PageModel pageModel){
		double wordFreq = pageModel
				.getWords()
				.stream()
				.filter(query::contains)
				.count();
		return wordFreq;
	}

	private double documentLocationScore(List<Integer> query, PageModel pageModel){
		return query
				.stream()
				.mapToInt(word -> calculateLocationScore(word, pageModel.getWords())).sum();
	}
	
//	private double calculatePageRank(List<Integer> query, PageModel pageModel) {
//		return query.stream().mapToInt(word -> calculateLocationScore(word, pageModel.getWords())).sum();
//	}

	private int calculateLocationScore(Integer word, List<Integer> words){
		for(int i = 0; i < words.size(); i++){
			if(words.get(i).equals(word)){
				return i + 1;
			}
		}
		return 243330;
	}

	private void normalize(Map<PageModel, Double> scores, boolean smallIsBetter){
		double max = scores.values().stream().max(Double::compareTo).get();
		double min = scores.values().stream().min(Double::compareTo).get();
		if(smallIsBetter){
			scores
			.entrySet()
			.forEach(entry -> entry.setValue(min / max(entry.getValue(), 0.000001)));
		} else {
			scores
			.entrySet()
			.forEach(entry -> entry.setValue(entry.getValue() / max));
		}
	}
}
