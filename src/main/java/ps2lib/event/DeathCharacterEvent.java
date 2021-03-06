//Zacharias Thorell

package ps2lib.event;

import org.json.JSONObject;

/**
 * An event generated when a character dies.
 */
public class DeathCharacterEvent extends CharacterEvent {
    public static final String EVENT_NAME = "Death";

    public final String attackerId;
    public final boolean headshot;

    public DeathCharacterEvent(String world_id, String timestamp, String playerId, String attacker, String headshot) {
        super(EVENT_NAME, world_id, timestamp, playerId);
        this.attackerId = attacker;
        this.headshot = headshot.equals("1");
    }

    public DeathCharacterEvent(JSONObject object) {
        super(object);
        this.attackerId = object.getString("attacker_character_id");
        this.headshot = object.get("is_headshot").equals("1");
    }

    @Override
    public String toString() {
        return characterID + " killed by " + attackerId;
    }
}
