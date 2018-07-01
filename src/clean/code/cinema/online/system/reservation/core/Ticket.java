package clean.code.cinema.online.system.reservation.core;

public class Ticket {

	private Projection projection;
	private Seat seat;
	private String owner;
	private boolean isTaken;
	private int maxSeatNumbersInHall;

	public Ticket(Projection projection, Seat seat, String owner) {
		this.setProjection(projection);
		this.setSeat(seat);
		this.setOwner(owner);
		this.setIsTaken(false);
		maxSeatNumbersInHall = projection.getMaxCapacity();
	}

	public Projection getProjection() {
		return this.projection;
	}

	public Seat getSeat() {
		return this.seat;
	}

	public String getOwner() {
		return this.owner;
	}

	public Movie getMovie() {
		return projection.getMovie();
	}

	public boolean isExpired() {
		return projection.isExpired();
	}

	public boolean isTaken() {
		return this.isTaken;
	}

	public void setProjection(Projection projection) {
		this.projection = projection;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setIsTaken(boolean isTaken) {
		this.isTaken = isTaken;
	}

	public boolean hasInvalidSeat() {
		return seat.getSeatNumber() > maxSeatNumbersInHall || seat.getRow() > projection.getHall().getRows()
				|| seat.isInvalid();
	}

	public void book() {
		projection.bookSeat();
		seat.setIsTaken(true);
	}

	public void cancel() {
		projection.releaseSeat();
		seat.setIsTaken(false);
	}
}
