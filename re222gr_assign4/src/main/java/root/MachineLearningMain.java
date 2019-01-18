package root;

import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class MachineLearningMain {
	public static final String DATA = "spiral.arff";

	public static void main(String[] args) throws Exception {
		startMLP();
		startLogistic();
	}
	
	public static Instances readFile(String filePath) throws Exception {
		DataSource source = new DataSource(DATA);
		Instances data = source.getDataSet();
		 // setting class attribute if the data format does not provide this information
		 // For example, the XRFF format saves the class attribute information as well
		if(data.classIndex() == -1) {
			data.setClassIndex(data.numAttributes() - 1);		
			return data;			
		}
		return null;	
	}
	
	private static void startLogistic() throws Exception {
		Instances data = readFile(DATA);
		
		/** Classifier here is Linear Regression */
		Classifier cl = new Logistic();
		/** */
		cl.buildClassifier(data);
		/**
		 * train the alogorithm with the training data and evaluate the
		 * algorithm with testing data
		 */
		Evaluation evaluation = new Evaluation(data);
//		evaluation.evaluateModel(cl, data);
		evaluation.crossValidateModel(cl, data, 10, new Random(1));
		System.out.println(evaluation.toSummaryString("LOGISTIC:\n", true));
		
		
	}

	private static void startMLP() {
		try {		
			Instances data = readFile(DATA);	
			
			Classifier cl = new MultilayerPerceptron();
			cl.buildClassifier(data);
	        ((MultilayerPerceptron) cl).setHiddenLayers("72");
			Evaluation evaluation = new Evaluation(data);
//	        Evaluates the classifier on a given set of instances
//			evaluation.evaluateModel(cl, data);
			
//			Performs a (stratified if class is nominal) cross-validation for a classifier on a set of instances.
			evaluation.crossValidateModel(cl, data, 10, new Random(1));

			System.out.println(evaluation.toSummaryString("MLP:\n", true));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
