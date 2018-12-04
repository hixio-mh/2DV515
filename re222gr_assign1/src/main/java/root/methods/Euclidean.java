package root.methods;

import static java.lang.Double.compare;
import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.List;

import root.ReadFile;
import root.model.MatchModel;
import root.model.RatingModel;
import root.model.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Euclidean {
	@Autowired
	private ReadFile readFile;
	
	public double euclidean(UserModel A, UserModel B) {
		float sim = 0;
		float n = 0;

		for (RatingModel rA : A.getRatings()) {
			for (RatingModel rB : B.getRatings()) {
				if (rA.getMovie().equals(rB.getMovie())) {
					sim = (float) (sim + Math.pow(rA.getScore() - rB.getScore(), 2));
					n++;
					if (n == 0) {
						return 0;
					}
				}
			}
		}
		@SuppressWarnings("deprecation")
		double similarity = new BigDecimal(1/(1 + sim))
				.setScale(3, ROUND_HALF_UP)
				.doubleValue();
		return similarity;
	}

	public List<MatchModel> euclideanSimilarity(UserModel user){
		List<UserModel> allUsers = readFile.getAllUsersId();
		
		List<MatchModel> matches = allUsers.stream()
				.filter(otherUser -> otherUser != user)
				.map(otherUser -> new MatchModel(otherUser, euclidean(user, otherUser)))
				.limit(3)
				.sorted((userA, userB) -> compare(userB.getMatchScore(), userA.getMatchScore()))
				.collect(toList());
		
		return matches; 
	}
}
