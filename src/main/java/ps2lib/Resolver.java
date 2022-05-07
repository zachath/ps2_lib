//Zacharias Thorell

package ps2lib;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * Resolves the specified id of a type and returns the corresponding value.
 * With types of low counts (such as faction) are hardcoded and send to query to the API.
 */
public class Resolver {
    private static final Map<String, Faction> FACTION_MAP = Map.of("1", Faction.VS, "2", Faction.NC, "3", Faction.TR, "4", Faction.NSO);
    private static final Map<String, String> WORLD_MAP = Map.of("13", "Cobalt", "10", "Miller", "1", "Connery", "40", "SolTech", "19", "Jaeger", "17", "Emerald", "24", "Apex", "25", "Briggs");

    /**
     * @param id The id of the faction (1 - 4).
     * @return The faction of the specified id, null if none.
     */
    public static Faction resolveFaction(String id) {
        return FACTION_MAP.get(id);
    }

    /**
     * @param id The id of the world.
     * @return The faction of the specified id, null if none.
     */
    public static String resolveWorld(String id) {
        return WORLD_MAP.get(id);
    }

    public static String resolveItemName(String id) {
        try {
            String query = CensusAPI.getItemName(id);
            return CensusAPI.getResponseObject(query, "item_list").getJSONObject("name").getString("en");
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid id");
        }
    }

    /**
     * Resolve player name without creating an entire character.
     * @param id character id.
     * @return name of player.
     */
    public static String resolvePlayerName(String id) {
        try {
            String query = CensusAPI.getCharacterFromIdURL(id, CensusAPI.SHOW_FILTER + "name");
            return CensusAPI.getResponseObject(query, "character_list").getJSONObject("name").getString("first");
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid id");
        }
    }
}
