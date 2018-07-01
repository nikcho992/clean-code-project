package clean.code.cinema.online.system.reservation.core;

public class Seat {

	private int row;
	private int seatNumber;
	private boolean isTaken;

	public Seat(int row, int seat) {
		this.setRow(row);
		this.setSeatNumber(seat);
		this.setIsTaken(false);
	}

	public int getRow() {
		return this.row;
	}

	public int getSeatNumber() {
		return this.seatNumber;
	}

	public void setRow(int row) {
		if (row < 1) {
			throw new IllegalArgumentException("You should set a positive number.");
		}
		this.row = row;
	}

	public void setSeatNumber(int seatNumber) {
		if (seatNumber < 1) {
			throw new IllegalArgumentException("You should set a positive number.");
		}
		this.seatNumber = seatNumber;
	}

	public boolean isTaken() {
		return this.isTaken;
	}

	public void setIsTaken(boolean isTaken) {
		this.isTaken = isTaken;
	}

	public boolean isInvalid() {
		return this.row < 1 || this.seatNumber < 1;
	}
}