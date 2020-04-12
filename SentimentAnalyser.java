import java.util.Arrays;

public class SentimentAnalyzer {
    // Tip: labeled continue can be used when iterating featureSet + convert review to lower-case
	public static int[] detectProsAndCons(String review, String[][] featureSet, String[] posOpinionWords,
		                                  String[] negOpinionWords) {

        String reviewToLower = review.toLowerCase();
		int[] featureOpinions = new int[featureSet.length]; // output
 
        // your code ~ you will be invoking getOpinionOnFeature		

        for(int i = 0, lengthOfFeatureSetDim1 = featureSet.length, opinion = 0; i < lengthOfFeatureSetDim1; i++){
            for(int j = 0, lengthOfFeatureSetDim2 = featureSet[i].length; j < lengthOfFeatureSetDim2; j++){
                opinion += getOpinionOnFeature(reviewToLower, featureSet[i][j], posOpinionWords, negOpinionWords);
            }

            featureOpinions[i] = opinion;

            opinion = 0;
        }
 
		return featureOpinions;
	}

    private static int getOpinionOnFeature(String review, String feature, String[] posOpinionWords, String[] negOpinionWords) {

        int opinion = checkForWasPhrasePattern(review, feature, posOpinionWords, negOpinionWords);

        if(opinion == 0){
            return checkForOpinionFirstPattern(review, feature, posOpinionWords, negOpinionWords);
        }

        return opinion;
	}

    private static int checkForWasPhrasePattern(String review, String feature, String[] posOpinionWords, String[] negOpinionWords) {
        int opinion = 0;
        String pattern = feature + " was ";


        for (String string : posOpinionWords) {

            if(review.contains(pattern + string)){
                opinion++;
                System.out.println("good");
            }
        }

        for (String string : negOpinionWords) {
            if(review.contains(pattern + string)){
                opinion--;
            }           
        }


		return opinion; 	
	}

    private static int checkForOpinionFirstPattern(String review, String feature, String[] posOpinionWords,
			String[] negOpinionWords) {
		// Extract sentences as feature might appear multiple times. 
		// split() takes a regular expression and "." is a special character 
		// for regular expression. So, escape it to make it work!!
        String[] sentences = review.split("\\.|!");
        int opinion = 0;

        for (String string : sentences) {

            for (String string2 : posOpinionWords) {
                if(string.contains(string2 + ' ' + feature)){
                    opinion++;

                System.out.println("good");
                }
            }

            for (String string2 : negOpinionWords) {
                if(string.contains(string2 + ' ' + feature)){
                    opinion--;

                }
            }
        }
		
		// your code for processing each sentence. You can return if opinion is found in a sentence (no need to process subsequent ones)		

		return opinion;
	}

    public static void main(String[] args) {
       String review = "Haven't been here in years! Fantastic service and the food was delicious! Definetly will be a frequent flyer! Francisco was very attentive";
		
	    //String review = "Sorry OG, but you just lost some loyal customers. Horrible service, no smile or greeting just attitude. The breadsticks were stale and burnt, appetizer was cold and the food came out before the salad.";
		
		String[][] featureSet = { 
		        { "ambiance", "ambience", "atmosphere", "decor" },
				{ "dessert", "ice cream", "desert" }, 
				{ "food" }, 
				{ "soup" },
				{ "service", "management", "waiter", "waitress", "bartender", "staff", "server" } };
		String[] posOpinionWords = { "good", "fantastic", "friendly", "great", "excellent", "amazing", "awesome",
				"delicious" };
        String[] negOpinionWords = { "slow", "bad", "horrible", "awful", "unprofessional", "poor" };


        System.out.println(Arrays.toString(detectProsAndCons(review, featureSet, posOpinionWords, negOpinionWords)));
        
        

    }
}