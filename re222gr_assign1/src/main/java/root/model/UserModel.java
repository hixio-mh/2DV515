package root.model;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
	private List<RatingModel> ratings;
	private String username;
	private long userId;
	
	public UserModel() {
		
	}
	
	public UserModel(long userId, String username){
		this.userId = userId;
		this.username = username;
		ratings = new ArrayList<>();
	}

	public void addRating(RatingModel rating){
		ratings.add(rating);
	}

	public String getUsername() {
		return username;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public List<RatingModel> getRatings(){
		return ratings;
	}
}
