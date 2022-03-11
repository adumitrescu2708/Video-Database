package platform.commands;

import entertainment.Video;
import platform.Action;
import user.User;

/**
 * Class contains information about a View Command
 * <p> A view command consists in adding to the list of
 * watched videos of a user a specified video.
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public class ViewCommand extends Action {
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
     * <p> Creates a view command object with the given user and video.
     * No View command could exist without its user and video.
     *
     * @param user The user applying the command
     * @param video The video
     */
    public ViewCommand(final User user, final Video video) {
        this.user = user;
        this.video = video;
    }

    /**
     * Scenarios
     * <p> In this scenario there are 2 cases:
     *     1. The user has seen the video, in which case we update
     *     the value from the history map of the user and increment
     *     the total number of views of the video
     *     2. The user hasn't seen the video, in which case we add a
     *     new entry in the user's map of history with the given
     *     video and a value of 1 - The user has seen the video
     *     just once.
     *
     */
    @Override
    public void execute() {
        if (user.hasWatchedVideo(video)) {
            Integer countViews = user.getHistory().get(video);
            user.getHistory().put(video, countViews + 1);
            video.setCountViews(video.getCountViews() + 1);
        } else {
            user.getHistory().put(video, 1);
        }

        message.append("success -> ").append(video.getTitle())
                .append(" was viewed with total views of ").append(user.getHistory().get(video));
    }

}
