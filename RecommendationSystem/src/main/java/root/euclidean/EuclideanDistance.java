package root.euclidean;

import static java.lang.Double.compare;
import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import static java.math.BigDecimal.ROUND_HALF_UP;
import java.util.List;

import root.controller.RatingModel;
import root.controller.ReadFile;
import root.controller.UserModel;
import root.controller.UserMatchesUser;
import root.controller.MatchesToUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EuclideanDistance {
	@Autowired
	private ReadFile readFile;

	public double euclidean(UserModel A, UserModel B) {
		float sim = 0;
		float n = 0;

		for (RatingModel rA : A.getRatings()) {
			for (RatingModel rB : B.getRatings()) {
				if (rA.getMovieName().equals(rB.getMovieName())) {
					sim = (float) (sim + Math.pow(rA.getMovieScore() - rB.getMovieScore(), 2.0));
					n++;
					if (n == 0) {
						return 0;
					}
				}
			}
		}
//		double similarity = 1.0 / (1.0 + sim);
//		similarity = new BigDecimal(similarity).doubleValue();
//		return similarity;
		@SuppressWarnings("deprecation")
		double similarity = new BigDecimal(1.0 / (1.0 + sim))
				.setScale(3, ROUND_HALF_UP)
				.doubleValue();
		return similarity;
	}

	public MatchesToUser euclideanSimilarity(Long userId) {
		UserModel chosenUser = readFile.getUserId(userId);
		List<UserModel> AllUsers = readFile.AllUsersId();

		// from github
		MatchesToUser matches = new MatchesToUser(chosenUser, AllUsers.stream()
				.filter(restUsers -> restUsers != chosenUser)
				.map(restUsers -> new UserMatchesUser(restUsers.getUsername(), euclidean(chosenUser, restUsers)))
				.sorted((userA, userB) -> compare(userB.getMatchScore(), userA.getMatchScore())).collect(toList()));

		return matches;
	}
}
