//Zacharias Thorell

package lib.event;

import lib.CensusAPI;
import lib.IllegalServiceIdException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates events using the supplied methods.
 */
public class EventFactory {
    /**
     * Creates an event depending on what kind of event is received.
     * @param object content of payload object.
     * @return an event.
     */
    public static Event createEvent(JSONObject object) {
        String event_name = object.getString("event_name");

        return switch (event_name) {
            case "PlayerLogin" -> new PlayerLoginEvent(object);
            case "PlayerLogout" -> new PlayerLogoutEvent(object);
            case "Death" -> new DeathEvent(object);
            default -> null;
        };
    }

    /**
     * @param id id of the character to get death events from-
     * @return A list of death events.
     * @throws IllegalServiceIdException if the service id is not set.
     */
    public static List<DeathEvent> getDeathEvents(String id) throws IllegalServiceIdException {
        if (!CensusAPI.serviceIDIsSet())
            throw new IllegalServiceIdException();

        List<DeathEvent> deaths = new ArrayList<>();
        String query = CensusAPI.getCharacterEventList(id, CensusAPI.GET_MAX_LIMIT, "DEATH");

        JSONArray responseArray = new JSONArray();
        try {
            responseArray = CensusAPI.getResponseArray(query, "characters_event_list");
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Object o : responseArray) {
            JSONObject jsonObject = (JSONObject) o;
            deaths.add(new DeathEvent(jsonObject));
        }

        return deaths;
    }
}
