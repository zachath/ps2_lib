//Zacharias Thorell

package lib.event;

import org.json.JSONObject;

public class SkillAddedCharacterEvent extends CharacterEvent {
    private static final String EVENT_NAME = "SkillAdded";

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
