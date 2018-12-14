package root;

import java.util.List;

import root.methods.KMeans;
import root.presentation.CentroidPresentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ClusterMain {
	@Autowired
	KMeans k;
	
	public static void main(String[] args){
		SpringApplication.run(ClusterMain.class, args);
	}

	@GetMapping("/k/{n}")
	List<CentroidPresentation> kMeansClustering(@PathVariable Integer n){
		List<CentroidPresentation> kClustering = k.cluster(n);
		return kClustering;
	}
}
