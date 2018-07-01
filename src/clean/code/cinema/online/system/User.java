package clean.code.cinema.online.system;

import java.util.HashSet;
import java.util.Set;

import clean.code.cinema.online.system.reservation.Reservation;
import clean.code.cinema.online.system.reservation.core.Movie;
import clean.code.cinema.online.system.reservation.core.Ticket;
import clean.code.cinema.online.system.reservation.exceptions.AlreadyReservedException;
import clean.code.cinema.online.system.reservation.exceptions.ExpiredProjectionException;
import clean.code.cinema.online.system.reservation.exceptions.InvalidSeatException;
import clean.code.cinema.online.system.reservation.exceptions.ProjectionNotFoundException;
import clean.code.cinema.online.system.reservation.exceptions.ReservationNotFoundException;

public class User {
	
	private String userName;
	private Set<Ticket> boughtTickets;
	private Reservation reservationSystem;
	private ReviewAnalyzingSystem reviewAnalyzing;
	
	public User(String userName, Reservation reservationSystem, ReviewAnalyzingSystem reviewAnalyzing) {
		this.setUsername(userName);
		this.reservationSystem = reservationSystem;
		this.reviewAnalyzing = reviewAnalyzing;
		boughtTickets = new HashSet<>();
	}
	
	public void setUsername(String userName) {
		this.userName = userName;
	}
	
	public String getUsername(String userName) {
		return this.userName;
	}
	
	public Set<Ticket> getBoughtTickets() {
		return this.boughtTickets;
	}
	
	public void bookTicket(Ticket ticket) throws AlreadyReservedException, ProjectionNotFoundException, InvalidSeatException, ExpiredProjectionException {
		reservationSystem.bookTicket(ticket);
		boughtTickets.add(ticket);
		
	}
	
	public void cancelTicket(Ticket ticket) throws ReservationNotFoundException, ProjectionNotFoundException {
		reservationSystem.cancelTicket(ticket);
		boughtTickets.remove(ticket);
	}
	
	public String checkWhenMovieIsShown(Movie movie) {
		return reservationSystem.checkWhenMovieIsShown(movie);
	}
	
	public void writeMovieReview(Movie movie, String review) {
		reviewAnalyzing.processMovieReview(movie, review);
	}
	
	public double getAverageMovieRating(Movie movie) {
		return reviewAnalyzing.getAverageMovieRating(movie);
	}
}
