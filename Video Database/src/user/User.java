package user;

import database.Database;
import entertainment.Video;
import fileio.UserInputData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class contains information about a User
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public class User {
    /**
     * Username
     */
    private String username;
    /**
     * Subscription Type - could be Standard or Premium
     */
    private String subscriptionType;
    /**
     * Map of History - stores a video and a total count of views
     */
    private HashMap<Video, Integer> history;
    /**
     * List of Favorite Videos
     */
    private List<Video> favoriteVideos;
    /**
     * Activity Score
     */
    private int activityCounter;

    /**
     * Constructor
     * <p> Creates a User based on the Actor Input data parsed from
     * the JSON file.
     * <p> From the input data, the user stores the username and
     * subscription type.
     * <p> Once a user is added in the Database, its activity score
     * is set to 0.
     * <p> For the list of Favorite Videos, we first filter the input
     * List, verifying the existence of the given video in the
     * Database and then store the video in the List.
     * <p> We store de map of history given in the input
     * <p> Once a user has been given a map of history and a list of favorite
     * videos, we need to update each video the total number of views and
     * of occurrences in the favorite lists.
     *
     * @param input Actor Input data from JSON
     * @param instance Instance of the Database
     */
    public User(final UserInputData input, final Database instance) {
        this.username = input.getUsername();
        this.subscriptionType = input.getSubscriptionType();
        this.activityCounter = 0;

        List<Video> inputFavoriteVideos = new ArrayList<>();
        input.getFavoriteMovies().stream()
                .filter((name) -> instance.getVideoByName(name) != null)
                .forEach((name) -> inputFavoriteVideos.add(instance.getVideoByName(name)));
        this.favoriteVideos = inputFavoriteVideos;


        HashMap<Video, Integer> historyVideos = new HashMap<>();
        for (Map.Entry<String, Integer> entry : input.getHistory().entrySet()) {
            if (instance.getVideoByName(entry.getKey()) != null) {
                historyVideos.put(instance.getVideoByName(entry.getKey()), entry.getValue());
            }
        }
        this.history = historyVideos;

        for (Video video : this.favoriteVideos) {
            video.setCountFavorites(video.getCountFavorites() + 1);
        }

        for (Map.Entry<Video, Integer> entry : this.history.entrySet()) {
            entry.getKey().setCountViews(entry.getKey().getCountViews() + entry.getValue());
        }

    }

    /**
     * Favorite Video Checker
     * <p> This method checks if the user has in the favorite list
     * the given video.
     * <p> Our platform doesn't allow adding the same video multiple
     * times in the favorite list.
     *
     * @param video The video
     * @return true, if the user has the video in the favorite list
     * and false, otherwise
     */
    public boolean hasFavoriteVideo(final Video video) {
        for (Video favoriteVideo : favoriteVideos) {
            if (favoriteVideo.equals(video)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Watched Video Checker
     * <p> This method checks if the user has seen a specified video.
     * @param video The video
     * @return true, if the user has already watched the video and
     * false, otherwise
     */
    public boolean hasWatchedVideo(final Video video) {
        for (Map.Entry<Video, Integer> entry : history.entrySet()) {
            if (entry.getKey().getTitle().equals(video.getTitle())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Username Getter
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * SubcriptionType Getter
     * @return the subscription type of the user
     */
    public String getSubscriptionType() {
        return subscriptionType;
    }

    /**
     * History Getter
     * @return the map of watched videos
     */
    public HashMap<Video, Integer> getHistory() {
        return history;
    }

    /**
     * Favorite Videos Getter
     * @return the List of favorite videos
     */
    public List<Video> getFavoriteVideos() {
        return favoriteVideos;
    }

    /**
     * Activity Score Getter
     * @return the activity score
     */
    public int getActivityCounter() {
        return activityCounter;
    }

    /**
     * Activity Score Setter
     * @param activityCounter The score that needs to be set
     */
    public void setActivityCounter(final int activityCounter) {
        this.activityCounter = activityCounter;
    }
}
