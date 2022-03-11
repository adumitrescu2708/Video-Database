package filter;

import actor.ActorsAwards;
import entertainment.Genre;
import java.util.ArrayList;
import java.util.List;

/**
 * Class contains information about a Filter
 * <p> Filters are commonly used objects for actions such as queries and
 * recommendations.
 * <p> Our platform applies filters on Actors, specifically awards and keywords
 * and on Videos, years and genres.
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public class Filter {
    /**
     * List of Years
     */
    private List<Integer> year = new ArrayList<>();
    /**
     * List of Genres
     */
    private List<Genre> genres = new ArrayList<>();
    /**
     * List of Words
     */
    private List<String> words = new ArrayList<>();
    /**
     * List of Awards
     */
    private List<ActorsAwards> awards = new ArrayList<>();

    /**
     * Default Constructor
     */
    public Filter() { }

    /**
     * Constructor
     * <p> Creates a Filter object with the given Genre
     * @param genre the filtered genre
     */
    public Filter(final Genre genre) {
        this.genres.add(genre);
    }

    /**
     * Constructor
     * <p> Creates a Filter object with the specified List of years,
     * genres, keywords and awards
     *
     * @param year List of expected years
     * @param genre List of expected genres
     * @param words List of expected keywords
     * @param awards List of expected awards
     */
    public Filter(final List<Integer> year, final List<Genre> genre, final List<String> words,
                  final List<ActorsAwards> awards) {
        this.year = year;
        this.words = words;
        this.awards = awards;
        this.genres = genre;
    }

    /**
     * Words Getter
     * @return the list of KeyWords
     */
    public List<String> getWords() {
        return words;
    }

    /**
     * Years Getter
     * @return the list of Years
     */
    public List<Integer> getYear() {
        return year;
    }

    /**
     * Genres Getter
     * @return the list of Genres
     */
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     * Awards Getter
     * @return the list of Awards
     */
    public List<ActorsAwards> getAwards() {
        return awards;
    }
}
