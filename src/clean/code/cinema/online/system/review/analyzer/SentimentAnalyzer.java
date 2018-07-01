package clean.code.cinema.online.system.review.analyzer;

import java.util.Collection;

public interface SentimentAnalyzer {
	 	
	double getReviewSentiment(String review);

	String getReviewSentimentAsName(String review);

	double getWordSentiment(String word);

	Collection<String> getMostFrequentWords(int n);

	Collection<String> getMostPositiveWords(int n);

	Collection<String> getMostNegativeWords(int n);

	int getSentimentDictionarySize();

	boolean isStopWord(String word);
}
