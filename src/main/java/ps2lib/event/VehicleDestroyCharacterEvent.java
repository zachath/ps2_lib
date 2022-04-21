//Zacharias Thorell

package ps2lib.event;

import org.json.JSONObject;

/**
 * An event generated when a character destroys a vehicle.
 */
public class VehicleDestroyCharacterEvent extends CharacterEvent {
    public static final String EVENT_NAME = "VehicleDestroy";

    public final String attackerId, vehicleID;

    protected VehicleDestroyCharacterEvent(String world_id, String timestamp, String player, String attackerId, String vehicleID) {
        super(EVENT_NAME, world_id, timestamp, player);
        this.attackerId = attackerId;
        this.vehicleID = vehicleID;
    }

    protected VehicleDestroyCharacterEvent(JSONObject object) {
        super(object);
        this.attackerId = object.getString("attacker_id");
        this.vehicleID = object.getString("vehicle_id");
    }

    @Override
    public String toString() {
        return attackerId + " destroyed the vehicle (" + vehicleID + ") of " + characterID;
    }
}
