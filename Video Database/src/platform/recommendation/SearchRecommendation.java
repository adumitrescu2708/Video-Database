package platform.recommendation;

import database.Database;
import entertainment.Genre;
import entertainment.Video;
import filter.Filter;
import platform.queries.videoQuery.videosQueriesTypes.VideoRatingQuery;
import user.User;
import java.util.List;
/**
 * Class contains information about a Search Recommendation
 * <p> A search Recommendation retrieves the list of unseen videos from a given genre
 * sorted in ascendant order after their rating. This recommendation is available only
 * for premium users.
 * <p> We can easily reduce this action to a Video Rating Query.
 * Therefore, this type of recommendation is a Video Rating Query applied on the list
 * of the user's unseen videos from the database.
 * Search Recommendation = VideoRatingQuery - number = size(list_of_unseen_videos)
 *                                          - sort = ascendant
 *                                          - list = unseen_videos
 *                                          - filter = given filter
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public class SearchRecommendation extends VideoRatingQuery {
    /**
     * The user
     */
    protected User user;
    /**
     * The Database
     */
    protected Database instance;

    public SearchRecommendation(final User user, final Database instance, final Genre genre) {
        super(instance.getUnseenVideosByUser(user).size(), "asc",
                instance.getUnseenVideosByUser(user), new Filter(genre));
        this.user = user;
        this.instance = instance;
    }

    /**
     * <p> In this case, we do not need the restriction of not-rated videos anymore.
     * @param x Initial list
     * @return x
     */
    @Override
    public List<Video> getValidatedList(final List<Video> x) {
        return x;
    }

    /**
     * <p> This method prints all unseen videos from the required genre.
     * If there is no unseen video in the database or the user's subscription type
     * is basic, we print an error message.
     */
    @Override
    public void execute() {
        List<Video> finalList = getFinalList(list);

        if (finalList.size() == 0  || user.getSubscriptionType().equals("BASIC")) {
            message.append("SearchRecommendation cannot be applied!");
        } else {
            message.append("SearchRecommendation result: [").append(finalList.get(0).getTitle());
            finalList.remove(0);
            finalList.forEach((video) -> message.append(", ").append(video.getTitle()));
            message.append("]");
        }
    }
}
