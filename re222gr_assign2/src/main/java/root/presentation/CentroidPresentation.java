package root.presentation;

import static java.util.stream.Collectors.toList;

import java.util.List;

import root.models.BlogModel;
import root.models.CentroidModel;

public class CentroidPresentation {
	List<String> blogs;
	
	public CentroidPresentation() {
		
	}

	public CentroidPresentation(CentroidModel centroid){
		this.blogs = centroid.getAssignedBlogs()
				.stream()
				.map(BlogModel::getBlogName)
				.collect(toList());
	}

	public List<String> getBlogs() {
		return blogs;
	}
}