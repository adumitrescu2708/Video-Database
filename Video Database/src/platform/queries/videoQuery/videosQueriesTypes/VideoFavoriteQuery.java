package platform.queries.videoQuery.videosQueriesTypes;

import entertainment.Video;
import filter.Filter;
import platform.queries.videoQuery.VideoQuery;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class contains information on Favorite Video Query
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public class VideoFavoriteQuery extends VideoQuery {

    public VideoFavoriteQuery(final int number, final String sortType,
                              final List<Video> videos, final Filter filter) {
        super(number, sortType, videos, filter);
    }

    /**
     * Favorite Validation
     * <p> This method selects the videos that have appeared at least once in
     * the favorite lists of all users.
     *
     * @param x Initial list
     * @return
     */
    @Override
    public List<Video> getValidatedList(final List<Video> x) {
        List<Video> validatedList = new ArrayList<>();

        x.stream()
                .filter((video) -> video.getCountFavorites() != 0)
                .forEach(validatedList::add);

        return validatedList;
    }

    /**
     * Favorite Sort
     * <p> This method sorts the given list of videos by the number of occurrences
     * in the list of favorites of all users and then by the name in ascendant order.
     *
     * @param x Initial list
     * @return Sorted list
     */
    @Override
    public List<Video> getSortedList(final List<Video> x) {
        List<Video> sorted;

        Comparator<Video> favoriteComparator = (Video v1, Video v2) ->
                Double.compare(v1.getCountFavorites(), v2.getCountFavorites());

        Comparator<Video> nameComparator = (Video v1, Video v2) ->
                v1.getTitle().compareTo(v2.getTitle());

        sorted = (List<Video>) x.stream()
                .sorted(favoriteComparator.thenComparing(nameComparator))
                .collect(Collectors.toList());
        return sorted;
    }
}
