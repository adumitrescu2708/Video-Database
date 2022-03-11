package platform.queries.actorsQuery;

import actor.Actor;
import platform.queries.Query;
import filter.Filter;

import java.util.List;
/**
 * Class contains information on an Actor Query
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public abstract class ActorQuery extends Query<Actor> {

    public ActorQuery(final int number, final String sortType,
                      final List<Actor> actors, final Filter filter) {
        super(number, sortType, actors, filter);
    }

    /**
     * No need for filtering in this scenario
     * @param x Initial list
     * @return x
     */
    @Override
    public List<Actor> getFilteredList(final List<Actor> x) {
        return x;
    }

    /**
     * <p> This method generates the output message after retrieving
     * the expected final list of actors.
     */
    @Override
    public void execute() {
        List<Actor> finalList = getFinalList(list);

        message.append("Query result: ");
        message.append("[");
        if (finalList.size() > 0) {
            message.append(finalList.get(0).getName());
            finalList.remove(0);
            finalList.forEach((actor) -> message.append(", ").append(actor.getName()));
        }
        message.append("]");
    }
}
