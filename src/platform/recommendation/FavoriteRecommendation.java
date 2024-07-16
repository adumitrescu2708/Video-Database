package platform.recommendation;

import database.Database;
import entertainment.Video;
import filter.Filter;
import platform.queries.videoQuery.videosQueriesTypes.VideoFavoriteQuery;
import user.User;
import java.util.Comparator;
import java.util.List;

/**
 * Class contains information about a Favorite Recommendation
 * <p> A Favorite Recommendation retrieves the video with the most occurrences in
 * the favorite lists from the unseen list of a user.
 * <p> This recommendation can be applied only to premium users
 * <p> It can be easily reduced to a Favorite Video Query
 * Favorite Recommendation = VideoFavoriteQuery - number = 1
 *                                              - sort = descendant
 *                                              - list = unseen_videos
 *                                              - filter = NONE
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public class FavoriteRecommendation extends VideoFavoriteQuery {
    private Database instance;
    private User user;

    public FavoriteRecommendation(final User user, final Database instance) {
        super(1, "desc", instance.getUnseenVideosByUser(user), new Filter());
        this.instance = instance;
        this.user = user;
    }

    /**
     * <p> This method sorts videos by their number of occurrences in the lists of
     * favorite videos of all users.
     * <p> In order to obtain the database order after the sorting is applied,
     * we force equalities to swap positions.
     *
     * @param x Initial list
     * @return Sorted List
     */

    @Override
    public List<Video> getSortedList(final List<Video> x) {
        x.sort(new Comparator<Video>() {
            @Override
            public int compare(final Video o1, final Video o2) {
                if (o1.getCountFavorites() == o2.getCountFavorites()) {
                    return -1;
                } else {
                    return Double.compare(o1.getCountFavorites(), o2.getCountFavorites());
                }
            }
        });
        return x;
    }

    /**
     * <p> This method prints the unseen video appearing in the most favorite lists.
     * <p> If there is no unseen video in the database or the user's subscription type
     * is basic, we print an error message.
     * <p> If the rating query returns an empty list, we search for the first
     * unseen video in the Database. (An empty query result is produced only
     * when all the videos in the list do not appear at all in the favorites list
     * therefore, we take the first one in the database.)
     */
    @Override
    public void execute() {
        List<Video> finalList = getFinalList(list);

        if (finalList.size() == 0) {
            if (instance.getUnseenVideosByUser(user).size() == 0
                    || user.getSubscriptionType().equals("BASIC")) {
                message.append("FavoriteRecommendation cannot be applied!");
            } else {
                message.append("FavoriteRecommendation result: ")
                        .append(instance.getUnseenVideosByUser(user).get(0).getTitle());
            }
        } else {
            message.append("FavoriteRecommendation result: ").append(finalList.get(0).getTitle());
        }
    }
}
