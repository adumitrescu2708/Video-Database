package platform.queries.actorsQuery.actorsQueriesTypes;

import actor.Actor;
import platform.queries.actorsQuery.ActorQuery;
import filter.Filter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Class contains information on Actor Awards Query
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public class ActorAwardsQuery extends ActorQuery {

    public ActorAwardsQuery(final int number, final String sortType,
                            final List<Actor> actors, final Filter filter) {
        super(number, sortType, actors, filter);
    }

    /**
     * Awards Validate
     * <p> This method selects all actors from the given list having the
     * specified awards.
     * @param x Initial list
     * @return Validated list
     */
    @Override
    public List<Actor> getValidatedList(final List<Actor> x) {
        List<Actor> validatedList = new ArrayList<>();

        x.stream()
                .filter((actor) -> actor.hasAwardsFromList(filter.getAwards()))
                .forEach(validatedList::add);

        return validatedList;
    }

    /**
     * Awards Sort
     * <p> This method sorts a given list of actors by the number of total awards and
     * then by name in ascendant order.
     * @param x Initial list
     * @return Sorted list
     */
    @Override
    public List<Actor> getSortedList(final List<Actor> x) {
        List<Actor> sorted;

        Comparator<Actor> awardsComparator = (Actor a1, Actor a2) ->
                Integer.compare(a1.countAwards(), a2.countAwards());

        Comparator<Actor> nameComparator = (Actor a1, Actor a2) ->
                a1.getName().compareTo(a2.getName());

        sorted = (List<Actor>) x.stream()
                .sorted(awardsComparator.thenComparing(nameComparator))
                .collect(Collectors.toList());

        return sorted;
    }
}
