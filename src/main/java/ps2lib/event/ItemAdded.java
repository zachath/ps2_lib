//Zacharias Thorell

package ps2lib.event;

import org.json.JSONObject;

/**
 * An event generated when a character adds an item to a loadout.
 */
public class ItemAdded extends CharacterEvent {
    public static final String EVENT_NAME = "ItemAdded";

    public final String context, itemCount, itemID;

    protected ItemAdded(String world_id, String timestamp, String player, String context, String itemCount, String itemID) {
        super(EVENT_NAME, world_id, timestamp, player);
        this.context = context;
        this.itemCount = itemCount;
        this.itemID = itemID;
    }

    protected ItemAdded(JSONObject object) {
        super(object);
        this.context = object.getString("context");
        this.itemCount = object.getString("item_count");
        this.itemID = object.getString("item_id");
    }

    @Override
    public String toString() {
        return characterID + " added item " + itemID;
    }
}
