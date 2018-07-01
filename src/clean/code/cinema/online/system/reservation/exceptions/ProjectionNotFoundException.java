package clean.code.cinema.online.system.reservation.exceptions;

public class ProjectionNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public ProjectionNotFoundException() {}
	
	public ProjectionNotFoundException(String message) {
		super(message);
	}
}