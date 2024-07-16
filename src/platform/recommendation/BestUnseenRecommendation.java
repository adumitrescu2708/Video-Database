package platform.recommendation;

import database.Database;
import entertainment.Video;
import filter.Filter;
import platform.queries.videoQuery.videosQueriesTypes.VideoRatingQuery;
import user.User;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class contains information about a Best-Unseen-Video Recommendation
 * <p> This recommendation provides the best rated unseen video by user.
 * It is available to both kinds of users.
 * <p> This type of recommendation can be reduced to a video rating query
 * applied on the list of unseen videos of the user, taking descendant order, and
 * taking the first element.
 * Best-Unseen Recommendation = VideoRatingQuery - number = 1
 *                                               - sort = descendant
 *                                               - list = unseen_videos
 *                                               - filter = NONE
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public class BestUnseenRecommendation extends VideoRatingQuery {
    /**
     * The User
     */
    private User user;
    /**
     * The Database instance
     */
    private Database instance;

    public BestUnseenRecommendation(final User user, final Database instance) {
        super(1, "desc", instance.getUnseenVideosByUser(user), new Filter());
        this.user = user;
        this.instance = instance;
    }

    /**
     * <p> In this Scenario, The first comparator is the rating score and
     * the second one is the order in the Database.
     * @param x Initial list
     * @return Sorted List
     */
    @Override
    public List<Video> getSortedList(final List<Video> x) {
        final List<Video> sorted;

        Comparator<Video> ratingComp = (Video v1, Video v2) ->
                Double.compare(v1.getRating(), v2.getRating());

        sorted = (List<Video>) x.stream()
                .sorted(ratingComp)
                .collect(Collectors.toList());

        return sorted;
    }

    /**
     * <p> This method prints the best unseen video.
     * <p> If it there is no unseen video this method gives an error message.
     * <p> If the rating query returns an empty list, we search for the first
     * unseen video in the Database. (An empty query result is produced only
     * when all the videos in the list are not rated at all, therefore, we take
     * the first one in the database.)
     *
     */
    @Override
    public void execute() {
        List<Video> finalList = getFinalList(list);
        if (finalList.size() == 0) {
            if (instance.getUnseenVideosByUser(user).size() == 0) {
                message.append("BestRatedUnseenRecommendation cannot be applied!");
            } else {
                message.append("BestRatedUnseenRecommendation result: ");
                message.append(instance.getUnseenVideosByUser(user).get(0).getTitle());
            }
        } else {
            message.append("BestRatedUnseenRecommendation result: ");
            message.append(finalList.get(0).getTitle());
        }
    }

}
