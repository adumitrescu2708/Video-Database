package platform.queries;

import platform.Action;
import filter.Filter;
import java.util.Collections;
import java.util.List;

/**
 * Class contains information about a generic Query
 * <p> A query describes a powerful tool that not only retrieves relevant
 * metadata from the given database, but also filters, validates and sorts data.
 * <p> Given the great relevance to our platform, we propose a
 * generic Query Class, that facilitates future improvement on the platform.
 *
 * @param <E> The type of database object we are applying
 *                queries on.
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public abstract class Query<E> extends Action {
    /**
     * Filter
     */
    protected Filter filter;
    /**
     * Number of elements
     */
    protected int number;
    /**
     * Sort Type - ascending or descending
     */
    protected String sortType;
    /**
     * Initial List of Objects - Could be Actors, Videos or Users
     */
    protected List<E> list;

    /**
     * Constructor
     * <p> Creates a query with the given number, sort type, list of initial
     * objects and a filter. No Query could exist without these fields.
     *
     * @param number The interrogated number
     * @param sortType Sort Type, could be ascending or descending
     * @param list Initial List of Objects
     * @param filter Expected filter
     */
    public Query(final int number, final String sortType,
                 final List<E> list, final Filter filter) {
        this.number = number;
        this.sortType = sortType;
        this.list = list;
        this.filter = filter;
    }

    /**
     * First Step
     * <p> Retrieve the filtered list from the initial list
     * @param x Initial list
     * @return Filtered list
     */
    public abstract List<E> getFilteredList(List<E> x);

    /**
     * Second Step
     * <p> Check the validation of a property and retrieve the new list.
     * <p> The property could be extended to anything, but in out cases
     * we will apply common existence restriction we will later on describe.
     *
     * @param x Initial list
     * @return Validated list
     */
    public abstract List<E> getValidatedList(List<E> x);

    /**
     * Third Step
     * <p> Sort the given list by default in ascendant order after specific fields
     * depending on the query's behaviour.
     *
     * @param x Initial list
     * @return Sorted list
     */
    public abstract List<E> getSortedList(List<E> x);

    /**
     * Fourth Step
     * <p> Our sorting mechanism, sorts objects by default in ascendant order.
     * In case of a descending sort type requirement, we reverse the list.
     *
     * @param x List of objects
     */
    public void getCorrectOrder(final List<E> x) {
        if (sortType.equals("desc")) {
            Collections.reverse(x);
        }
    }

    /**
     * Last Step
     * <p> Given a number of expected objects, this method retrieves
     * the sublist of that number from the given list.
     *
     * @param x Initial list
     * @return Sublist of the required number
     */
    public List<E> getCorrectNumber(final List<E> x) {
        if (x.size() < number) {
            return x;
        }
        return x.subList(0, number);
    }

    /**
     * Final List Retriever
     * <p> This method follows the above-mentioned steps and
     * retrieves the final list
     *
     * @param x Initial list
     * @return Final list
     */
    public List<E> getFinalList(final List<E> x) {
        List<E> filteredList = getFilteredList(x);
        List<E> validatedList = getValidatedList(filteredList);
        List<E> sortedList = getSortedList(validatedList);
        getCorrectOrder(sortedList);
        List<E> finalList = getCorrectNumber(sortedList);

        return finalList;
    }

    /**
     * Filter Setter
     * @param filter The filter
     */
    public void setFilter(final Filter filter) {
        this.filter = filter;
    }
}
