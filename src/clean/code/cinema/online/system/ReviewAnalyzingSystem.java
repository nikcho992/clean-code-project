package clean.code.cinema.online.system;

import clean.code.cinema.online.system.review.analyzer.MovieReviewAnalyzer;
import clean.code.cinema.online.system.review.analyzer.PairPointsOccurrences;

import java.util.HashMap;
import java.util.Map;

import clean.code.cinema.online.system.reservation.core.Movie;

public class ReviewAnalyzingSystem {

	private MovieReviewAnalyzer reviewAnalyzer;
	private Map<Movie, PairPointsOccurrences> movieAverageRating;

	public ReviewAnalyzingSystem(MovieReviewAnalyzer reviewAnalyzer) {
		this.reviewAnalyzer = reviewAnalyzer;
		movieAverageRating = new HashMap<>();
	}
	
	public Map<Movie, PairPointsOccurrences> getMovieAverageRatingMap() {
		return this.movieAverageRating;
	}
	
	public void processMovieReview(Movie movie, String review) {
		double reviewRating = reviewAnalyzer.getReviewSentiment(review);

		if (!movieAverageRating.containsKey(movie)) {
			movieAverageRating.put(movie, new PairPointsOccurrences(reviewRating, 1));
		} else {
			movieAverageRating.put(movie,
					new PairPointsOccurrences(movieAverageRating.get(movie).getReviewPoints() + reviewRating,
							movieAverageRating.get(movie).getOccurrences() + 1));
		}
	}
	
	public double getAverageMovieRating(Movie movie) {
		if (!movieAverageRating.containsKey(movie)) {
			throw new IllegalArgumentException("This movie has no reviews.");
		} 
		
		double totalMovieRating = movieAverageRating.get(movie).getReviewPoints();
		double numberOfMovieReviews = movieAverageRating.get(movie).getOccurrences();
		double averageMovieRating = totalMovieRating / numberOfMovieReviews;
		
		return averageMovieRating;
	}
}
