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
            case PlayerLoginCharacterEvent.EVENT_NAME -> new PlayerLoginCharacterEvent(object);
            case PlayerLogoutCharacterEvent.EVENT_NAME -> new PlayerLogoutCharacterEvent(object);
            case DeathCharacterEvent.EVENT_NAME -> new DeathCharacterEvent(object);
            case AchievementEarnedCharacterEvent.EVENT_NAME-> new AchievementEarnedCharacterEvent(object);
            case BattleRankUpCharacterEvent.EVENT_NAME -> new BattleRankUpCharacterEvent(object);
            case FacilityControlCharacterEvent.EVENT_NAME -> new FacilityControlCharacterEvent(object);
            case GainExperienceCharacterEvent.EVENT_NAME -> new GainExperienceCharacterEvent(object);
            case ItemAdded.EVENT_NAME -> new ItemAdded(object);
            case PlayerFacilityCaptureCharacterEvent.EVENT_NAME -> new PlayerFacilityCaptureCharacterEvent(object);
            case PlayerFacilityDefendCharacterEvent.EVENT_NAME -> new PlayerFacilityDefendCharacterEvent(object);
            case SkillAddedCharacterEvent.EVENT_NAME -> new SkillAddedCharacterEvent(object);
            case VehicleDestroyCharacterEvent.EVENT_NAME -> new VehicleDestroyCharacterEvent(object);
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
