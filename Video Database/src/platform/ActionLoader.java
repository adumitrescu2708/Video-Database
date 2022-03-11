package platform;

import actor.Actor;
import database.Database;
import entertainment.Video;
import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import platform.commands.FavoriteCommand;
import platform.commands.RatingCommand;
import platform.commands.ViewCommand;
import platform.queries.actorsQuery.actorsQueriesTypes.ActorAverageQuery;
import platform.queries.actorsQuery.actorsQueriesTypes.ActorAwardsQuery;
import platform.queries.actorsQuery.actorsQueriesTypes.ActorFilterDescriptionQuery;
import filter.Filter;
import filter.FilterLoader;
import platform.queries.usersQuery.UserQuery;
import platform.queries.videoQuery.videosQueriesTypes.VideoFavoriteQuery;
import platform.queries.videoQuery.videosQueriesTypes.VideoLongestQuery;
import platform.queries.videoQuery.videosQueriesTypes.VideoMostViewed;
import platform.queries.videoQuery.videosQueriesTypes.VideoRatingQuery;
import platform.recommendation.BestUnseenRecommendation;
import platform.recommendation.FavoriteRecommendation;
import platform.recommendation.PopularRecommendation;
import platform.recommendation.SearchRecommendation;
import platform.recommendation.StandardRecommendation;
import user.User;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class represents an Action Loader Layer
 * <p> Given the input, the Action Loader parses each input into the
 * corresponding action and in the end executes it, providing an output
 * message to the JSON array of results.
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public final class ActionLoader {

    private ActionLoader() { }

    /**
     * <p> For each action from the input data, this method isolates the required
     * objects and then decides which action object is going to be created.
     * <p> Using polymorphism, we use an Action Object and depending on
     * the input's action type we create the specified action.
     * <p> In the end, it executes the action and stores the output message
     * in the JSON array.
     *
     *
     * @param input Input
     * @param arrayResult JSON array storing the results (the output messages)
     * @param fileWriter The writer
     * @param instance Database instance
     */
    public static void actionLoading(final Input input, final JSONArray arrayResult,
                                     final Writer fileWriter, final Database instance) {
        Action command = null;
        JSONObject result = null;

        for (ActionInputData actionData : input.getCommands()) {

            double grade = actionData.getGrade();
            int seasonIdx = actionData.getSeasonNumber();
            int number = actionData.getNumber();

            User user = instance.getUserByName(actionData.getUsername());
            Video video = instance.getVideoByName(actionData.getTitle());
            Filter filter = FilterLoader.loadFilters(actionData);
            String sortType = actionData.getSortType();
            List<User> users = instance.getUsers();
            List<Actor> actors = instance.getActors();
            List<Video> videoList = new ArrayList<>();

            if (actionData.getObjectType() != null) {
                if (actionData.getObjectType().equals("movies")) {
                    videoList.addAll(instance.getMovies());
                } else {
                    videoList.addAll(instance.getSeries());
                }
            }

            switch (actionData.getActionType()) {
                case "command":
                    command = switch (actionData.getType()) {
                        case "favorite" -> new FavoriteCommand(user, video);
                        case "view" -> new ViewCommand(user, video);
                        case "rating" -> new RatingCommand(user, video, grade, seasonIdx);
                        default -> command;
                    };
                    break;
                case "query":
                    switch (Objects.requireNonNull(actionData.getObjectType())) {
                        case "actors" -> {
                            command = switch (actionData.getCriteria()) {
                                case "average" -> new ActorAverageQuery(number, sortType, actors,
                                        filter);
                                case "awards" -> new ActorAwardsQuery(number, sortType, actors,
                                        filter);
                                case "filter_description" -> new ActorFilterDescriptionQuery(
                                        number, sortType, actors, filter);
                                default -> command;
                            };
                        }
                        case "movies", "shows" -> {
                            command = switch (actionData.getCriteria()) {
                                case "ratings" -> new VideoRatingQuery(number, sortType,
                                        videoList, filter);
                                case "longest" -> new VideoLongestQuery(number, sortType,
                                        videoList, filter);
                                case "favorite" -> new VideoFavoriteQuery(number, sortType,
                                        videoList, filter);
                                case "most_viewed" -> new VideoMostViewed(number, sortType,
                                        videoList, filter);
                                default -> command;
                            };
                        }
                        case "users" -> {
                            command = new UserQuery(number, sortType, users, filter);
                        }
                        default -> {
                            command = command;
                        }
                    }
                case "recommendation":
                    if (actionData.getType() != null) {
                        command = switch (actionData.getType()) {
                            case "standard" -> new StandardRecommendation(user, instance);
                            case "best_unseen" -> new BestUnseenRecommendation(user, instance);
                            case "favorite" -> new FavoriteRecommendation(user, instance);
                            case "search" -> new SearchRecommendation(user, instance,
                                    Utils.stringToGenre(actionData.getGenre()));
                            case "popular" -> new PopularRecommendation(user, instance);
                            default -> command;
                        };
                    }
                default:
            }

            command.execute();
            try {
                result = fileWriter.writeFile(actionData.getActionId(), "",
                        command.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            arrayResult.add(result);
        }
    }
}
