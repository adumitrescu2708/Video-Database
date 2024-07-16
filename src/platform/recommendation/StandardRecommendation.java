package platform.recommendation;

import database.Database;
import entertainment.Video;
import platform.Action;
import user.User;
import java.util.List;
/**
 * Class contains information about a Standard Recommendation
 * <p> A standard recommendation can be applied to all types of users
 * and retrieves the first unseen movie by the user from the database.
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public class StandardRecommendation extends Action {
    /**
     * The user
     */
    private User user;
    /**
     * The Database instance
     */
    private Database instance;

    /**
     * Constructor
     * <p> Created a Standard Recommendation object with given user and database
     * instance. No recommendation could exist without these fields.
     * @param user The user
     * @param instance The database
     */
    public StandardRecommendation(final User user, final Database instance) {
        this.user = user;
        this.instance = instance;
    }

    /**
     * <p> This method prints the name of the first unseen movie in the database
     * or an error message if there is no unseen movie in the database
     */
    @Override
    public void execute() {
        List<Video> unseenVideos = instance.getUnseenVideosByUser(user);
        if (unseenVideos.size() == 0) {
            message.append("StandardRecommendation cannot be applied!");
        } else {
            message.append("StandardRecommendation result: ");
            message.append(unseenVideos.get(0).getTitle());
        }
    }
}
