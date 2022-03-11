package platform.recommendation;

import database.Database;
import entertainment.Genre;
import entertainment.Video;
import filter.Filter;
import user.User;
import java.util.List;
/**
 * Class contains information about a Popular Recommendation
 * <p> A Popular Recommendation retrieves the first unseen video of the most popular
 * genre. This recommendation is available only for premium users.
 * <p> We can reduce this action to a search recommendation applied to
 * the most popular genre, from which we choose the first video.
 * <p> In order to choose the correct genre, we implemented a method in the Database
 * class that sorts the Genres.
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public class PopularRecommendation extends SearchRecommendation {

    public PopularRecommendation(final User user, final Database instance) {
        super(user, instance, null);
        setFilter(new Filter(getGenre()));
    }

    /**
     * Choosing the Correct Genre
     * <p> Given the list of unseen videos and the list of genres sorted in descendant
     * order by their popularity, this method chooses the first available genre
     * that the user hasn't fully watched.
     *
     * <p> For example,
     * @return the most popular genre
     */
    private Genre getGenre() {
        List<Genre> sortedGenres = instance.getSortedGenres();
        List<Video> unseenVideos = instance.getUnseenVideosByUser(user);

        for (Genre genre : sortedGenres) {
            for (Video video : unseenVideos) {
                if (video.hasGenre(genre)) {
                    return genre;
                }
            }
        }
        return null;
    }

    /**
     * In this case, we do not need to sort the list.
     * @param x Initial list
     * @return x
     */
    @Override
    public List<Video> getSortedList(final List<Video> x) {
        return x;
    }

    /**
     * <p> This method prints the name of the first unseen video having the
     * most popular genre.
     * <p> This method cannot be applied to standard users. If such a user
     * wants to access this recommendation, an error message is printed;
     *
     */
    @Override
    public void execute() {
        List<Video> finalList = getFinalList(list);
        if (finalList.size() == 0 || user.getSubscriptionType().equals("BASIC")) {
            message.append("PopularRecommendation cannot be applied!");
        } else {
            message.append("PopularRecommendation result: ");
            message.append(finalList.get(0).getTitle());
        }
    }
}
