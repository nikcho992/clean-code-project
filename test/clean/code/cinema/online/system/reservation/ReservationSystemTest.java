package clean.code.cinema.online.system.reservation;

import org.junit.Test;

import clean.code.cinema.online.system.reservation.exceptions.*;
import clean.code.cinema.online.system.reservation.core.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ReservationSystemTest {
	
	Movie movie = new Movie("The best offer", 120, MovieGenre.THRILLER);
	Movie movie2 = new Movie("Lame", 100, MovieGenre.COMEDY);

	Hall hall = new Hall(1, 10, 10);
	Hall hall2 = new Hall(2, 10, 10);

	Seat seat = new Seat(5, 5);
	Seat seat2 = new Seat(6, 6);

	Projection projection = new Projection(movie, hall, LocalDateTime.of(2019, 11, 12, 10, 30));
	Projection projection2 = new Projection(movie2, hall2, LocalDateTime.of(2019, 11, 12, 10, 30));

	Ticket ticket = new Ticket(projection, seat, "Nick");
	Ticket ticket2 = new Ticket(projection2, seat2, "John");
	
	@Test()
	public void cinemaShouldThrowReservationNotFoundException()
			throws AlreadyReservedException, ProjectionNotFoundException, InvalidSeatException,
			ExpiredProjectionException, ReservationNotFoundException {


		Map<Movie, Set<Projection>> program = new HashMap<>();
		Set<Projection> set1 = new HashSet<>();
		
		set1.add(projection);

		Set<Projection> set2 = new HashSet<>();
		set2.add(projection2);

		program.put(movie, set1);
		program.put(movie2, set2);

		ReservationSystem cinema = new ReservationSystem(program);

		cinema.bookTicket(ticket);
		cinema.bookTicket(ticket2);
		cinema.cancelTicket(ticket2);
	}

}
