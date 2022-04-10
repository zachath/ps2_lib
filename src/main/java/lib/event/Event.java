//Zacharias

package lib.event;

import org.json.JSONObject;

/**
 * Any event.
 */
public abstract class Event {
    protected final String eventName;

    protected Event(String eventName) {
        this.eventName = eventName;
    }

    protected Event(JSONObject object) {
        this.eventName = object.getString("event_name");
    }
}
