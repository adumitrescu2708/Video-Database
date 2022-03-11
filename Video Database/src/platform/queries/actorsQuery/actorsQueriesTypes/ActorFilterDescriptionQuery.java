package platform.queries.actorsQuery.actorsQueriesTypes;

import actor.Actor;
import platform.queries.actorsQuery.ActorQuery;
import filter.Filter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Class contains information on Actor filter Description Query
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public class ActorFilterDescriptionQuery extends ActorQuery {

    public ActorFilterDescriptionQuery(final int number, final String sortType,
                                       final List<Actor> actors, final Filter filter) {
        super(number, sortType, actors, filter);
    }

    /**
     * Filter description Validation
     * <p> Given a list of keywords, this method selects all actors
     * having the stated words in their career description.
     *
     * @param x Initial list
     * @return Validated list
     */
    @Override
    public List<Actor> getValidatedList(final List<Actor> x) {
        List<Actor> validatedActors = new ArrayList<>();

        x.stream()
                .filter((actor) -> actor.hasKeyWords(filter.getWords()))
                .forEach(validatedActors::add);

        return validatedActors;
    }

    /**
     * Filter Description Sort
     * <p> This method sorts actors by name in ascendant order
     * @param x Initial list
     * @return Sorted list
     */
    @Override
    public List<Actor> getSortedList(final List<Actor> x) {
        List<Actor> sorted;

        Comparator<Actor> nameComparator = (Actor a1, Actor a2) ->
                a1.getName().compareTo(a2.getName());

        sorted = (List<Actor>) x.stream()
                .sorted(nameComparator)
                .collect(Collectors.toList());

        return sorted;
    }
}
