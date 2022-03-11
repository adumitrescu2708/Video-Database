package platform.queries.videoQuery;

import entertainment.Video;
import platform.queries.Query;
import filter.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class contains information on a Video Query
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public abstract class VideoQuery extends Query<Video> {

    /**
     * Constructor
     *
     * @param number Expected Number of elements
     * @param sortType Sort Type (could be ascending or descending)
     * @param videos List of initial videos
     * @param filter Expected Filter
     */
    public VideoQuery(final int number, final String sortType, final List<Video> videos,
                      final Filter filter) {
        super(number, sortType, videos, filter);
    }

    /**
     * Filter List
     * <p> The videos are filtered from a given list of genres and of years.
     * This method returns all videos having at least one year and one
     * genre from the required filters
     *
     * @param x Initial list
     * @return Filtered videos
     */
    @Override
    public List<Video> getFilteredList(final List<Video> x) {
        List<Video> filteredList = new ArrayList<>();

        if (filter.getGenres().isEmpty() && filter.getYear().isEmpty()) {
            return x;
        }

        if (filter.getGenres().isEmpty()) {
            x.stream()
                    .filter((video) -> video.hasYearFromList(filter.getYear()))
                    .forEach(filteredList::add);
        }

        if (filter.getYear().isEmpty()) {
            x.stream()
                    .filter((video) -> (video.hasGenreFromList(filter.getGenres())))
                    .forEach(filteredList::add);
        }

        if (!filter.getGenres().isEmpty() && !filter.getYear().isEmpty()) {
            x.stream()
                    .filter((video) -> (video.hasGenreFromList(filter.getGenres())))
                    .filter((video) -> (video.hasYearFromList(filter.getYear())))
                    .forEach(filteredList::add);
        }

        return filteredList;
    }

    /**
     * <p> This method generates the output message after retrieving
     * the expected final list.
     */
    @Override
    public void execute() {
        List<Video> finalList = getFinalList(list);

        message.append("Query result: ");
        message.append("[");
        if (finalList.size() > 0) {
            message.append(finalList.get(0).getTitle());
            finalList.remove(0);
            finalList.forEach((video) -> message.append(", ").append(video.getTitle()));
        }
        message.append("]");
    }
}
