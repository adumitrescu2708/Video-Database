package database;

import actor.Actor;
import entertainment.Genre;
import entertainment.Movie;
import entertainment.Series;
import entertainment.Video;
import user.User;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class contains information about a Database
 * <p> Our platform stores information on videos, actors and users
 * using a Database. This is a placeholder for all entities given
 * in the input.
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public class Database {
    /**
     * List of Movies
     */
    private List<Movie> movies = new ArrayList<>();
    /**
     * List of Series
     */
    private List<Series> series = new ArrayList<>();
    /**
     * List of Actors
     */
    private List<Actor> actors = new ArrayList<>();
    /**
     * List of Users
     */
    private List<User> users = new ArrayList<>();

    /**
     * Default Constructor
     * <p> We use an Object Loader Layer for storing
     * the database from the JSON files. Therefore, loading
     * is made in DatabaseLoader class.
     */
    public Database() { }

    /**
     * Video Search by Name
     * <p> This method returns the video having the given name
     * and null if there is no such named video in the database.
     * <p> We first search the name in the Movie list and then
     * in the Series list.
     *
     * @param name the name of the searched video
     * @return the video, or null if the specified name
     * is not in the database
     */
    public Video getVideoByName(final String name) {
        for (Movie movie:movies) {
            if (movie.getTitle().equals(name)) {
                return movie;
            }
        }
        for (Series s : this.series) {
            if (s.getTitle().equals(name)) {
                return s;
            }
        }
        return null;
    }

    /**
     * User Search by Name
     * <p> Given a name, this method returns the user in the database
     * having the specified name, or null in case it doesn't exist.
     *
     * @param name The name of the searched User
     * @return the user, or null if the specified name is not in the
     * database
     */
    public User getUserByName(final String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Unseen Videos
     * <p> Given a user, this method returns the list of videos
     * from the database that the user hasn't yet watched. This method
     * is used for recommendation actions.
     *
     * @param user The user
     * @return The list of unseen videos by user
     */
    public List<Video> getUnseenVideosByUser(final User user) {
        List<Video> unseenVideos = new ArrayList<>();

        movies.stream()
                .filter((movie) -> !user.hasWatchedVideo(movie))
                .forEach(unseenVideos::add);

        series.stream()
                .filter((s) -> !user.hasWatchedVideo(s))
                .forEach(unseenVideos::add);

        return unseenVideos;
    }

    /**
     * Sorted Genres
     * <p> This method is used for computing the sorted Genres
     * We sort genres by the total number of views of the movies with the
     * specified genre.
     * <p> We first use a Map that stores for each Genre the total number of views.
     * We iterate through the List of Movies and Series from the Database and
     * update the value from the corresponding genre in the hashmap.
     * <p> We then store each entry in a List and sort it in descendant order
     * comparing the number of views.
     * <p> In the end we put in the final list the genres and return it.
     *
     * @return the list of sorted Genres in descendant order, sorted by the number
     * of views
     */
    public List<Genre> getSortedGenres() {
        HashMap<Genre, Integer> genresUtil = new HashMap<>();
        ArrayList<Genre> genres = new ArrayList<>();

        for (Movie movie : movies) {
            for (Genre genre : movie.getGenres()) {
                genresUtil.merge(genre, movie.getCountViews(), Integer::sum);
            }
        }
        for (Series serial : this.series) {
            for (Genre genre : serial.getGenres()) {
                genresUtil.merge(genre, serial.getCountViews(), Integer::sum);
            }
        }
        List<Map.Entry<Genre, Integer>> aux = new ArrayList<>(genresUtil.entrySet());
        Comparator<Map.Entry<Genre, Integer>> viewsComp = (Map.Entry<Genre, Integer> e1,
                                                           Map.Entry<Genre, Integer> e2) ->
                                                    Integer.compare(e2.getValue(), e1.getValue());

        aux = (List<Map.Entry<Genre, Integer>>) aux.stream()
                .sorted(viewsComp).collect(Collectors.toList());

        aux.forEach((entry) -> genres.add((entry.getKey())));
        return genres;
    }

    /**
     * Users Getter
     * @return the list of users from the database
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Users Setter
     * @param users the list of users
     */
    public void setUsers(final List<User> users) {
        this.users = users;
    }

    /**
     * Movies Getter
     * @return the list of Movies from the Database
     */
    public List<Movie> getMovies() {
        return movies;
    }

    /**
     * Movies Setter
     * @param movies the list of Movies
     */
    public void setMovies(final List<Movie> movies) {
        this.movies = movies;
    }

    /**
     * Series Getter
     * @return the list of Series from the Database
     */
    public List<Series> getSeries() {
        return series;
    }

    /**
     * Series setter
     * @param series the list of series
     */
    public void setSeries(final List<Series> series) {
        this.series = series;
    }

    /**
     * Actors Getter
     * @return the list of Actors from the Database
     */
    public List<Actor> getActors() {
        return actors;
    }

    /**
     * Actors Setter
     * @param actors the list of actors
     */
    public void setActors(final List<Actor> actors) {
        this.actors = actors;
    }
}
