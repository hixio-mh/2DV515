package root.controller;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
	private List<RatingModel> ratings = new ArrayList<>();
	private String username;
	private long userId;
	
	public UserModel() {
	}
	
	public UserModel(long userId, String username){
		this.userId = userId;
		this.username = username;
	}
	
	public void addRaiting(RatingModel rating){
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
