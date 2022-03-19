//Zacharias Thorell

package lib;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Scanner;


/**
 * Values related to the Census API and Planetside 2.
 */
public class CensusAPI {
    private static String SERVICE_ID = null;
    private static final String NAMESPACE = "ps2:v2/";
    private static final String BASE_URL = "https://census.daybreakgames.com/";

    private static final String AND = "&";
    private static final String FILTER_PREFIX = AND + "c:";

    public static final String SHOW_FILTER = FILTER_PREFIX + "show=";

    private static final String GET = "get/";

    public static final Map<String, Faction> FACTION_MAP = Map.of("1", Faction.TR, "2", Faction.NC, "3", Faction.VS);

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
     * @return the base url for a get_character request.
     */
    public static String getGetCharacterURL() {
        return BASE_URL + SERVICE_ID + GET + NAMESPACE + "character/?name.first_lower=";
    }

    /**
     * @return the base url for a get characters_online_status request.
     */
    public static String getGetCharacterOnlineStatusURL() {
        return BASE_URL + GET + NAMESPACE + "characters_online_status/?character_id=";
    }

    /**
     * Returns a JSONObject for a query.
     * @param query Query to API.
     * @param key JSONArray of interest in response.
     * @return JSONObject containing query response.
     * @throws IOException if malformed URL or error in response.
     */
    public static JSONObject getResponseObject(String query, String key) throws IOException {
        URLConnection connection = new URL(query).openConnection();
        InputStream response = connection.getInputStream();

        Scanner scanner = new Scanner(response);
        String responseBody = scanner.useDelimiter("\\A").next();
        return new JSONObject(responseBody).getJSONArray(key).getJSONObject(0);
    }
}
