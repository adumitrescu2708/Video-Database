package platform.queries.videoQuery.videosQueriesTypes;


import entertainment.Video;
import filter.Filter;
import platform.queries.videoQuery.VideoQuery;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Class contains information on Longest Video Query
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public class VideoLongestQuery extends VideoQuery {

    public VideoLongestQuery(final int number, final String sortType,
                             final List<Video> videos, final Filter filter) {
        super(number, sortType, videos, filter);
    }

    /**
     * No need for validation in the longest Video Queries.
     * @param x Initial list
     * @return x
     */
    @Override
    public List<Video> getValidatedList(final List<Video> x) {
        return x;
    }

    /**
     * The Longest Video Query Sort
     * <p> This method sorts the given videos by the duration and then by the
     * name in ascendant order.
     *
     * @param x Initial list
     * @return
     */
    @Override
    public List<Video> getSortedList(final List<Video> x) {
        List<Video> sorted;

        Comparator<Video> durationComparator = (Video v1, Video v2) ->
                Double.compare(v1.getDuration(), v2.getDuration());

        Comparator<Video> nameComparator = (Video v1, Video v2) ->
                v1.getTitle().compareTo(v2.getTitle());

        sorted = (List<Video>) x.stream()
                .sorted(durationComparator.thenComparing(nameComparator))
                .collect(Collectors.toList());

        return sorted;
    }
}
