package platform.queries.videoQuery.videosQueriesTypes;

import entertainment.Video;
import filter.Filter;
import platform.queries.videoQuery.VideoQuery;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Class contains information on Video Rating Query
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public class VideoRatingQuery extends VideoQuery {

    public VideoRatingQuery(final int number, final String sortType,
                            final List<Video> videos, final Filter filter) {
        super(number, sortType, videos, filter);
    }

    /**
     * Rating Validation
     * <p> This method selects the videos that have been rated at least once.
     * @param x Initial list
     * @return Filtered list
     */

    @Override
    public List<Video> getValidatedList(final List<Video> x) {
        List<Video> validatedList = new ArrayList<>();

        x.stream()
                .filter((video) -> video.getRating() != 0)
                .forEach(validatedList::add);

        return validatedList;
    }

    /**
     * Rating Sort
     * <p> This method sorts the given list of videos by rating and then by
     * name in ascendant order.
     * @param x Initial list
     * @return Sorted list
     */
    @Override
    public List<Video> getSortedList(final List<Video> x) {
        List<Video> sorted;

        Comparator<Video> ratingComparator = (Video v1, Video v2) ->
                Double.compare(v1.getRating(), v2.getRating());

        Comparator<Video> nameComparator = (Video v1, Video v2) ->
                v1.getTitle().compareTo(v2.getTitle());

        sorted = (List<Video>) x.stream()
                .sorted(ratingComparator.thenComparing(nameComparator))
                .collect(Collectors.toList());

        return sorted;
    }
}
