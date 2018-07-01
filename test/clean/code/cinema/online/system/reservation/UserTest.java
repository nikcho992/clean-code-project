package clean.code.cinema.online.system.reservation;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import clean.code.cinema.online.system.ReviewAnalyzingSystem;
import clean.code.cinema.online.system.User;
import clean.code.cinema.online.system.reservation.core.Hall;
import clean.code.cinema.online.system.reservation.core.Movie;
import clean.code.cinema.online.system.reservation.core.MovieGenre;
import clean.code.cinema.online.system.reservation.core.Projection;
import clean.code.cinema.online.system.reservation.core.Seat;
import clean.code.cinema.online.system.reservation.core.Ticket;
import clean.code.cinema.online.system.reservation.exceptions.AlreadyReservedException;
import clean.code.cinema.online.system.reservation.exceptions.ExpiredProjectionException;
import clean.code.cinema.online.system.reservation.exceptions.InvalidSeatException;
import clean.code.cinema.online.system.reservation.exceptions.ProjectionNotFoundException;
import clean.code.cinema.online.system.reservation.exceptions.ReservationNotFoundException;
import clean.code.cinema.online.system.review.analyzer.MovieReviewAnalyzer;

public class UserTest {

	Set<Ticket> boughtTickets = new HashSet<>();
	Map<Movie, Set<Projection>> program = new HashMap<>();
	Reservation reservationSystem = new Reservation(program);
	MovieReviewAnalyzer analyzer = new MovieReviewAnalyzer("movieReviews.txt", "stopwords.txt");
	ReviewAnalyzingSystem reviewAnalyzing = new ReviewAnalyzingSystem(analyzer);

	User nick = new User("Nick", reservationSystem, reviewAnalyzing);

	Movie movie = new Movie("The best offer", 120, MovieGenre.THRILLER);
	Movie movie2 = new Movie("Lame", 100, MovieGenre.COMEDY);
	Movie dramaMovie = new Movie("No drama", 90, MovieGenre.DRAMA);

	Hall hall = new Hall(1, 10, 10);
	Hall hall2 = new Hall(2, 10, 10);

	Seat seat = new Seat(5, 5);
	Seat seat2 = new Seat(6, 6);

	Projection projection = new Projection(movie, hall, LocalDateTime.of(2019, 11, 12, 10, 30));
	Projection projection2 = new Projection(movie2, hall2, LocalDateTime.of(2019, 11, 12, 10, 35));

	Ticket ticket = new Ticket(projection, seat, "Nick");
	Ticket ticket2 = new Ticket(projection2, seat2, "John");
	Ticket ticket3 = new Ticket(projection, seat2, "Peter");

	Set<Projection> set1 = new HashSet<>();

	private final String REVIEW_1 = "It was such a compelling story. I think this is one amazing and wonderful movie. Totally recommend it.";
	private final String REVIEW_2 = "Refreshing and positive movie.";

	@Test
	public void testShouldCheckIfUserBooksTicketSuccessfully() throws AlreadyReservedException,
			ProjectionNotFoundException, InvalidSeatException, ExpiredProjectionException {
		set1.add(projection);
		program.put(movie, set1);

		nick.bookTicket(ticket);

		assertEquals(nick.getBoughtTickets().size(), 1);
		assertEquals(reservationSystem.getTickets().size(), 1);
	}

	@Test
	public void testShouldCheckIfUserCancelsTicketSuccessfully()
			throws AlreadyReservedException, ProjectionNotFoundException, InvalidSeatException,
			ExpiredProjectionException, ReservationNotFoundException {
		set1.add(projection);
		program.put(movie, set1);

		nick.bookTicket(ticket);
		nick.bookTicket(ticket3);
		nick.cancelTicket(ticket);

		assertEquals(nick.getBoughtTickets().size(), 1);
		assertEquals(reservationSystem.getTickets().size(), 1);
		assertTrue(nick.getBoughtTickets().contains(ticket3));
	}

	@Test
	public void testShouldCheckIfUserWritesMovieReviewSuccessfully() {
		nick.writeMovieReview(movie, REVIEW_1);

		assertTrue(reviewAnalyzing.getMovieAverageRatingMap().keySet().contains(movie));
		assertEquals(reviewAnalyzing.getMovieAverageRatingMap().get(movie).getOccurrences(), 1);
		assertEquals((Double) reviewAnalyzing.getMovieAverageRatingMap().get(movie).getReviewPoints(),
				(Double) analyzer.getReviewSentiment(REVIEW_1));
	}

	@Test
	public void testShouldCheckIfUserWritesMoreThanOneReviewsForTheSameMovieSuccessfully() {
		nick.writeMovieReview(movie, REVIEW_1);
		nick.writeMovieReview(movie, REVIEW_2);

		assertEquals(reviewAnalyzing.getMovieAverageRatingMap().get(movie).getOccurrences(), 2);
	}

	@Test
	public void testShouldGetAverageMovieRating() {
		nick.writeMovieReview(movie, REVIEW_1);
		nick.writeMovieReview(movie, REVIEW_2);

		double actualAverageRating = (analyzer.getReviewSentiment(REVIEW_1) + analyzer.getReviewSentiment(REVIEW_2)) / 2;

		double expectedAverageRating = nick.getAverageMovieRating(movie);

		assertEquals((Double) expectedAverageRating, (Double) actualAverageRating);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShouldThrowIllegalArgumentExceptionWhenGettingRatingForNotIncludedMovie() {
		nick.getAverageMovieRating(movie2);
	}
}
