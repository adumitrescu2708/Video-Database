package platform;

/**
 * Class contains information about an Action
 * <p> Our platform supports three different action types:
 * commands, queries and recommendations. This class describes
 * the requirements for these three and for future actions.
 *
 * @author Dumitrescu Alexandra
 * @since Nov 2021
 */
public abstract class Action {
    /**
     * The output message of the action
     */
    protected StringBuilder message = new StringBuilder();

    /**
     * Action executor
     * <p> This method will be implemented by all action types,
     * depending on a specific behaviour.
     */
    public abstract void execute();

    /**
     * Message Getter
     * <p> This method returns the output message of an action and
     * guarantees that the action has been executed in the first place.
     * <p> Otherwise, no output message would exist without an executed
     * action.
     *
     * @return the output message of the action
     */
    public String getMessage() {
        if (message == null) {
            execute();
        }
        return message.toString();
    }
}
