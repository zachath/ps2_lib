//Zacharias Thorell

package ps2lib.event;

import org.json.JSONObject;

/**
 * Event specific to a specific character.
 */
public abstract class CharacterEvent extends Event {
    public final String characterID;

    protected CharacterEvent(String eventName, String world_id, String timestamp, String player) {
        super(eventName, world_id, timestamp);
        this.characterID = player;
    }

    protected CharacterEvent(JSONObject object) {
        super(object);
        this.characterID = object.getString("character_id");
    }
}
