package actor;

import database.Database;
import entertainment.Video;
import fileio.ActorInputData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Class contains information about an Actor
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public class Actor {
    /**
     * Actor's Name
     */
    private String name;
    /**
     * Actor's Career Description
     */
    private String careerDescription;
    /**
     * List of Videos in which the Actor has played
     */
    private List<Video> videos;
    /**
     * Map that stores de Awards and the number of Awards
     */
    private Map<ActorsAwards, Integer> awards;

    /**
     * Constructor
     * <p> Creates an Actor from the given Actor Input Data, parsed
     * from the JSON file. We store the name, career description and
     * awards.
     * <p> For the list of Videos, we first filter the input List,
     * verifying the existence of the given video in the
     * Database and then store the video in the List.
     *
     * @param input Actor Input Data from JSON File
     * @param instance instance of the Database
     */
    public Actor(final ActorInputData input, final Database instance) {
        this.name = input.getName();
        this.careerDescription = input.getCareerDescription();
        this.awards = input.getAwards();

        List<Video> inputVideos = new ArrayList<>();
        input.getFilmography().stream()
                .filter((title) -> instance.getVideoByName(title) != null)
                .forEach((title) -> inputVideos.add(instance.getVideoByName(title)));

        this.videos = inputVideos;
    }

    /**
     * Average Calculator
     * <p> This method computes the average grade of all the videos
     * in which the actor has played. This method is used in the
     * queries actions.
     *
     * @return the average rating of all the movies in which the
     * actor has played, and 0 if there is no reviewed movie
     */
    public double getAverageRating() {
        double reviewsSum = 0;
        double reviewsCount = 0;

        for (Video video : videos) {
            if (video.getRating() != 0) {
                reviewsSum += video.getRating();
                reviewsCount++;
            }
        }
        if (reviewsCount != 0) {
            return (reviewsSum / reviewsCount);
        }
        return 0;
    }

    /**
     * Award Checker
     * <p> Given an Award, this method checks if the actor
     * has the specified award.
     *
     * @param award The award we are searching
     * @return true, if the actor has the award and false, otherwise
     */
    public boolean hasAward(final ActorsAwards award) {
        return awards.containsKey(award);
    }

    /**
     * Award List Checker
     * <p> Given a List of Awards, this method checks if the actor
     * has all the specified awards from the List.
     *
     * @param awardsList - List of Awards
     * @return true, if the actor has all awards from List and false, otherwise
     */
    public boolean hasAwardsFromList(final List<ActorsAwards> awardsList) {
        for (ActorsAwards award : awardsList) {
            if (!hasAward(award)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Career Description Filter
     * <p> This method checks if the given keywords are in the
     * career description of the actor. This method is used for queries
     * and filtering.
     * <p> What is more, we propose a case-insensitive search. We use
     * the lower version of both keywords and words from career description and
     * check the equality of them.
     *
     *
     * @param keyWords List of keywords
     * @return true if the actor's career description contains the keywords
     * and false, otherwise
     */
    public boolean hasKeyWords(final List<String> keyWords) {
        for (String word : keyWords) {
            if (!Arrays.asList(careerDescription.toLowerCase().split("[ ,-.]"))
                    .contains(word.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Awards Counter
     * <p> This method computes the number of awards that the actor owns.
     * @return the total number of awards
     */
    public int countAwards() {
        int countAwards = 0;
        for (Map.Entry<ActorsAwards, Integer> entry : awards.entrySet()) {
            countAwards += entry.getValue();
        }
        return countAwards;
    }

    /**
     * Name Getter
     * @return the name of the actor
     */
    public String getName() {
        return name;
    }

    /**
     * Videos Getter
     * @return the list of Videos in wich the actor has played
     */
    public List<Video> getVideos() {
        return videos;
    }

    /**
     * Awards Getter
     * @return the map of awards
     */
    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

}
