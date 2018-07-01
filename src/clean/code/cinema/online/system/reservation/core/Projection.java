package clean.code.cinema.online.system.reservation.core;

import java.time.LocalDateTime;

public class Projection {

	private Movie movie;
	private Hall hall;
	private LocalDateTime date;
	private int freeSeats;
	private int maxCapacity;

	public Projection(Movie movie, Hall hall, LocalDateTime date) {
		this.setMovie(movie);
		this.setHall(hall);
		this.setDate(date);
		maxCapacity = hall.getMaxCapacity();
		this.setFreeSeats();
	}

	public Movie getMovie() {
		return this.movie;
	}

	public Hall getHall() {
		return this.hall;
	}

	public LocalDateTime getDate() {
		return this.date;
	}

	public int getMaxCapacity() {
		return this.maxCapacity;
	}

	public int getFreeSeats() {
		return this.freeSeats;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public void setFreeSeats() {
		this.freeSeats = maxCapacity;
	}

	public boolean isExpired() {

		LocalDateTime currentDate = LocalDateTime.now();

		return this.date.isBefore(currentDate);
	}

	public void releaseSeat() {
		if (freeSeats == maxCapacity) {
			throw new IllegalStateException("You have reached the maximum capacity of free seats.");
		}
		freeSeats++;
	}

	public void bookSeat() {
		if (freeSeats >= 1) {
			freeSeats--;
		} else {
			throw new IllegalStateException("There are no free seats left to be booked.");
		}
	}
}
