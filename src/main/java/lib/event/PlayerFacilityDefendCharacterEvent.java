//Zacharias Thorell

package lib.event;

import org.json.JSONObject;

public class PlayerFacilityDefendCharacterEvent extends CharacterEvent {
    private static final String EVENT_NAME = "PlayerFacilityDefend";

    public final String facilityID, outfitID;

    protected PlayerFacilityDefendCharacterEvent(String world_id, String timestamp, String player, String facilityID, String outfitID) {
        super(EVENT_NAME, world_id, timestamp, player);
        this.facilityID = facilityID;
        this.outfitID = outfitID;
    }

    protected PlayerFacilityDefendCharacterEvent(JSONObject object) {
        super(object);
        this.facilityID = object.getString("facility_id");
        this.outfitID = object.getString("outfit_id");
    }

    @Override
    public String toString() {
        return characterID + " defended " + facilityID;
    }
}
