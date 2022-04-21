//Zacharias Thorell

package ps2lib.event;

import ps2lib.CensusAPI;
import ps2lib.Faction;
import org.json.JSONObject;

/**
 * An event generated when a facility switches control.
 */
public class FacilityControlEvent extends Event {
    public static final String EVENT_NAME = "FacilityControl";

    public final String zoneID, durationHeld, facilityID, outfitID;
    public final Faction newFaction, oldFaction;

    protected FacilityControlEvent(String world_id, String timestamp, String zoneID, String durationHeld, String facilityID, String outfitID, Faction newFaction, Faction oldFaction) {
        super(EVENT_NAME, world_id, timestamp);
        this.zoneID = zoneID;
        this.durationHeld = durationHeld;
        this.facilityID = facilityID;
        this.outfitID = outfitID;
        this.newFaction = newFaction;
        this.oldFaction = oldFaction;
    }

    protected FacilityControlEvent(JSONObject object) {
        super(object);
        this.zoneID = object.getString("zone_id");
        this.durationHeld = object.getString("duration_held");
        this.facilityID = object.getString("facility_id");
        this.outfitID = object.getString("outfit_id");
        this.newFaction = CensusAPI.FACTION_MAP.get(object.getString("new_faction_id"));
        this.oldFaction = CensusAPI.FACTION_MAP.get(object.getString("old_faction_id"));
    }

    @Override
    public String toString() {
        return EVENT_NAME + " - outfit: " + outfitID + " facility: " + facilityID + " duration: " + durationHeld + " zone: " + zoneID + " old faction: " + oldFaction + " new faction: " + newFaction;
    }
}
