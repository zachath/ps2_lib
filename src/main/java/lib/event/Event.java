//Zacharias Thorell

package lib.event;

public abstract class Event {
    protected String playerID;

    protected Event(String player) {
        this.playerID = player;
    }

    public String getPlayerID() {
        return playerID;
    }

    @Override
    abstract public String toString();
}
