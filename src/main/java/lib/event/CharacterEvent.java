//Zacharias Thorell

package lib.event;

import org.json.JSONObject;

/**
 * Event specific to a specific character.
 */
public abstract class CharacterEvent extends Event {
    public final String characterID;

    protected CharacterEvent(String eventName, String player) {
        super(eventName);
        this.characterID = player;
    }

    protected CharacterEvent(JSONObject object) {
        super(object);
        this.characterID = object.getString("character_id");
    }

    @Override
    abstract public String toString();
}
