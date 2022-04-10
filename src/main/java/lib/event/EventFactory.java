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
    public static CharacterEvent createEvent(JSONObject object) {
        String event_name = object.getString("event_name");

        return switch (event_name) {
            case "PlayerLogin" -> new PlayerLoginCharacterEvent(object);
            case "PlayerLogout" -> new PlayerLogoutCharacterEvent(object);
            case "Death" -> new DeathCharacterEvent(object);
            case "AchievementEarned" -> new AchievementEarnedCharacterEvent(object);
            case "BattleRankUp" -> new BattleRankUpCharacterEvent(object);
            case "FacilityControl" -> new FacilityControlCharacterEvent(object);
            case "GainExperience" -> new GainExperienceCharacterEvent(object);
            case "ItemAdded" -> new ItemAdded(object);
            case "PlayerFacilityCapture" -> new PlayerFacilityCaptureCharacterEvent(object);
            case "PlayerFacilityDefend" -> new PlayerFacilityDefendCharacterEvent(object);
            case "SkillAdded" -> new SkillAddedCharacterEvent(object);
            case "VehicleDestroy" -> new VehicleDestroyCharacterEvent(object);
            default -> null;
        };
    }

    /**
     * @param id id of the character to get death events from-
     * @return A list of death events.
     * @throws IllegalServiceIdException if the service id is not set.
     */
    public static List<DeathCharacterEvent> getDeathEvents(String id) throws IllegalServiceIdException {
        if (!CensusAPI.serviceIDIsSet())
            throw new IllegalServiceIdException();

        List<DeathCharacterEvent> deaths = new ArrayList<>();
        String query = CensusAPI.getCharacterEventList(id, CensusAPI.GET_MAX_LIMIT, "DEATH");

        JSONArray responseArray = new JSONArray();
        try {
            responseArray = CensusAPI.getResponseArray(query, "characters_event_list");
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Object o : responseArray) {
            JSONObject jsonObject = (JSONObject) o;
            deaths.add(new DeathCharacterEvent(jsonObject));
        }

        return deaths;
    }
}
