//Zacharias Thorell

package lib;

import lib.event.Event;
import lib.event.EventFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Values related to the Census API and Planetside 2.
 */
public class CensusAPI {
    private static String SERVICE_ID = null;
    private static final String NAMESPACE = "ps2:v2/";
    private static final String BASE_URL = "https://census.daybreakgames.com/";

    //Used to open connection to websocket.
    private static final String EVENT_STREAMING_URL = "wss://push.planetside2.com/streaming?environment=ps2&service-id=";

    //Valid character events that it is possible to subscribe to.
    //WARNING: GainExperience occurs VERY frequently (unless player is completely incompetent).
    public static final List<String> VALID_SUBSCRIBE_CHARACTER_EVENTS = Arrays.asList("AchievementEarned", "BattleRankUp", "Death", "FacilityControl", "GainExperience", "ItemAdded", "PlayerFacilityCapture", "PlayerFacilityDefend", "PlayerLogin", "PlayerLogout", "SkillAdded", "VehicleDestroy");

    //Valid world-level events that it is possible to subscribe to.
    public static final List<String> VALID_SUBSCRIBE_WORLD_EVENTS = Arrays.asList("ContinentLock", "ContinentUnlock", "FacilityControl", "MetagameEvent");

    //Unsubscribe from all events.
    public static final String CLEAR_SUBSCRIBE = "{\"action\":\"clearSubscribe\",\"all\":\"true\",\"service\":\"event\"}";

    private static final String AND = "&";
    private static final String COMMAND_PREFIX = AND + "c:";

    public static final String SHOW_FILTER = COMMAND_PREFIX + "show=";

    private static final String GET = "get/";
    public static final int GET_MAX_LIMIT = 1000;

    public static final Map<String, Faction> FACTION_MAP = Map.of("1", Faction.VS, "2", Faction.NC, "3", Faction.TR, "4", Faction.NSO);

    /**
     * To use the api a Service ID is needed which is requested at: http://census.daybreakgames.com/
     * @param serviceID Service ID.
     */
    public static void setServiceId(String serviceID) {
        SERVICE_ID = "s:" + serviceID + "/";
    }

    /**
     * Checks whether the Service ID has been set.
     * @return if the service id has been set.
     */
    public static boolean serviceIDIsSet() {
        return SERVICE_ID != null;
    }

    /**
     * @return the base url for a get_character request based on name.
     */
    public static String getCharacterFromNameURL(String name, String filters) {
        return BASE_URL + SERVICE_ID + GET + NAMESPACE + "character/?name.first_lower=" + name + filters;
    }

    /**
     * @return the base url for a get_character request based on id.
     */
    public static String getCharacterFromIdURL(String id, String filters) {
        return BASE_URL + SERVICE_ID + GET + NAMESPACE + "character/?character_id=" + id + filters;
    }

    /**
     * @return the base url for a get characters_online_status request.
     */
    public static String getGetCharacterOnlineStatusURL(String playerId) {
        return BASE_URL + SERVICE_ID + GET + NAMESPACE + "characters_online_status/?character_id=" + playerId;
    }

    /**
     * @param characterId id of character.
     * @param limit how many responses, max 1000.
     * @param type event type.
     * @return url for specified query.
     */
    public static String getCharacterEventList(String characterId, int limit, String type) {
        if (limit < 0 || limit > 1000)
            return null;
        return BASE_URL + SERVICE_ID + GET + NAMESPACE + "characters_event/?character_id=" + characterId + COMMAND_PREFIX + "limit=" + limit + AND + "type=" + type;
    }

    /**
     * @return the url for the event streaming.
     */
    public static String getEventStreamingUrl() {
        return EVENT_STREAMING_URL + SERVICE_ID.substring(0, SERVICE_ID.length() - 1); //To remove the final "/" from the Service ID that is used otherwise.
    }

    /**
     * Subscribe to single event for single character,
     * Example subscribe.
     * {"service":"event","action":"subscribe","characters":["5428926375528770321"],"eventNames":["PlayerLogin", "PlayerLogout"]}
     * @param characterID id of character.
     * @param event name of event.
     * @return JSON string for the specified subscribe.
     */
    public static String getCharacterSubscribeJSONPayload(String characterID, String event) {
        return "{\"service\":\"event\",\"action\":\"subscribe\",\"characters\":[\"" + characterID + "\"], \"eventNames:\"[\"" + event + "\"]}";
    }

    /**
     * Returns a JSONObject for a query.
     * @param query Query to API.
     * @param key JSONArray of interest in response.
     * @return JSONObject containing query response.
     * @throws IOException if malformed URL or error in response.
     */
    public static JSONObject getResponseObject(String query, String key) throws IOException {
        //Debug print
        System.out.println(query);

        URLConnection connection = new URL(query).openConnection();
        InputStream response = connection.getInputStream();

        Scanner scanner = new Scanner(response);
        String responseBody = scanner.useDelimiter("\\A").next();
        return new JSONObject(responseBody).getJSONArray(key).getJSONObject(0);
    }

    /**
     * Returns a JSONArray for a query.
     * @param query Query to API.
     * @param key JSONArray of interest in response.
     * @return JSONArray containing query response.
     * @throws IOException if malformed URL or error in response.
     */
    public static JSONArray getResponseArray(String query, String key) throws IOException {
        //Debug print
        System.out.println(query);

        URLConnection connection = new URL(query).openConnection();
        InputStream response = connection.getInputStream();

        Scanner scanner = new Scanner(response);
        String responseBody = scanner.useDelimiter("\\A").next();
        return new JSONObject(responseBody).getJSONArray(key);
    }

    /**
     * Creates a JSONObject for every response which contains a payload (response to query).
     * @param response the JSON response sent by the API websocket.
     */
    public static Event handleLiveStreamingResponse(String response) {
        JSONObject object = new JSONObject(response);

        try {
            JSONObject payload = object.getJSONObject("payload");
            return EventFactory.createEvent(payload);
        } catch (JSONException ignored) {return null;} //Ignore messages from the API that is not in response to subscription.
    }

    /**
     * Formats the subscription payload.
     * @param characterIds the characters to subscribe to.
     * @param events the events to subscribe to.
     * @return JSONObject as string.
     */
    public static String formatPayLoadCharacter(Collection<String> characterIds, Collection<String> events) {
        JSONObject toBeSent = new JSONObject();

        toBeSent.put("eventNames", new JSONArray(events));
        toBeSent.put("characters", new JSONArray(characterIds));
        toBeSent.put("action","subscribe");
        toBeSent.put("service", "event");

        return toBeSent.toString();
    }

    /**
     * Formats the subscription payload.
     * @param worlds the worlds to subscribe to.
     * @param events the events to subscribe to.
     * @return JSONObject as string.
     */
    public static String formatPayLoadWorld(Collection<String> worlds, Collection<String> events) {
        JSONObject toBeSent = new JSONObject();

        toBeSent.put("eventNames", new JSONArray(events));
        toBeSent.put("worlds", new JSONArray(worlds));
        toBeSent.put("action","subscribe");
        toBeSent.put("service", "event");

        return toBeSent.toString();
    }
}
