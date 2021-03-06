//Zacharias Thorell

package ps2lib.event;

import org.json.JSONObject;

/**
 * An event generated when a character adds a skill.
 */
public class SkillAddedCharacterEvent extends CharacterEvent {
    public static final String EVENT_NAME = "SkillAdded";

    public final String skillID;

    protected SkillAddedCharacterEvent(String world_id, String timestamp, String player, String skillID) {
        super(EVENT_NAME, world_id, timestamp, player);
        this.skillID = skillID;
    }

    protected SkillAddedCharacterEvent(JSONObject object) {
        super(object);
        this.skillID = object.getString("skill_id");
    }

    @Override
    public String toString() {
        return characterID + " added skill " + skillID;
    }
}
