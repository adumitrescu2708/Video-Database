package platform.queries.videoQuery.videosQueriesTypes;

import entertainment.Video;
import filter.Filter;
import platform.queries.videoQuery.VideoQuery;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Class contains information on Most Viewed Video Query
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public class VideoMostViewed extends VideoQuery {

    public VideoMostViewed(final int number, final String sortType,
                           final List<Video> videos, final Filter filter) {
        super(number, sortType, videos, filter);
    }

    /**
     * Most Viewed Validation
     * <p> This method selects the videos that have at least
     * one view.
     *
     * @param x Initial list
     * @return Validated list
     */
    @Override
    public List<Video> getValidatedList(final List<Video> x) {
        List<Video> validatedList = new ArrayList<>();

        x.stream()
                .filter((video) -> video.getCountViews() != 0)
                .forEach(validatedList::add);

        return validatedList;
    }

    /**
     * Most Viewed Sort
     * <p> This method sorts the given videos by the number of views and then
     * by the name in ascendant order.
     *
     * @param x Initial list
     * @return Sorted list
     */
    @Override
    public List<Video> getSortedList(final List<Video> x) {
        List<Video> sorted;

        Comparator<Video> viewsComparator = (Video v1, Video v2) ->
                Double.compare(v1.getCountViews(), v2.getCountViews());

        Comparator<Video> nameComparator = (Video v1, Video v2) ->
                v1.getTitle().compareTo(v2.getTitle());

        sorted = (List<Video>) x.stream()
                .sorted(viewsComparator.thenComparing(nameComparator))
                .collect(Collectors.toList());

        return sorted;
    }
}
