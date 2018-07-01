package clean.code.cinema.online.system.reservation;

import org.junit.Test;

import clean.code.cinema.online.system.reservation.exceptions.*;
import clean.code.cinema.online.system.reservation.core.*;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReservationTest {
	
	Movie movie = new Movie("The best offer", 120, MovieGenre.THRILLER);
	Movie movie2 = new Movie("Lame", 100, MovieGenre.COMEDY);
	Movie dramaMovie = new Movie("No drama", 90, MovieGenre.DRAMA);

	Hall hall = new Hall(1, 10, 10);
	Hall hall2 = new Hall(2, 10, 10);

	Seat seat = new Seat(5, 5);
	Seat seat2 = new Seat(6, 6);
	Seat invalidSeat = new Seat(400,400);

	Projection projection = new Projection(movie, hall, LocalDateTime.of(2019, 11, 12, 10, 30));
	Projection projection2 = new Projection(movie2, hall2, LocalDateTime.of(2019, 11, 12, 10, 35));
	Projection projection3 = new Projection(movie, hall, LocalDateTime.of(2019, 11, 12, 12, 30));
	Projection expiredProjection = new Projection(movie2, hall2,LocalDateTime.of(2016, 11, 12, 12, 30));

	Ticket ticket = new Ticket(projection, seat, "Nick");
	Ticket ticket2 = new Ticket(projection2, seat2, "John");
	Ticket ticket3 = new Ticket(projection, seat2, "Peter");
	Ticket expiredTicket = new Ticket(expiredProjection,seat,"Jo");
	Ticket ticketWithInvalidSeat = new Ticket(projection, invalidSeat, "Rom");
	Ticket sameAsTicket3 = new Ticket(projection, seat2, "Peter");
	
	Map<Movie, Set<Projection>> program = new HashMap<>();
	Set<Projection> set1 = new HashSet<>();
	Set<Projection> set2 = new HashSet<>();
	Reservation reservationSystem = new Reservation(program);
	
	@Test()
	public void testShouldGetNumberOfBookedTickets()
			throws AlreadyReservedException, ProjectionNotFoundException, InvalidSeatException,
			ExpiredProjectionException, ReservationNotFoundException {
		
		set1.add(projection);
		set2.add(projection2);

		program.put(movie, set1);
		program.put(movie2, set2);

		reservationSystem.bookTicket(ticket);
		reservationSystem.bookTicket(ticket2);
		
		assertEquals((Integer) 2, (Integer) reservationSystem.getTickets().size());
	}
	
	@Test() 
	public void testShouldCheckIfFreeSeatsInProjectionDecreaseWhenTicketIsBooked() throws AlreadyReservedException, ProjectionNotFoundException, InvalidSeatException, ExpiredProjectionException {
		set1.add(projection);
		program.put(movie, set1);
		reservationSystem.bookTicket(ticket);
		
		int expectedFreeSeats = projection.getMaxCapacity()-1;
		int actualFreeSeats = projection.getFreeSeats();
		
		assertEquals((Integer) expectedFreeSeats, (Integer) actualFreeSeats);
	}
	
	@Test() 
	public void testShouldCheckNumberOfBookedTicketsIfATicketIsCancelled()
			throws AlreadyReservedException, ProjectionNotFoundException, InvalidSeatException,
			ExpiredProjectionException, ReservationNotFoundException {
		
		set1.add(projection);
		program.put(movie, set1);
		reservationSystem.bookTicket(ticket);
		reservationSystem.bookTicket(ticket3);
		reservationSystem.cancelTicket(ticket3);
		
		int expectedFreeSeats = projection.getMaxCapacity()-1;
		int actualFreeSeats = projection.getFreeSeats();
		
		assertEquals((Integer) expectedFreeSeats, (Integer) actualFreeSeats);
	}
	

	@Test(expected = ProjectionNotFoundException.class)
	public void testShouldThrowProjectionNotFoundExceptionWhileBookingTicket() throws AlreadyReservedException, ProjectionNotFoundException, InvalidSeatException, ExpiredProjectionException {
		
		set1.add(projection2);
		program.put(movie, set1);
		reservationSystem.bookTicket(ticket);
	}
	
	@Test(expected = ExpiredProjectionException.class)
	public void testShouldThrowExpiredProjectionExceptionWhileBookingTicket() throws AlreadyReservedException, ProjectionNotFoundException, InvalidSeatException, ExpiredProjectionException {
		set1.add(projection2);
		set1.add(expiredProjection);
		program.put(movie2, set1);
		reservationSystem.bookTicket(expiredTicket);
	}
	
	@Test(expected = InvalidSeatException.class) 
	public void testShouldThrowInvalidSeatExceptionWhileBookingTicket() throws AlreadyReservedException, ProjectionNotFoundException, InvalidSeatException, ExpiredProjectionException {
		set1.add(projection);
		program.put(movie, set1);
		reservationSystem.bookTicket(ticketWithInvalidSeat);
	}
	
	@Test(expected = AlreadyReservedException.class)
	public void testShouldThrowAlreadyReservedTicketExceptionWhileBookingTicket() throws AlreadyReservedException, ProjectionNotFoundException, InvalidSeatException, ExpiredProjectionException {
		set1.add(projection);
		program.put(movie, set1);
		reservationSystem.bookTicket(ticket3);
		reservationSystem.bookTicket(sameAsTicket3);
	}
	
	@Test(expected = ProjectionNotFoundException.class)
	public void testShouldThrowProjectionNotFoundExceptionWhileCancellingTicket() throws AlreadyReservedException, ProjectionNotFoundException, InvalidSeatException, ExpiredProjectionException, ReservationNotFoundException {
		set1.add(projection);
		program.put(movie, set1);
		reservationSystem.bookTicket(ticket3);
		
		Set<Projection> randomSet = new HashSet<>();
		program.put(movie, randomSet);
		reservationSystem.cancelTicket(ticket3);
	}
	
	@Test(expected = ReservationNotFoundException.class)
	public void testShouldThrowReservationNotFoundExceptionWhileCancellingTicket() throws AlreadyReservedException, ProjectionNotFoundException, InvalidSeatException, ExpiredProjectionException, ReservationNotFoundException {
		set1.add(projection2);
		program.put(movie2, set1);
		reservationSystem.bookTicket(ticket2);
		
		Ticket randomTicket = new Ticket(projection2,seat,"Pat");
		reservationSystem.cancelTicket(randomTicket);
	}
	
	@Test()
	public void testShouldGetProjectionFreeSeats() throws AlreadyReservedException, ProjectionNotFoundException, InvalidSeatException, ExpiredProjectionException {
		
		int projection2TotalFreeSeats = projection2.getMaxCapacity();
		
		set1.add(projection2);
		program.put(movie2, set1);
		reservationSystem.bookTicket(ticket2);
		
		int actualFreeSeats = reservationSystem.getFreeSeats(projection2);
		
		assertEquals(projection2TotalFreeSeats-1, actualFreeSeats);
	}
	
	@Test(expected = ProjectionNotFoundException.class)
	public void testShouldThrowProjectionNotFoundExceptionWhileGettingFreeSeats() throws AlreadyReservedException, ProjectionNotFoundException, InvalidSeatException, ExpiredProjectionException {
		set1.add(projection2);
		program.put(movie2, set1);
		reservationSystem.bookTicket(ticket2);
		
		reservationSystem.getFreeSeats(expiredProjection);
	}
	
	@Test
	public void testShouldSortMoviesByGenre() {
		set1.add(projection);
		set2.add(projection2);
		program.put(movie, set1);
		program.put(movie2, set2);
		
		Set<Projection> dramaSet = new HashSet<>();
		dramaSet.add(projection3);
		program.put(dramaMovie, dramaSet);
		
		List<Movie> sortedMovies = (List<Movie>)reservationSystem.getSortedMoviesByGenre();
		
		assertEquals(movie2, sortedMovies.get(0));
		assertEquals(dramaMovie, sortedMovies.get(1));
		assertEquals(movie, sortedMovies.get(2));
	}
	
	@Test()
	public void testShouldCheckWhenMovieIsShown() {
		set1.add(projection);
		program.put(movie, set1);
		
		Set<LocalDateTime> timeSet = new HashSet<>();
		timeSet.add(projection.getDate());
		
		String showTime = timeSet.toString();
		String expectedShowTime = reservationSystem.checkWhenMovieIsShown(movie);
		
		assertEquals(expectedShowTime, showTime);
	}
}
