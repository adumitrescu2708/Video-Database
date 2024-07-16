package entertainment;

import user.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Class contains information about a Video
 * <p> Our platform stores information on different types of
 * videos, such as movies and serials. Therefore, this abstract class
 * describes the common necessities for both and for later implemented
 * types of videos.
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public abstract class Video {
    /**
     * Release Year
     */
    protected int year;
    /**
     * Title
     */
    protected String title;
    /**
     * Number of occurrences in the Favorites list of all users in the Database
     */
    protected int countFavorites;
    /**
     * Number of Views
     */
    protected int countViews;
    /**
     * List of all Genres
     */
    protected ArrayList<Genre> genres = new ArrayList<>();
    /**
     * Names of the cast members
     */
    protected ArrayList<String> cast;
    /**
     * The average rating
     */
    protected Double rating;


    /**
     * Constructor
     * <p> Creates a Video with the specified title, release year and cast
     * members. Once a Video is added in the Database, the counts of views
     * and occurrences in the favorites lists of users are both set to null.
     *
     * @param title Title of the Video
     * @param year Release Year
     * @param cast Name of the members of the cast
     */
    public Video(final String title, final int year, final ArrayList<String> cast) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.countFavorites = 0;
        this.countViews = 0;
    }

    /**
     * Review Checker
     * <p> Our platform allows users to review a video just once.
     * Therefore, this method checks if the specified user is already
     * in the reviewers of the video.
     *
     * @param user User
     * @param seasonIdx Optional, Used for Serial Video types
     * @return true, if the user has already reviewed the video
     * and false, otherwise
     */
    public abstract boolean isReviewedBy(User user, int seasonIdx);


    /**
     * Genre Checker
     * <p> This method is used for checking if the video describes
     * the specified genre. This method is used for filtering
     * videos in the Database.
     *
     * @param genre The genre
     * @return true, if the video has the specified genre and
     * false, otherwise
     */
    public boolean hasGenre(final Genre genre) {
        for (Genre videoGenre : genres) {
            if (videoGenre.equals(genre)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Genre List Checker
     * <p> Given a list of Genres, this method checks if the video
     * has at least one of the genres specified in the given list. This
     * method can be used for filtering videos in the Database.
     *
     * @param genresList The list of Genres
     * @return true, if the video has at least one genre from the list
     * and false, otherwise
     */
    public boolean hasGenreFromList(final List<Genre> genresList) {
        for (Genre genre : genresList) {
            if (hasGenre(genre)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Year List Checker
     * <p> This method checks if the video's release year is in a given
     * list of years. This method is used for filtering videos.
     *
     * @param yearList List of years
     * @return true, if the video is released in one of the specified
     * years and false, otherwise.
     */
    public boolean hasYearFromList(final List<Integer> yearList) {
        for (Integer yearReff : yearList) {
            if (yearReff == year) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reviewer Setter
     * <p> As mentioned above, our platform allows users to review a video a single time.
     * Therefore, each child-class of the stated class will present a List of Users
     * in order to keep track of the reviewers.
     * <p> This method will be implemented by each child-class, deciding for itself
     * how to handle the collision problem of the users.
     *
     * @param user User that sets a review
     * @param grade The review Grade
     * @param seasonIdx Optional, used for Serial Video type
     */
    public abstract void setReviewer(User user, Double grade, int seasonIdx);

    /**
     * Duration Getter
     * @return the duration of the Video
     */
    public abstract int getDuration();

    /**
     * Genres Getter
     * @return the list of genres of a video
     */
    public ArrayList<Genre> getGenres() {
        return genres;
    }

    /**
     * Title Getter
     * @return the title of the Video
     */
    public String getTitle() {
        return title;
    }

    /**
     * Year Getter
     * @return the release year of the Video
     */
    public int getYear() {
        return year;
    }

    /**
     * Cast Getter
     * @return the list of the cast members
     */
    public ArrayList<String> getCast() {
        return cast;
    }

    /**
     * Rating Grade Getter
     * @return the rating of the Video
     */
    public Double getRating() {
        return 0d;
    }

    /**
     * Count Favorites Getter
     * @return the occurrences in the favorites list of
     * all users in the Database
     */
    public int getCountFavorites() {
        return countFavorites;
    }

    /**
     * Views Getter
     * @return the number of views of the Video
     */
    public int getCountViews() {
        return countViews;
    }

    /**
     * Count Favorites Setter
     * @param countFavorites The modified counter
     */
    public void setCountFavorites(final int countFavorites) {
        this.countFavorites = countFavorites;
    }

    /**
     * Views Setter
     * @param countViews The modified counter of views
     */
    public void setCountViews(final int countViews) {
        this.countViews = countViews;
    }

    /**
     * Rating Grade Setter
     * @param rating The modified rating
     */
    public void setRating(final Double rating) {
        this.rating = rating;
    }
}
