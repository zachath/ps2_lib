//Zacharias Thorell

package lib.event;

import lib.CensusAPI;
import lib.Faction;
import org.json.JSONObject;

public class FacilityControlCharacterEvent extends CharacterEvent {
    private static final String EVENT_NAME = "FacilityControl";

    public final String durationHeld, facilityID, outfitID;
    public final Faction newFaction, oldFaction;

    protected FacilityControlCharacterEvent(String world_id, String timestamp, String player, String durationHeld, String facilityID, String outfitID, Faction newFaction, Faction oldFaction) {
        super(EVENT_NAME, world_id, timestamp, player);
        this.durationHeld = durationHeld;
        this.facilityID = facilityID;
        this.outfitID = outfitID;
        this.newFaction = newFaction;
        this.oldFaction = oldFaction;
    }

    protected FacilityControlCharacterEvent(JSONObject object) {
        super(object);
        this.durationHeld = object.getString("duration_held");
        this.facilityID = object.getString("facility_id");
        this.outfitID = object.getString("outfit_id");
        this.newFaction = CensusAPI.FACTION_MAP.get(object.getString("new_faction_id"));
        this.oldFaction = CensusAPI.FACTION_MAP.get(object.getString("old_faction_id"));
    }

    @Override
    public String toString() {
        return characterID + " controlled facility " + facilityID;
    }
}
