package platform.commands;

import entertainment.Video;
import platform.Action;
import user.User;

/**
 * Class contains information about a Favorite Command
 * <p> A favorite command consists in adding
 * to the favorite videos list of a user a specified video.
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public class FavoriteCommand extends Action {
    /**
     * The User applying the command
     */
    private User user;
    /**
     * The Video
     */
    private Video video;

    /**
     * Constructor
     * <p> Creates a Favorite Command - object with
     * the given user and video. No Favorite command could exist
     * without a user and a video.
     *
     * @param user The user applying the command
     * @param video The video it wants to add to Favorite List
     */
    public FavoriteCommand(final User user, final Video video) {
        this.user = user;
        this.video = video;
    }

    /**
     * Scenarios
     * <p> There are 3 cases for this scenario:
     *      1. The user has not seen the video yet. In this case,
     *      our platform doesn't allow adding an unseen video to
     *      the favorites.
     *      2. The user already has the video in the favorite list.
     *      In this case, our platform gives an informative message.
     *      3. The action is correct and the platform executes the
     *      action, as described below.
     *
     * <p> When a video is added in the Favorites list of a
     * user, the video's counter of occurrences in the favorites list
     * increments.
     *
     */
    @Override
    public void execute() {
        if (!user.hasWatchedVideo(video)) {
            message.append("error -> ").append(video.getTitle())
                    .append(" is not seen");
        } else if (user.hasFavoriteVideo(video)) {
            message.append("error -> ").append(video.getTitle())
                    .append(" is already in favourite list");
        } else {
            user.getFavoriteVideos().add(video);
            video.setCountFavorites(video.getCountFavorites() + 1);
            message.append("success -> ").append(video.getTitle())
                    .append(" was added as favourite");
        }
    }
}
