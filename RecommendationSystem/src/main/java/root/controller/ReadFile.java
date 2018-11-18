package root.controller;

import static java.util.Objects.nonNull;
import static java.lang.Long.parseLong;
import static java.lang.Double.parseDouble;

import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.io.FileReader;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import java.util.function.Consumer;
import java.io.BufferedReader;

import org.springframework.stereotype.Component;

@Component
public class ReadFile {
	private ClassLoader cl = getClass().getClassLoader();
	private Map<Long, UserModel> allUsersId = new HashMap<>();

	// all users ids
	public List<UserModel> AllUsersId() {
		ArrayList<UserModel> listOfIds = new ArrayList<>(allUsersId.values());
		return listOfIds;
	}
	
	// get chosen users id
	public UserModel getUserId(long userId) {
		UserModel chosenUserId = allUsersId.get(userId);
		return chosenUserId;
	}
	
	@PostConstruct
	public void getFiles() {
		try {
			readFile(this::handlingUsers, "users.csv");
			readFile(this::handlingRating, "ratings.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readFile(Consumer<String> parsedData, String classPath) throws IOException {
		String line;
		File movieNames = new File(cl.getResource(classPath).getFile());
		FileReader readMovies = new FileReader(movieNames);
		BufferedReader br = new BufferedReader(readMovies);

		while (nonNull(line = br.readLine())) {
			parsedData.accept(line);
		}
		br.close();
	}

	public void handlingUsers(String text) {
		// formating raw data 
		String[] splittedText = text.split(";", 2);
		// parse splitted data string to long 
		Long userId = parseLong(splittedText[1]);
		UserModel userModel = new UserModel(userId, splittedText[0]);
		
		putInUserModel(userId, userModel);
	}

	private void putInUserModel(Long userId, UserModel userModel) {
		// puts id in user model
		allUsersId.put(userId, userModel);	
		
	}
	
	public void handlingRating(String text) {
		// formating raw data 
		String[] splittedText = text.split(";", 3);
		// parse splitted data string to long 
		Long userId = parseLong(splittedText[0]);
		RatingModel ratingData = new RatingModel(splittedText[1], parseDouble(splittedText[2]));
		
		putRatingsInRatingModel(ratingData, userId);
	}
	
	private void putRatingsInRatingModel(RatingModel ratingData, Long userId) {
		// generate the ratings in RatingModel
		Optional.ofNullable(allUsersId.get(userId)).ifPresent(user -> user.addRaiting(ratingData));
	}
}
