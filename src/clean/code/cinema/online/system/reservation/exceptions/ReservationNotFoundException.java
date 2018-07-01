package clean.code.cinema.online.system.reservation.exceptions;

public class ReservationNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public ReservationNotFoundException() {}
	
	public ReservationNotFoundException(String message) {
		super(message);
	}
}