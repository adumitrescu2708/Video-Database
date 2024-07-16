package database;

import actor.Actor;
import entertainment.Movie;
import entertainment.Series;
import fileio.Input;
import user.User;
import java.util.List;


/**
 * Class represents a Database Object Loader Layer
 * <p> Given the input, this layer is used for storing and parsing
 * the information from the input into a Database instance.
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */

public final class DatabaseLoader {

    /**
     * Default Constructor
     */
    private DatabaseLoader() { }

    /**
     * Load Input
     * <p> This method stores each movie, series, actor and
     * user from the input into a new database instance.
     *
     * @param input The input containing data
     * @return a Database instance, with actors, videos and users set from
     * the input
     */
    public static Database loadInput(final Input input) {
        Database instance = new Database();

        List<Movie> movies = instance.getMovies();
        List<Series> series = instance.getSeries();
        List<User> users = instance.getUsers();
        List<Actor> actors = instance.getActors();

        input.getMovies().forEach((movieData) -> movies.add(new Movie(movieData)));

        input.getSerials().forEach((serialData) -> series.add(new Series(serialData)));

        input.getActors().forEach((actorData) -> actors.add(new Actor(actorData, instance)));

        input.getUsers().forEach((userData) -> users.add(new User(userData, instance)));

        return instance;
    }
}
