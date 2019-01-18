package root;

import static java.util.stream.Collectors.toList;

import java.util.List;

import root.methods.Search;
import root.models.PageDBModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SearchMain {
	
	public static void main(String[] args){
		SpringApplication.run(SearchMain.class, args);
	}
	@Autowired
	private Search search;

	@GetMapping("/search")
	public List<PageDBModel> search(@RequestParam(value = "query") List<String> query){
			query = query
					.stream()
					.map(String::toLowerCase)
					.collect(toList());
			
			List<PageDBModel> searchResult = search.search(query);		
			return searchResult;
	}
}
