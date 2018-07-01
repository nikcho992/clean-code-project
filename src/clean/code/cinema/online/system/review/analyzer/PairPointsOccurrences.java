package clean.code.cinema.online.system.review.analyzer;

public class PairPointsOccurrences {
	private double reviewPoints;
	private int occurrences;

	public PairPointsOccurrences(double reviewPoints, int occurrences) {
		this.setReviewPoints(reviewPoints);
		this.setOccurrences(occurrences);
	}

	public double getReviewPoints() {
		return this.reviewPoints;
	}

	public int getOccurrences() {
		return this.occurrences;
	}

	public void setReviewPoints(double reviewPoints) {
		this.reviewPoints = reviewPoints;
	}

	public void setOccurrences(int occurrences) {
		this.occurrences = occurrences;
	}
}
