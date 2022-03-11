package entertainment;

import fileio.SerialInputData;
import user.User;
import utils.Utils;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class contains information about a Serial
 * <p> A Serial is considered to be a Video type with additional information
 * such as Seasons. Serial class implements the required methods described
 * in the Video parent-class with the specific behaviour.
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public class Series extends Video {
    /**
     * Number of Seasons
     */
    private int numberOfSeasons;
    /**
     * List of Seasons
     */
    private ArrayList<Season> seasons;
    /**
     * Map that stores for each Season a List of reviewers
     */
    private HashMap<Season, ArrayList<User>> reviewers;

    /**
     * Constructor
     * <p> Creates a Serial based on the given Serial Input Data, parsed
     * from the JSON files. From the given input, we store the information
     * on title, year, cast, number of seasons and seasons.
     * <p> For the Genres, we use the static method described in Utils
     * to parse strings to genres
     * <p> Once a Serial is added in the Database, its map for reviewers
     * is generated.
     *
     * @param input Serial Input Data from JSON
     */
    public Series(final SerialInputData input) {
        super(input.getTitle(), input.getYear(), input.getCast());
        this.seasons = input.getSeasons();
        this.numberOfSeasons = input.getNumberSeason();
        input.getGenres().stream()
                .forEach((genre) -> genres.add(Utils.stringToGenre(genre)));

        reviewers = new HashMap<>();
    }
    /**
     * Review Checker
     * <p> This method checks if the given user has already reviewed
     * the specified season of the serial. Unlike Movies, Serials propose
     * a different approach, allowing users to review each season once.
     * <p> Therefore, we implemented this method, used for making sure
     * a user doesn't review the same season twice.
     *
     * @param user User
     * @param seasonIdx Optional, Used for Serial Video types
     * @return
     */
    public boolean isReviewedBy(final User user, final int seasonIdx) {
        Season reviewedSeason = seasons.get(seasonIdx - 1);
        ArrayList<User> seasonReviewers = reviewers.get(reviewedSeason);

        if (seasonReviewers == null) {
            return false;
        }

        for (User reviewer : seasonReviewers) {
            if (reviewer.equals(user)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Review Setter
     * <p> This method adds given user to the Map of reviewers
     * for the specified season. The given grade is then added in the
     * list of grades of the specified season.
     *
     * @param user User that sets a review
     * @param grade The review Grade
     * @param seasonIdx Optional, used for Serial Video type
     */
    public void setReviewer(final User user, final Double grade, final int seasonIdx) {
        Season reviewedSeason = seasons.get(seasonIdx - 1);
        ArrayList<User> seasonReviewers = reviewers.get(reviewedSeason);
        if (seasonReviewers == null) {
            seasonReviewers = new ArrayList<>();
        }
        seasonReviewers.add(user);
        reviewers.put(reviewedSeason, seasonReviewers);
        seasons.get(seasonIdx - 1).getRatings().add(grade);
    }

    /**
     * Duration Getter
     * <p> The total duration of a Serial is calculated as the sum of
     * durations of all seasons.
     * @return the duration of the Serial
     */
    @Override
    public int getDuration() {
        return seasons.stream().mapToInt(Season::getDuration).sum();
    }

    /**
     * Serial Rating Calculator
     * <p> The Serial Rating is calculated as the average of all
     * ratings of its Seasons.
     *
     * @return Rating average grade
     */
    public Double getRating() {
        double averageSerial = 0;
        double averageSeason = 0;

        for (Season season : seasons) {
            if (season.getRatings().size() != 0) {
                averageSeason = season.getRatings().stream().mapToDouble(rating -> rating).sum();
                averageSerial += averageSeason / season.getRatings().size();
            }
        }
        if (seasons.size() != 0) {
            return averageSerial / seasons.size();
        }
        return 0d;
    }
}
