package clean.code.cinema.online.system.reservation.exceptions;

public class AlreadyReservedException extends Exception {

	private static final long serialVersionUID = 1L;

	public AlreadyReservedException() {}
	
	public AlreadyReservedException(String message) {
		super(message);
	}
}
