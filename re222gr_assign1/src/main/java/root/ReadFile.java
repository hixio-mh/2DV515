package root;

import static java.lang.Double.parseDouble;
import static java.lang.Long.parseLong;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import root.model.RatingModel;
import root.model.UserModel;

@Component
public class ReadFile {
	private ClassLoader cl = getClass().getClassLoader();
	private Map<Long, UserModel> allUsersId = new HashMap<>();

	@PostConstruct
	public void getFiles() throws IOException {
		readFile("users.csv", this::handlingUsers);
		readFile("ratings.csv", this::parseRating);
	}

	public UserModel getUserId(long userId) {
		UserModel chosenUserId = allUsersId.get(userId);
		return chosenUserId;
	}
	
	public List<UserModel> getAllUsersId(){
		return new ArrayList<>(allUsersId.values());
	}

	public void readFile(String classPath, Consumer<String> parsedData) throws IOException {
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
	
	public void parseRating(String text){
		String[] splittedText  = text.split(";", 3);
		Long userId = parseLong(splittedText [0]);
		ofNullable(allUsersId.get(userId))
				.ifPresent(user -> user.addRating(new RatingModel(splittedText [1], parseDouble(splittedText [2]), user)));
	}
}
