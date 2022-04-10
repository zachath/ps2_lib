//Zacharias Thorell

package lib.event;

import org.json.JSONObject;

public class PlayerLoginCharacterEvent extends CharacterEvent {

    private static final String EVENT_NAME = "PlayerLogin";

    public PlayerLoginCharacterEvent(String playerId, String world_id, String timestamp) {
        super(EVENT_NAME, world_id, timestamp, playerId);
    }

    public PlayerLoginCharacterEvent(JSONObject object) {
        super(object);
    }

    @Override
    public String toString() {
        return characterID + " logged into world " + world_id + " at " + timestamp;
    }
}