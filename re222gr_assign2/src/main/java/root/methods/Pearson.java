package root.methods;

import static java.math.BigDecimal.ROUND_HALF_UP;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;

import root.models.UserModel;

@Service
public class Pearson {
	
	public double pearson(UserModel A, UserModel B){
		double sumA = 0;
		double sumB = 0; 
		double sumAsq = 0;
		double sumBsq = 0; 
		double pSum = 0;
		int n = 0;

		Map<String, Double> wordsA = A.getWordsToAmountUsed();
		Map<String, Double> wordsB = B.getWordsToAmountUsed();

		for(Map.Entry<String, Double> wordUsage : wordsA.entrySet()){
			String word = wordUsage.getKey();
			double valuesA = wordUsage.getValue();
			double amountOfUsesByB = wordsB.get(word);

			sumA = sumA + valuesA;
			sumB = sumB + amountOfUsesByB;

			sumAsq = sumAsq + Math.pow(valuesA, 2.0);
			sumBsq = sumBsq + Math.pow(amountOfUsesByB, 2.0);

			pSum = pSum + valuesA * amountOfUsesByB;
			n = n + 1;

		}
		if(n == 0)
			return 0;

		double num = pSum - ((sumA * sumB) / n);

		double resultA = sumAsq - (Math.pow(sumA, 2.0) / n);
		double resultB = sumBsq - (Math.pow(sumB, 2.0) / n);
		
		double den = Math.sqrt(resultA * resultB);

		double pearson = (1.0 - (num / den));
		
		return pearson;
	}
}
