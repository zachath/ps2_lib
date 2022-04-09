//Zacharias Thorell

package lib.event;

import org.json.JSONObject;

abstract public class PlayerLoginOutEvent extends Event {
    protected final String world_id, timestamp;

    protected PlayerLoginOutEvent(String playerId, String world_id, String timestamp) {
        super(playerId);
        this.world_id = world_id;
        this.timestamp = timestamp;
    }

    protected PlayerLoginOutEvent(JSONObject object) {
        super(object);
        this.world_id = object.getString("world_id");
        this.timestamp = object.getString("timestamp");
    }
}