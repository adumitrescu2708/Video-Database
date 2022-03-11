package filter;

import actor.ActorsAwards;
import entertainment.Genre;
import fileio.ActionInputData;
import utils.Utils;
import java.util.ArrayList;
import java.util.List;

/**
 * Class represents a Filter Loader Layer
 * <p> Given the action input, a FilterLoader object loads the given input
 * in a filter object
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public final class FilterLoader {
    /**
     * Default Constructor
     */
    private FilterLoader() { }

    /**
     * Filer Loader
     * <p> This method retrieves the years, genres, keywords and awards from
     * the input's filter.
     * <p> We first isolate the 4 Lists of strings from the given input
     * <p> If these lists contain data, we store them in new lists with
     * eventual parsing. For the words filter, we store them directly.
     * For the years filter, we parse each year from string to integer.
     * For the awards, we parse each string in award type using Utils.stringToAwards
     * static method. For the genres, we parse each genre from string to
     * corresponding genre using Utils.stringToGenre static method.
     *
     * @param actionInput Action Input from which we extract the filter
     * @return Parsed filter
     */
    public static Filter loadFilters(final ActionInputData actionInput) {

        if (actionInput == null || actionInput.getFilters().size() == 0) {
            return null;
        }

        int index = 0;
        List<String> years = actionInput.getFilters().get(index);
        index++;
        List<String> genres = actionInput.getFilters().get(index);
        index++;
        List<String> words = actionInput.getFilters().get(index);
        index++;
        List<String> awards = actionInput.getFilters().get(index);

        List<String> wordsFilter = new ArrayList<>();
        if (words != null) {
            wordsFilter.addAll(words);
        }

        List<Genre> genresFilter = new ArrayList<>();
        if (genres.get(0) != null) {
            genres.forEach((genre) -> genresFilter.add(Utils.stringToGenre(genre)));
        }

        List<ActorsAwards> awardsFilter = new ArrayList<>();
        if (awards != null) {
            awards.forEach((award) -> awardsFilter.add(Utils.stringToAwards(award)));
        }

        List<Integer> yearFilter = new ArrayList<>();
        if (years.get(0) != null) {
            years.forEach((year) -> yearFilter.add(Integer.parseInt(year)));
        }

        return new Filter(yearFilter, genresFilter, wordsFilter, awardsFilter);
    }
}
