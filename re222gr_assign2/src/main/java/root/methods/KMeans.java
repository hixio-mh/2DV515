package root.methods;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import root.ReadFile;
import root.models.BlogModel;
import root.models.CentroidModel;
import root.models.WordModel;
import root.presentation.CentroidPresentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KMeans {
	Random r = new Random();
	@Autowired
	private ReadFile readFile;
	@Autowired
	private Pearson pearson;

	public List<CentroidPresentation> cluster(int n){
		List<CentroidModel> centroids = newCentroids(n);
		List<BlogModel> blogs = readFile.getBlogs();
		boolean assignmentsUpdated = true;

		while(assignmentsUpdated){
			centroids.forEach(CentroidModel::resetAssignments);
			blogs.forEach(b -> getNearestCentroid(b, centroids));
			centroids.forEach(CentroidModel::centralizationBlogs);
			assignmentsUpdated = centroids
					.stream().anyMatch(CentroidModel::hasNewAssignments);
		}

		 		List<CentroidPresentation> presentation = centroids
				.stream()
				.map(CentroidPresentation::new)
				.collect(Collectors.toList());
		 		return presentation;
	}

	private void getNearestCentroid(BlogModel b, List<CentroidModel> centroids) {
		double distance = Double.MAX_VALUE;
		CentroidModel closest = null;
		for(CentroidModel c : centroids){
			double cDist = pearson.pearson(c, b);
			if(cDist < distance){
				closest = c;
				distance = cDist;
			}
		}

		ofNullable(closest).ifPresent(centroid -> centroid.assignBlogs(b));
	}


	private List<CentroidModel> newCentroids(int n){
		List<WordModel> datasetWords = readFile.getWords();

		List<CentroidModel> centroids = new ArrayList<>();
		for(int i = 1; i <= n; i++){
			centroids.add(new CentroidModel(i, generateRandomWordMap(datasetWords)));
		}
		return centroids;
	}

	private Map<String, Double> generateRandomWordMap(List<WordModel> datasetWords) {
		return datasetWords
				.stream()
				.collect(toMap(WordModel::getWord, word -> (double)r.nextInt(word.getMax() - word.getMin()) + word.getMin()));
	}
}
