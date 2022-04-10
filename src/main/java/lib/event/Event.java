//Zacharias

package lib.event;

import org.json.JSONObject;

/**
 * Any event.
 */
public abstract class Event {
    public final String eventName, world_id, timestamp;

    protected Event(String eventName, String world_id, String timestamp) {
        this.eventName = eventName;
        this.world_id = world_id;
        this.timestamp = timestamp;
    }

    protected Event(JSONObject object) {
        this.eventName = object.getString("event_name");
        this.world_id = object.getString("world_id");
        this.timestamp = object.getString("timestamp");
    }
}
