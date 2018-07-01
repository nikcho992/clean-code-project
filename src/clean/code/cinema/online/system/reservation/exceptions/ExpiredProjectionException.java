package clean.code.cinema.online.system.reservation.exceptions;

public class ExpiredProjectionException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public ExpiredProjectionException() {}
	
	public ExpiredProjectionException(String message) {
		super(message);
	}
}
