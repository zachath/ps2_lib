//Zacharias Thorell

package lib.event;

import org.json.JSONObject;

public class PlayerLogoutCharacterEvent extends CharacterEvent {
    private static final String EVENT_NAME = "PlayerLogout";

    public final String world_id, timestamp;

    public PlayerLogoutCharacterEvent(String playerId, String world_id, String timestamp) {
        super(EVENT_NAME, playerId);
        this.world_id = world_id;
        this.timestamp = timestamp;
    }

    public PlayerLogoutCharacterEvent(JSONObject object) {
        super(object);
        this.world_id = object.getString("world_id");
        this.timestamp = object.getString("timestamp");
    }

    @Override
    public String toString() {
        return characterID + " logged out of world " + world_id + " at " + timestamp;
    }
}
