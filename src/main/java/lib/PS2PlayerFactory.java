//Zacharias Thorell

package lib;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A class creating PS2Player instances.
 */
public class PS2PlayerFactory {
    private static final String FILTERS = CensusAPI.SHOW_FILTER + "character_id,name,faction_id,times,certs,battle_rank";

    /**
     * Creates a PS2Player instance if existing.
     * @param name Name of the player.
     * @return a PS2Player.
     * @throws IllegalServiceIdException if the service id is not set.
     * @throws IllegalArgumentException if the player could not be found.
     */
    public static PS2Player createPlayer(String name) throws IllegalServiceIdException, IllegalArgumentException {
        if (!CensusAPI.serviceIDIsSet())
            throw new IllegalServiceIdException();

        String query = CensusAPI.getGetCharacterURL() + name + FILTERS;
        try {

            JSONObject responseObject;
            try {
                responseObject = CensusAPI.getResponseObject(query, "character_list");
            } catch (JSONException e) { //Catch if no user found.
                throw new IllegalArgumentException("Player \"" + name + "\" not found.");
            }

            String playerId = responseObject.getString("character_id");
            String isOnlineQuery = CensusAPI.getGetCharacterOnlineStatusURL() + playerId;

            JSONObject responseObjectID = null;
            try {
                responseObjectID = CensusAPI.getResponseObject(isOnlineQuery, "characters_online_status_list");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String idResponse = responseObjectID.getString("online_status");
            boolean isOnline = idResponse.equals("1");

            int battleRank = Integer.parseInt(responseObject.getJSONObject("battle_rank").getString("value"));
            int minutesPlayed = Integer.parseInt((String) responseObject.getJSONObject("times").get("minutes_played"));
            int hoursPlayed = minutesPlayed / 60;
            int totalCerts = Integer.parseInt((String) responseObject.getJSONObject("certs").get("earned_points")) + Integer.parseInt((String) responseObject.getJSONObject("certs").get("gifted_points"));
            int availableCerts = Integer.parseInt((String) responseObject.getJSONObject("certs").get("available_points"));
            Faction faction = CensusAPI.FACTION_MAP.get(responseObject.getString("faction_id"));
            int logins = Integer.parseInt(responseObject.getJSONObject("times").getString("login_count"));
            String created = responseObject.getJSONObject("times").getString("creation_date");

            return new PS2Player(playerId, name, faction, created, isOnline, logins, battleRank, minutesPlayed, hoursPlayed, totalCerts, availableCerts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Failed to create PS2Player");
    }
}
