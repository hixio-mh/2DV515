package root.models;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CentroidModel implements UserModel {
	private Map<String, Double> wordsToAmountUsed;
	private Set<BlogModel> assignedBlogs;
	private Set<BlogModel> previousAssignments;
	private int id;
	
	public CentroidModel() {
		
	}

	public CentroidModel(int id, Map<String, Double> wordsToAmountUsed){
		this.id = id;
		this.wordsToAmountUsed = wordsToAmountUsed;
		assignedBlogs = new HashSet<>();
	}

	@Override
	public Map<String, Double> getWordsToAmountUsed() {
		return wordsToAmountUsed;
	}

	public Set<BlogModel> getAssignedBlogs() {
		return assignedBlogs;
	}

	public int getId() {
		return id;
	}

	public void resetAssignments(){
		previousAssignments = assignedBlogs;
		assignedBlogs = new HashSet<>();
	}

	public void assignBlogs(BlogModel b){
		assignedBlogs.add(b);
	}

	public void centralizationBlogs() {
			wordsToAmountUsed.entrySet().forEach(this::updateToAverageWordCountOfAssignedBlogs);
	}

	private void updateToAverageWordCountOfAssignedBlogs(Map.Entry<String, Double> entry){
		double totalUsage = assignedBlogs.stream().mapToDouble(b -> b.getWordsToAmountUsed().get(entry.getKey())).sum();
		entry.setValue(totalUsage / assignedBlogs.size());
	}

	public boolean hasNewAssignments() {
		boolean newAssignment = previousAssignments.containsAll(assignedBlogs) && previousAssignments.size() == assignedBlogs.size();
		if(newAssignment) {
			return true;
		}else{
			return false;
		}
	}
}
