package clean.code.cinema.online.system.reservation;

import java.util.Collection;

import clean.code.cinema.online.system.reservation.core.*;
import clean.code.cinema.online.system.reservation.exceptions.*;

public interface CinemaReservationSystem {

	/**
	 * Books a ticket for a particular projection
	 * 
	 * @param ticket
	 *            The ticket that we want to book
	 * @throws AlreadyReservedException
	 *             If the same ticket was already reserved
	 * @throws ProjectionNotFoundException
	 *             If the ticket's corresponding projection is not in the system
	 * @throws InvalidSeatException
	 *             If there is no such seat in the hall for this projection
	 * @throws ExpiredProjectionException
	 *             If the projection is already expired, i.e if the projection's day
	 *             passed.
	 */
	public void bookTicket(Ticket ticket) throws AlreadyReservedException, ProjectionNotFoundException,
			InvalidSeatException, ExpiredProjectionException;

	/**
	 * Cancels a reservation for a particular projection
	 * 
	 * @param ticket
	 *            The ticket that we want to cancel
	 * @throws ReservationNotFoundException
	 *             If the ticket is not found in the system
	 * @throws ProjectionNotFoundException
	 *             If the projection is not found in the system
	 */
	public void cancelTicket(Ticket ticket) throws ReservationNotFoundException, ProjectionNotFoundException;

	/**
	 * Gets the number of free seats for a particular projection
	 * 
	 * @param projection
	 *            The projection for which we want to get the free seats
	 * @return the number of free seats for a particular projection
	 * @throws ProjectionNotFoundException
	 *             if the projection is not found in the system
	 */
	public int getFreeSeats(Projection projection) throws ProjectionNotFoundException;

	/**
	 * Returns a collection of movies sorted by their genre in alphabetic order.
	 * 
	 * @return Collection of movies sorted by their genre in alphabetic order.
	 */
	public Collection<Movie> getSortedMoviesByGenre();
}
