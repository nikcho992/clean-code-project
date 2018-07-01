package clean.code.cinema.online.system.reservation;

import java.util.Collection;

import clean.code.cinema.online.system.reservation.core.*;
import clean.code.cinema.online.system.reservation.exceptions.*;

public interface CinemaReservationSystem {
		
		public void bookTicket(Ticket ticket) throws AlreadyReservedException, ProjectionNotFoundException,
	    InvalidSeatException, ExpiredProjectionException;
		
		public void cancelTicket(Ticket ticket) throws ReservationNotFoundException, ProjectionNotFoundException;
		
		public int getFreeSeats(Projection projection) throws ProjectionNotFoundException;
		
		public Collection<Movie> getSortedMoviesByGenre();
}
