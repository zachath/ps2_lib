//Zacharias Thorell

package ps2lib.event;

import org.json.JSONObject;

/**
 * An event generated when a character logs out.
 */
public class PlayerLogoutCharacterEvent extends CharacterEvent {
    public static final String EVENT_NAME = "PlayerLogout";

    public PlayerLogoutCharacterEvent(String playerId, String world_id, String timestamp) {
        super(EVENT_NAME, world_id, timestamp, playerId);
    }

    public PlayerLogoutCharacterEvent(JSONObject object) {
        super(object);
    }

    @Override
    public String toString() {
        return characterID + " logged out of world " + world_id + " at " + timestamp;
    }
}
