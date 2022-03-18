//Zacharias Thorell

import java.util.Map;

/**
 * Values related to the Census API and Planetside 2.
 */
public class CensusAPIUtil {
    public static final String SERVICE_ID = "s:TorranPS2Util/";
    public static final String NAMESPACE = "ps2:v2/";
    public static final String BASE_URL = "https://census.daybreakgames.com/" + SERVICE_ID;

    public static final String AND = "&";
    public static final String FILTER_PREFIX = AND + "c:";
    public static final String SHOW_FILTER = FILTER_PREFIX + "show=";

    public static final String GET = "get/";

    public static final String GET_CHARACTER = BASE_URL + GET + NAMESPACE + "character/?name.first_lower=";
    public static final String GET_CHARACTER_ONLINE_STATUS = BASE_URL + GET + NAMESPACE + "characters_online_status/?character_id=";

    public static final Map<Integer, String> FACTION_MAP = Map.of(1, "TR", 2, "NC", 3, "VS");
    public static final Map<String, String> FACTION_COLOUR_MAP = Map.of(FACTION_MAP.get(1), Util.ANSI_RED,FACTION_MAP.get(2), Util.ANSI_BLUE, FACTION_MAP.get(3), Util.ANSI_PURPLE);
}