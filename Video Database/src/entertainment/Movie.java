package entertainment;

import fileio.MovieInputData;
import user.User;
import utils.Utils;
import java.util.ArrayList;
import java.util.List;

/**
 * Class contains information about a Movie
 * <p> A Movie is considered to be a Video type with additional information
 * such as duration. Movie class implements the required methods described
 * in the Video parent-class with the specific behaviour.
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public class Movie extends Video {
    /**
     * Duration of a Movie
     */
    private int duration;
    /**
     * List of all given rating marks
     */
    private List<Double> ratings;
    /**
     * List of all reviewers
     */
    private ArrayList<User> reviewers = new ArrayList<>();

    /**
     * Constructor
     * <p> Creates a Movie based on the Movie Input Data, parsed from the
     * JSON files.
     * <p> From the Input Data, the movie stores the given title, year, cast
     * and duration. As about the parsed genres, we use Utils static method
     * for parsing a given string into a Genre type.
     *
     * @param input Movie Input Data from JSON file
     */
    public Movie(final MovieInputData input) {
        super(input.getTitle(), input.getYear(), input.getCast());
        this.duration = input.getDuration();
        input.getGenres()
                .forEach((genre) -> genres.add(Utils.stringToGenre(genre)));

        this.ratings = new ArrayList<>();
    }

    /**
     * Reviewer Setter
     * <p> The user is stored in the List of reviewers. Once a reviewer is added,
     * the given Grade is also added in the ratings list.
     *
     * @param user User that sets a review
     * @param grade The review Grade
     * @param seasonIdx Optional, used for Serial Video type
     */
    public void setReviewer(final User user, final Double grade, final int seasonIdx) {
        reviewers.add(user);
        ratings.add(grade);
    }

    /**
     * Review Checker
     * <p> In order to treat the platform reviewers problem, a Movie stores
     * each reviewer into a List. Once a user wants to execute a review command
     * on a video, this method checks if the specified user is in the
     * video's reviewers. If so, returns true, not allowing the user to
     * execute another review on an already reviewed movie.
     *
     * @param user User
     * @param seasonIdx Optional, Used for Serial Video types
     * @return true, if the user already reviewed the movie and
     * false, otherwise
     */
    public boolean isReviewedBy(final User user, final int seasonIdx) {
        for (User reviewer : reviewers) {
            if (reviewer.equals(user)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Duration Getter
     * @return The duration of the video, given from the
     * JSON input files and parsed in this class
     */
    @Override
    public int getDuration() {
        return duration;
    }

    /**
     * Rating Average Calculator
     * <p> From the List of ratings, we compute the average of all
     * of them and return the final rating. If a Movie isn't reviewed
     * we return 0 value.
     *
     * @return the rating of the Movie
     */
    public Double getRating() {
        double average = ratings.stream().mapToDouble(rating -> rating).sum();
        if (ratings.size() != 0) {
            return average / ratings.size();
        }

        return 0d;
    }

    /**
     * Ratings Getter
     * @return the List of all given ratings
     */
    public List<Double> getRatings() {
        return ratings;
    }
}
