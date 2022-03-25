//Zacharias Thorell

package lib.event;

import lib.CensusAPI;
import lib.IllegalServiceIdException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventFactory {
    public static List<Event> getDeathEvents(String id) throws IllegalServiceIdException {
        if (!CensusAPI.serviceIDIsSet())
            throw new IllegalServiceIdException();

        List<Event> deaths = new ArrayList<>();
        String query = CensusAPI.getCharacterEventList(id, CensusAPI.GET_MAX_LIMIT, "DEATH");

        JSONArray responseArray = new JSONArray();
        try {
            responseArray = CensusAPI.getResponseArray(query, "characters_event_list");
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Object o : responseArray) {
            JSONObject jsonObject = (JSONObject) o;
            deaths.add(new DeathEvent(id, jsonObject.getString("attacker_character_id"), jsonObject.getString("is_headshot"), jsonObject.getString("is_critical")));
        }

        return deaths;
    }
}
