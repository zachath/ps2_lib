//Zacharias Thorell

package lib.event;

import org.json.JSONObject;

public class PlayerLoginEvent extends PlayerLoginOutEvent {

    protected PlayerLoginEvent(String playerId, String world_id, String timestamp) {
        super(playerId, world_id, timestamp);
    }

    protected PlayerLoginEvent(JSONObject object) {
        super(object);
    }

    @Override
    public String toString() {
        return playerID + " logged into world " + world_id + " at " + timestamp;
    }
}
