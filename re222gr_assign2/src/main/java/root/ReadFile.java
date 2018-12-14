package root;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import root.models.BlogModel;
import root.models.WordModel;

@Component
public class ReadFile {
	private List<WordModel> words;
	private List<BlogModel> blogs;
	ClassLoader cl = getClass().getClassLoader();

	@PostConstruct
	void getFile() throws IOException {
		blogs = new ArrayList<>();
		words = new ArrayList<>();
		String filePath = cl.getResource("blogdata.txt").getFile();
		File file = new File(filePath);
		handleFile(file);
	}

	public List<WordModel> getWords() {
		return words;
	}

	public List<BlogModel> getBlogs() {
		return blogs;
	}
	
	private void handleFile(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		List<String> allWords = new ArrayList<>(asList(line.split("\\s")));
		allWords.remove(0);
		words = allWords
				.stream().map(WordModel::new)
				.collect(toList());
		
		while (nonNull(line = br.readLine())) {
			List<String> vals = new ArrayList<>(asList(line.split("\\t")));
			String blogName = vals.remove(0);
			blogs.add(new BlogModel(blogName, mapWordsToCount(vals)));
		}
		br.close();
	}

	private Map<String, Double> mapWordsToCount(List<String> wordCounts) {
		Map<String, Double> wordsToAmountUsed = new HashMap<>();

		range(0, words.size()).forEach(i -> {
			int wordUsage = parseInt(wordCounts.get(i));
			// Add word to map
			wordsToAmountUsed.put(words.get(i).getWord(), (double) wordUsage);
			// Update min/max usage of the word overall
			words.get(i).updateWordMinMaxUsage(wordUsage);

		});

		return wordsToAmountUsed;
	}
}
