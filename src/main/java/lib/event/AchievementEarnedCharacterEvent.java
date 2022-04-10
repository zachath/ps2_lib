//Zacharias Thorell

package lib.event;

import org.json.JSONObject;

public class AchievementEarnedCharacterEvent extends CharacterEvent {
    private static final String EVENT_NAME = "AchievementEarned";

    public final String achievementID;

    protected AchievementEarnedCharacterEvent(String player, String world_id, String timestamp, String achievementID) {
        super(EVENT_NAME, world_id, timestamp, player);
        this.achievementID = achievementID;
    }

    protected AchievementEarnedCharacterEvent(JSONObject object) {
        super(object);
        this.achievementID = object.getString("achievement_id");
    }

    @Override
    public String toString() {
        return characterID + " got achievement " + achievementID;
    }
}
