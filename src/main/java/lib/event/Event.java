//Zacharias Thorell

package lib.event;

import org.json.JSONObject;

public abstract class Event {
    protected String playerID;

    protected Event(String player) {
        this.playerID = player;
    }

    protected Event(JSONObject object) {
        this.playerID = object.getString("character_id");
    }

    public String getPlayerID() {
        return playerID;
    }

    @Override
    abstract public String toString();
}
