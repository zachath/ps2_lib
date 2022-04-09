//Zacharias Thorell

package lib.event;

import org.json.JSONObject;

public class DeathEvent extends Event {
    private final String attackerId;
    private final boolean headshot;

    public DeathEvent(String playerId, String attacker, String headshot) {
        super(playerId);
        this.attackerId = attacker;
        this.headshot = headshot.equals("1");
    }

    public DeathEvent(JSONObject object) {
        super(object);
        this.attackerId = object.getString("attacker_character_id");
        this.headshot = object.get("is_headshot").equals("1");
    }

    public String getAttackerId() {
        return attackerId;
    }

    public boolean isHeadshot() {
        return headshot;
    }

    @Override
    public String toString() {
        return playerID + " killed by " + attackerId;
    }
}
