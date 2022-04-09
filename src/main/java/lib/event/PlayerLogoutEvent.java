//Zacharias Thorell

package lib.event;

import org.json.JSONObject;

public class PlayerLogoutEvent extends PlayerLoginOutEvent {

    protected PlayerLogoutEvent(String playerId, String world_id, String timestamp) {
        super(playerId, world_id, timestamp);
    }

    protected PlayerLogoutEvent(JSONObject object) {
        super(object);
    }

    @Override
    public String toString() {
        return playerID + " logged out of world " + world_id + " at " + timestamp;
    }
}
