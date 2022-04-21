//Zacharias Thorell

package ps2lib.event;

import org.json.JSONObject;

/**
 * An event generated when a character ranks up.
 */
public class BattleRankUpCharacterEvent extends CharacterEvent {
    public static final String EVENT_NAME = "BattleRankUp";

    public final String battleRank;

    protected BattleRankUpCharacterEvent(String world_id, String timestamp, String player, String battleRank) {
        super(EVENT_NAME, world_id, timestamp, player);
        this.battleRank = battleRank;
    }

    protected BattleRankUpCharacterEvent(JSONObject object) {
        super(object);
        this.battleRank = object.getString("battle_rank");
    }

    @Override
    public String toString() {
        return characterID + " got to battlerank " + battleRank;
    }
}
