//Zacharias Thorell

package lib.event;

import org.json.JSONObject;

public class DeathCharacterEvent extends CharacterEvent {
    private static final String EVENT_NAME = "Death";

    public final String attackerId;
    public final boolean headshot;

    public DeathCharacterEvent(String playerId, String attacker, String headshot) {
        super(EVENT_NAME, playerId);
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
