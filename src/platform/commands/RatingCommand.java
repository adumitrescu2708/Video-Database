package platform.commands;

import entertainment.Video;
import platform.Action;
import user.User;

/**
 * Class contains information about a Rating Command
 * <p> A Rating command consists in providing a rating grade
 * to a specific video.
 * <p> Users can only rate a movie or series season once.
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public class RatingCommand extends Action {
    /**
     * The Rating Grade
     */
    private Double grade;
    /**
     * The season, optional parameter, used for series
     */
    private int season;
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
     * <p> Creates a Rating Command with the given user,
     * grade, video and season.
     *
     * @param user - The user applying the command
     * @param video - The reviewed Video
     * @param grade - The rating grade
     * @param season - The season, Optional used for series
     */
    public RatingCommand(final User user, final Video video,
                         final Double grade, final int season) {
        this.user = user;
        this.video = video;
        this.grade = grade;
        this.season = season;
    }

    /**
     * Scenarios
     * <p> There are 3 cases for this scenario:
     *      1. The user has not seen the video yet. In this case,
     *      our platform doesn't allow rating an unseen video.
     *      2. The user has already rated the video. In this case,
     *      our platform gives an error message.
     *      3. The action is correct, and the platform executes
     *      the command as mentioned bellow.
     *
     * <p> Each video provides a list of ratings, used for computing
     * the average rating of the video and for later queries
     * and recommendations.
     * <p> Each user has an activity counter, used for providing
     * information on the most active users from our Database.
     * <p> Therefore, when a video is rated, the given grade is added to the
     * video's list of ratings and the activity score of the user increments.
     */
    @Override
    public void execute() {
        if (!user.hasWatchedVideo(video)) {
            message.append("error -> ").append(video.getTitle())
                    .append(" is not seen");
        } else if (video.isReviewedBy(user, season)) {
            message.append("error -> ").append(video.getTitle())
                    .append(" has been already rated");
        } else {
            video.setReviewer(user, grade, season);
            user.setActivityCounter(user.getActivityCounter() + 1);
            message.append("success -> ").append(video.getTitle())
                    .append(" was rated with ").append(grade)
                    .append(" by ").append(user.getUsername());
        }
    }

}
