package platform.queries.actorsQuery.actorsQueriesTypes;

import actor.Actor;
import platform.queries.actorsQuery.ActorQuery;
import filter.Filter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Class contains information on Actor Average Query
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public class ActorAverageQuery extends ActorQuery {

    public ActorAverageQuery(final int number, final String sortType,
                             final List<Actor> actors, final Filter filter) {
        super(number, sortType, actors, filter);
    }

    /**
     * Average Validation
     * <p> This method selects all actors that have played at least in a video which
     * has at least one review.
     * @param x Initial list
     * @return Validated list
     */
    @Override
    public List<Actor> getValidatedList(final List<Actor> x) {
        List<Actor> validatedList = new ArrayList<>();

        x.stream()
                .filter((actor) -> actor.getAverageRating() != 0)
                .forEach(validatedList::add);

        return validatedList;
    }

    /**
     * Average Sort
     * <p> This method sorts actors by their average rating grade and then by name in
     * ascendant order
     * @param x Initial list
     * @return Sorted list
     */
    @Override
    public List<Actor> getSortedList(final List<Actor> x) {
        List<Actor> sorted;

        Comparator<Actor> averageComparator = (Actor a1, Actor a2) ->
                        Double.compare(a1.getAverageRating(), a2.getAverageRating());

        Comparator<Actor> nameComparator = (Actor a1, Actor a2) ->
                                a1.getName().compareTo(a2.getName());

        sorted = (List<Actor>) x.stream()
                .sorted(averageComparator.thenComparing(nameComparator))
                .collect(Collectors.toList());

        return sorted;
    }
}
