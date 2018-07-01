package clean.code.cinema.online.system.reservation.exceptions;

public class InvalidSeatException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public InvalidSeatException() {}
	
	public InvalidSeatException(String message) {
		super(message);
	}
}