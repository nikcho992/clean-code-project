package clean.code.cinema.online.system.reservation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import clean.code.cinema.online.system.reservation.core.*;
import clean.code.cinema.online.system.reservation.exceptions.*;

public class Reservation implements CinemaReservationSystem {
	
	private Map<Movie, Set<Projection>> cinemaProgram;
	private Set<Ticket> tickets;

	public Reservation(Map<Movie, Set<Projection>> cinemaProgram) {
		this.cinemaProgram = cinemaProgram;
		this.tickets = new HashSet<>();
	}
	
	public boolean isProjectionPresent(Ticket ticket) {
		return cinemaProgram.get(ticket.getMovie()).contains(ticket.getProjection());
	}
	
	public void bookTicket(Ticket ticket) throws AlreadyReservedException, ProjectionNotFoundException,
			InvalidSeatException, ExpiredProjectionException {

		if (!isProjectionPresent(ticket)) {
			throw new ProjectionNotFoundException("Projection not found.");
		}

		if (isProjectionPresent(ticket)) {
			if (ticket.isExpired()) {
				throw new ExpiredProjectionException("Expired projection");
			}

			if (ticket.hasInvalidSeat()) {
				throw new InvalidSeatException("Invalid seat.");
			}

			if (ticket.getSeat().isTaken()) {
				throw new AlreadyReservedException("Already reserved ticket.");
			}
		}

		ticket.book();
		tickets.add(ticket);
	}

	public void cancelTicket(Ticket ticket) throws ReservationNotFoundException, ProjectionNotFoundException {

		if (!isProjectionPresent(ticket)) {
			throw new ProjectionNotFoundException("Projection not found.");
		}

		if (!tickets.contains(ticket)) {
			throw new ReservationNotFoundException("Reservation not found.");
		}

		ticket.cancel();
		tickets.remove(ticket);
	}

	public int getFreeSeats(Projection projection) throws ProjectionNotFoundException {

		if (!cinemaProgram.get(projection.getMovie()).contains(projection)) {
			throw new ProjectionNotFoundException("Projection not found.");
		}

		return projection.getFreeSeats();
	}

	public Collection<Movie> getSortedMoviesByGenre() {

		List<Movie> movies = new ArrayList<>(cinemaProgram.keySet());

		Collections.sort(movies);
		return movies;
	}
	
	public String checkWhenMovieIsShown(Movie movie) {
		Set<LocalDateTime> movieShowTimes = new HashSet<>();
		
		for (Projection currentProjection : cinemaProgram.get(movie)) {
			movieShowTimes.add(currentProjection.getDate());
		}
		return movieShowTimes.toString();
	}
}
