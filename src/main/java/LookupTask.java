//Zacharias Thorell

import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * The task of looking up a player
 */
public class LookupTask implements Runnable {
    private static final String FILTERS = CensusAPIUtil.SHOW_FILTER + "character_id,name,faction_id,times,certs,battle_rank";

    private final String player;
    public LookupTask(String player) {
        this.player = player;
    }

    @Override
    public void run() {
        String query = CensusAPIUtil.GET_CHARACTER + player + FILTERS;
        System.out.println("Querying " + query);
        double start = System.nanoTime();
        try {
            URLConnection connection = new URL(query).openConnection();
            InputStream response = connection.getInputStream();

            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();

            JSONObject responseObject = null;
            try {
                responseObject = new JSONObject(responseBody).getJSONArray("character_list").getJSONObject(0);
            } catch (JSONException e) { //Catch if no user found.
                System.out.println("Player \"" + player + "\" not found");
                return;
            }

            String playerId = responseObject.getString("character_id");
            String isOnlineQuery = CensusAPIUtil.GET_CHARACTER_ONLINE_STATUS + playerId;

            connection = new URL(isOnlineQuery).openConnection();
            response = connection.getInputStream();
            scanner = new Scanner(response);

            String onlineResponse = scanner.useDelimiter("\\A").next();
            JSONObject responseObjectID;
            try {
                responseObjectID = new JSONObject(onlineResponse).getJSONArray("characters_online_status_list").getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }

            String idResponse = responseObjectID.getString("online_status");
            boolean isOnline = idResponse.equals("1");

            int minutesPlayed = Integer.parseInt((String) responseObject.getJSONObject("times").get("minutes_played"));
            double hoursPlayed = (double) minutesPlayed / 60;

            int totalCerts = Integer.parseInt((String) responseObject.getJSONObject("certs").get("earned_points"))+ Integer.parseInt((String) responseObject.getJSONObject("certs").get("gifted_points"));
            int availableCerts = Integer.parseInt((String) responseObject.getJSONObject("certs").get("available_points"));

            String faction = CensusAPIUtil.FACTION_MAP.get(Integer.parseInt((String) responseObject.get("faction_id")));

            //Constructing a String to print from collected information.
            //Builder not needed but looks cleaner than massive String and more concise than many prints.
            StringBuilder builder = new StringBuilder();
            builder.append("Name: ").append(responseObject.getJSONObject("name").get("first")).append("\t");
            builder.append("Faction: ").append(Util.printInColour(faction, CensusAPIUtil.FACTION_COLOUR_MAP.get(faction))).append("\n");
            builder.append("Online Status: ").append(isOnline).append("\n");

            builder.append("Logins: ").append(responseObject.getJSONObject("times").get("login_count")).append("\n").append("\n");

            builder.append("Minutes played: ").append(minutesPlayed).append("\t");
            builder.append("Hours played: ").append(hoursPlayed).append("\n").append("\n");

            builder.append("Total Certs Accumulated: ").append(Util.printInColour(Integer.toString(totalCerts), Util.ANSI_YELLOW)).append("\t");
            builder.append("Available Certs: ").append(Util.printInColour(Integer.toString(availableCerts), Util.ANSI_YELLOW)).append("\n").append("\n");

            builder.append("Created: ").append(responseObject.getJSONObject("times").get("creation_date")).append("\n").append("\n");

            builder.append("Finished in ").append(Math.round(((System.nanoTime() - start) / 1.0E9))).append(" second(s)");

            System.out.println(builder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}