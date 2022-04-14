//Zacharias Thorell

package lib.event;

import org.json.JSONObject;

/**
 * An event generated when a continent is unlocked.
 */
public class ContinentUnlockEvent extends Event {
    public final static String EVENT_NAME = "ContinentUnlock";

    public final String zoneID, triggeringFaction, previousFaction, vsPopulation, ncPopulation, trPopulation;

    protected ContinentUnlockEvent(String eventName, String world_id, String timestamp, String zoneID, String triggeringFaction, String previousFaction, String vsPopulation, String ncPopulation, String trPopulation) {
        super(eventName, world_id, timestamp);
        this.zoneID = zoneID;
        this.triggeringFaction = triggeringFaction;
        this.previousFaction = previousFaction;
        this.vsPopulation = vsPopulation;
        this.ncPopulation = ncPopulation;
        this.trPopulation = trPopulation;
    }

    protected ContinentUnlockEvent(JSONObject object) {
        super(object);
        this.zoneID = object.getString("zoneID");
        this.triggeringFaction = object.getString("triggering_faction");
        this.previousFaction = object.getString("previous_faction");
        this.vsPopulation = object.getString("vs_population");
        this.ncPopulation = object.getString("nc_population");
        this.trPopulation = object.getString("tr_population");
    }

    @Override
    public String toString() {
        return "At " + timestamp + " on world " + world_id + triggeringFaction + " unlocked continent: " + zoneID + ". previous faction: " + previousFaction + " Population - vs: "  + vsPopulation + " nc: " + ncPopulation + " tr: " + trPopulation;
    }
}

