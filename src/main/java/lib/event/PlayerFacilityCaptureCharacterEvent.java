//Zacharias Thorell

package lib.event;

import org.json.JSONObject;

public class PlayerFacilityCaptureCharacterEvent extends CharacterEvent {
    public static final String EVENT_NAME = "PlayerFacilityCapture";

    public final String facilityID, outfitID;

    protected PlayerFacilityCaptureCharacterEvent(String world_id, String timestamp, String player, String facilityID, String outfitID) {
        super(EVENT_NAME, world_id, timestamp, player);
        this.facilityID = facilityID;
        this.outfitID = outfitID;
    }

    protected PlayerFacilityCaptureCharacterEvent(JSONObject object) {
        super(object);
        this.facilityID = object.getString("facility_id");
        this.outfitID = object.getString("outfit_id");
    }

    @Override
    public String toString() {
        return characterID + " captured " + facilityID;
    }
}
