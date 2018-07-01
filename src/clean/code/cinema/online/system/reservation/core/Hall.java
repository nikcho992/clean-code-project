package clean.code.cinema.online.system.reservation.core;

public class Hall {

	private int number;
	private int rows;
	private int rowSeats;

	public Hall(int number, int rows, int rowSeats) {
		this.setNumber(number);
		this.setRows(rows);
		this.setRowSeats(rowSeats);
	}

	public int getNumber() {
		return this.number;
	}

	public int getRows() {
		return this.rows;
	}

	public int getRowSeats() {
		return this.rowSeats;
	}

	public int getMaxCapacity() {
		return this.rows * this.rowSeats;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setRows(int rows) {
		if (rows < 0) {
			throw new IllegalArgumentException("Invalid argument.");
		}

		this.rows = rows;
	}

	public void setRowSeats(int rowSeats) {
		if (rowSeats < 0) {
			throw new IllegalArgumentException("Invalid argument.");
		}

		this.rowSeats = rowSeats;
	}
}
