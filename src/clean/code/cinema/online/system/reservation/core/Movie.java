package clean.code.cinema.online.system.reservation.core;

public class Movie implements Comparable<Movie> {

	private String name;
	private int duration;
	private MovieGenre genre;

	public Movie(String name, int duration, MovieGenre genre) {
		this.setName(name);
		this.setDuration(duration);
		this.setGenre(genre);
	}

	public String getName() {
		return this.name;
	}

	public int getDuration() {
		return this.duration;
	}

	public MovieGenre getMovieGenre() {
		return this.genre;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setGenre(MovieGenre genre) {
		this.genre = genre;
	}

	@Override
	public int compareTo(Movie other) {

		return this.getMovieGenre().toString().compareTo(other.getMovieGenre().toString());

	}
}