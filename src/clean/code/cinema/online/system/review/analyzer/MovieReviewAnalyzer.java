package clean.code.cinema.online.system.review.analyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieReviewAnalyzer implements SentimentAnalyzer{
	private final static String UNKNOWN = "unknown";
	private final static String NEGATIVE = "negative";
	private final static String SOMEWHAT_NEGATIVE = "somewhat negative";
	private final static String NEUTRAL = "neutral";
	private final static String SOMEWHAT_POSITIVE = "somewhat positive";
	private final static String POSITIVE = "positive";

	private Set<String> stopwords;

	// used for computing sentiment score; in the first element of the Pair
	// we will accumulate all review points for the word and in the second - how many
	// times the word occurred.
	private Map<String, PairPointsOccurrences> words;

	// used for storing the sentiment score calculated from the map 'words'
	private Map<String, Double> sentimentScore;
	private List<String> lineWords;

	public MovieReviewAnalyzer(String reviewsFileName, String stopwordsFileName) {
		this.stopwords = new HashSet<String>();
		this.words = new HashMap<String, PairPointsOccurrences>();
		this.sentimentScore = new HashMap<String, Double>();
		this.lineWords = new ArrayList<String>();
		this.readStopwords(stopwordsFileName);
		this.readMovieReviews(reviewsFileName);
		this.computeSentimentScore();
	}

	private void readStopwords(String stopwordsFileName) {

		String currentLine;

		try (BufferedReader br = new BufferedReader(new FileReader(stopwordsFileName))) {

			while ((currentLine = br.readLine()) != null) {
				stopwords.add(currentLine);
			}
		} catch (IOException e) {
			e.getMessage();
		}
	}

	private void processSingleWord(String word, double currentRating) {
		if (!stopwords.contains(word) && !words.containsKey(word)) {
			words.put(word, new PairPointsOccurrences(currentRating, 1));
		} else if (words.containsKey(word) && !stopwords.contains(word)) {
			words.put(word, new PairPointsOccurrences(words.get(word).getReviewPoints() + currentRating,
					words.get(word).getOccurrences() + 1));
		}
	}

	private void readMovieReviews(String reviewsFileName) {
		String currentLine;
		double currentRating;

		try (BufferedReader br = new BufferedReader(new FileReader(reviewsFileName))) {
			while ((currentLine = br.readLine()) != null) {
				currentRating = 0.0;
				lineWords.clear();

				currentLine = currentLine.toLowerCase();
				lineWords.addAll(Arrays.asList(
						currentLine.split("[\\s\\t\\+\\=\\$\\&\\#\\;\\-\\.\\'\\?\\,\\\"\\!\\'\\`\\/\\:\\\\*]+")));
				currentRating = Double.parseDouble(lineWords.get(0));
				lineWords.remove(0);

				for (String word : this.lineWords) {
					processSingleWord(word, currentRating);
				}
			}
		} catch (IOException e) {
			e.getMessage();
		}
	}

	private void computeSentimentScore() {

		for (Map.Entry<String, PairPointsOccurrences> entry : words.entrySet()) {

			sentimentScore.put(entry.getKey(),
					entry.getValue().getReviewPoints() / entry.getValue().getOccurrences());
		}
	}

	public double getReviewSentiment(String review) {

		lineWords.clear();
		review = review.toLowerCase();
		double sentimentScore = 0.0;
		int occurrences = 0;

		lineWords.addAll(
				Arrays.asList(review.split("[\\s\\t\\+\\=\\$\\&\\#\\;\\-\\.\\'\\?\\,\\\"\\!\\'\\`\\/\\:\\\\*]+")));

		for (String word : lineWords) {

			if (!stopwords.contains(word) && this.sentimentScore.containsKey(word)) {
				sentimentScore += this.sentimentScore.get(word);
				occurrences++;
			}
		}

		if (occurrences != 0) {
			return sentimentScore / occurrences;
		} else {
			return -1.0;
		}
	}

	public String getReviewSentimentAsName(String review) {

		double sentimentScore = getReviewSentiment(review);
		sentimentScore = Math.round(sentimentScore * 100.0) / 100.0;

		if (sentimentScore == -1.0) {
			return UNKNOWN;
		} else if (sentimentScore >= 0.0 && sentimentScore <= 0.49) {
			return NEGATIVE;
		} else if (sentimentScore >= 0.5 && sentimentScore <= 1.49) {
			return SOMEWHAT_NEGATIVE;
		} else if (sentimentScore >= 1.5 && sentimentScore <= 2.49) {
			return NEUTRAL;
		} else if (sentimentScore >= 2.5 && sentimentScore <= 3.49) {
			return SOMEWHAT_POSITIVE;
		} else {
			return POSITIVE;
		}
	}

	public double getWordSentiment(String word) {

		if (!sentimentScore.containsKey(word)) {
			return -1.0;
		} else {
			return sentimentScore.get(word);
		}
	}

	public List<String> getMostFrequentWords(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("You should enter a positive number");
		}

		List<String> mostFrequent = words.entrySet().stream()
				.sorted(Map.Entry.<String, PairPointsOccurrences>comparingByValue(
						Comparator.comparing(PairPointsOccurrences::getOccurrences).reversed()))
				.map(Map.Entry::getKey).collect(Collectors.toList());

		if (n > words.size()) {
			return mostFrequent;
		} else {
			return mostFrequent.subList(0, n);
		}
	}

	public List<String> getMostPositiveWords(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("You should enter a positive number");
		}

		List<String> mostPositive = sentimentScore.entrySet().stream()
				.sorted(Map.Entry.<String, Double>comparingByValue().reversed()).map(Map.Entry::getKey)
				.collect(Collectors.toList());

		if (n > sentimentScore.size()) {
			return mostPositive;
		} else {
			return mostPositive.subList(0, n);
		}
	}

	public List<String> getMostNegativeWords(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("You should enter a positive number");
		}

		List<String> mostNegative = sentimentScore.entrySet().stream()
				.sorted(Comparator.comparing(Map.Entry::getValue)).map(Map.Entry::getKey).collect(Collectors.toList());

		if (n > sentimentScore.size()) {
			return mostNegative;
		} else {
			return mostNegative.subList(0, n);
		}
	}

	public int getSentimentDictionarySize() {
		return sentimentScore.size();
	}

	public boolean isStopWord(String word) {
		return stopwords.contains(word);
	}
}
