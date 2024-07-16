package platform.queries.usersQuery;

import filter.Filter;
import platform.queries.Query;
import user.User;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Class contains information on a User Query
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public class UserQuery extends Query<User> {
    public UserQuery(final int number, final String sortType, final List<User> list,
                     final Filter filter) {
        super(number, sortType, list, filter);
    }

    /**
     * No need for filter in the user query in this scenario.
     * @param x Initial list
     * @return x
     */
    @Override
    public List<User> getFilteredList(final List<User> x) {
        return x;
    }

    /**
     * User Validation
     * <p> This method selects all users that have given at least one rating,
     * meaning at least activity score of 1.
     *
     * @param x Initial list
     * @return Validated list
     */
    @Override
    public List<User> getValidatedList(final List<User> x) {
        List<User> validatedList = new ArrayList<>();

        x.stream()
                .filter((user) -> user.getActivityCounter() != 0)
                .forEach(validatedList::add);

        return validatedList;
    }

    /**
     * User sort
     * <p> This method sorts all given users by their activity score and
     * then by name in ascendant order.
     *
     * @param x Initial list
     * @return Sorted list
     */
    @Override
    public List<User> getSortedList(final List<User> x) {
        List<User> sorted;

        Comparator<User> activityComparator = (User u1, User u2) ->
                Integer.compare(u1.getActivityCounter(), u2.getActivityCounter());

        Comparator<User> nameComparator = (User u1, User u2) -> u1.getUsername()
                .compareTo(u2.getUsername());

        sorted = (List<User>) x.stream()
                .sorted(activityComparator.thenComparing(nameComparator))
                .collect(Collectors.toList());

        return sorted;
    }

    /**
     * <p> This method generates the output message after retrieving
     * the expected users list.
     */
    @Override
    public void execute() {
        List<User> finalList = getFinalList(list);

        message.append("Query result: ");
        message.append("[");
        if (finalList.size() >= 1) {
            message.append(finalList.get(0).getUsername());
            finalList.remove(0);
            finalList.forEach((user) -> message.append(", ").append(user.getUsername()));
        }
        message.append("]");
    }
}
