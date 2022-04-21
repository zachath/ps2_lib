//Zacharias Thorell

package ps2lib.event;

import org.json.JSONObject;

/**
 * An event generated when a character gain experience (WARNING, occurs very frequently).
 */
public class GainExperienceCharacterEvent extends CharacterEvent {
    public static final String EVENT_NAME = "GainExperience";

    public final String amount;

    protected GainExperienceCharacterEvent(String world_id, String timestamp, String player, String amount) {
        super(EVENT_NAME, world_id, timestamp, player);
        this.amount = amount;
    }

    protected GainExperienceCharacterEvent(JSONObject object) {
        super(object);
        this.amount = object.getString("amount");
    }

    @Override
    public String toString() {
        return characterID + " has gained " + amount + " experience";
    }
}
