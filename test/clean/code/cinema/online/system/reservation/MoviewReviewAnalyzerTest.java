package clean.code.cinema.online.system.reservation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import clean.code.cinema.online.system.review.analyzer.MovieReviewAnalyzer;

public class MoviewReviewAnalyzerTest {

	private final String REVIEW_1 = "It was such a compelling story. I think this is one amazing and wonderful movie. Totally recommend it.";
	private final String REVIEW_2 = "Понеже всички български думи са непознати за алгоритъма ни, очаквам отрицателен резултат.";
	private final String REVIEW_3 = "Понеже всички български думи са непознати за алгоритъма ни, очаквам резулат ънноун.";
	private final String REVIEW_4 = "Overacted and miserable.";
	private final String REVIEW_5 = "Despite being boring and trivial, it was not the worst.";
	private final String REVIEW_6 = "Despite being boring and trivial, there was something positive.";
	private final String REVIEW_7 = "Refreshing and positive movie.";
	private final String REVIEW_8 = "Immersive ecstasy: energizing artwork!";

	MovieReviewAnalyzer analyzer = new MovieReviewAnalyzer("movieReviews.txt", "stopwords.txt");
	MovieReviewAnalyzer customAnalyzer = new MovieReviewAnalyzer("myReview.txt", "myStop.txt");

	@Test
	public void testShouldGetReviewSentimentIncludingKnownWords() {

		assertEquals((Double) 2.297598058036491, (Double) analyzer.getReviewSentiment(REVIEW_1));
	}

	@Test
	public void testShouldGetReviewSentimentIncludingOnlyUnknownWords() {

		assertEquals((Double) (-1.0), (Double) analyzer.getReviewSentiment(REVIEW_2));
	}

	@Test
	public void testShouldGetReviewSentimentUnknown() {

		assertEquals("unknown", analyzer.getReviewSentimentAsName(REVIEW_3));
	}

	@Test
	public void testShouldGetReviewSentimentNegative() {

		assertEquals("negative", analyzer.getReviewSentimentAsName(REVIEW_4));
	}

	@Test
	public void testShouldGetReviewSentimentSomewhatNegative() {

		assertEquals("somewhat negative", analyzer.getReviewSentimentAsName(REVIEW_5));
	}

	@Test
	public void testShouldGetReviewSentimentNeutral() {

		assertEquals("neutral", analyzer.getReviewSentimentAsName(REVIEW_6));
	}

	@Test
	public void testShouldGetReviewSentimentSomewhatPositive() {

		assertEquals("somewhat positive", analyzer.getReviewSentimentAsName(REVIEW_7));
	}

	@Test
	public void testShouldGetReviwSentimentPositive() {

		assertEquals("positive", analyzer.getReviewSentimentAsName(REVIEW_8));
	}

	@Test
	public void testShouldGetWordSentimentUnknown() {

		assertEquals((Double) (-1.0), (Double) analyzer.getWordSentiment("debilnaDuma"));
	}

	@Test
	public void testShouldGetWordSentimentKnown() {

		assertEquals((Double) 3.2857142857142856, (Double) analyzer.getWordSentiment("amazing"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShouldThrowIndexOutOfBoundsExceptionInMostFrequentWords() {

		analyzer.getMostFrequentWords(-312);
	}

	@Test
	public void testShouldGetAllMostFrequentWordsWhenNIsLargerThanSize() {
		List<String> expectedMostFrequent = new ArrayList<>(
				Arrays.asList("amazing", "passion", "movie", "missed", "potential", "full", "little"));

		assertEquals(expectedMostFrequent, customAnalyzer.getMostFrequentWords(188888));
	}

	@Test
	public void testShouldGetTopTenMostFrequentWords() {

		List<String> expectedMostFrequent = new ArrayList<>(
				Arrays.asList("s", "film", "movie", "t", "n", "one", "like", "rrb", "lrb", "story"));

		assertEquals(expectedMostFrequent, analyzer.getMostFrequentWords(10));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShouldThrowIndexOutOfBoundsExceptionInMostPositiveWords() {

		analyzer.getMostPositiveWords(-1);
	}

	@Test

	public void testShouldGetAllMostPositiveWordsWhenNIsLargerThanSize() {
		List<String> expectedMostPositive = new ArrayList<>(

				Arrays.asList("movie", "full", "amazing", "passion", "missed", "potential", "little"));

		assertEquals(expectedMostPositive, customAnalyzer.getMostPositiveWords(13123));

	}

	@Test
	public void testShouldGetTopTenMostPositiveWords() {

		List<String> expectedMostPositive = new ArrayList<>(Arrays.asList("impersonation", "compassionate",
				"depictions", "skeleton", "rosa", "kudos", "hilary", "skateboards", "specialized", "fatter"));

		assertEquals(expectedMostPositive, analyzer.getMostPositiveWords(10));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShouldThrowIndexOutOfBoundsExceptionInMostNegativeWords() {

		analyzer.getMostNegativeWords(-53);
	}

	@Test
	public void testShouldGetAllMostNegativeWordsWhenNIsLargerThanSize() {
		List<String> expectedMostNegative = new ArrayList<>(
				Arrays.asList("missed", "potential", "little", "amazing", "passion", "movie", "full"));

		assertEquals(expectedMostNegative, customAnalyzer.getMostNegativeWords(13123));

	}

	@Test
	public void testShouldGetTopTenMostNegativeWords() {

		List<String> expectedMostNegative = new ArrayList<>(Arrays.asList("pics", "turd", "resume", "claptrap",
				"walker", "embody", "cancer", "burlesque", "flog", "muzak"));

		assertEquals(expectedMostNegative, analyzer.getMostNegativeWords(10));
	}

	@Test
	public void testShouldGetSentimentDictionarySize() {

		assertEquals((Integer) 15079, (Integer) analyzer.getSentimentDictionarySize());
	}

	@Test
	public void testShouldReturnThatAWordIsAStopword() {

		assertTrue(analyzer.isStopWord("don't"));
	}

	@Test
	public void testShouldReturnThatAWordIsNotAStopword() {

		assertFalse(analyzer.isStopWord("magnificent"));
	}
}
