//Zacharias Thorell

package example;

import ps2lib.*;

/**
 * The task of looking up a player
 * Should be run in another thread in case of long response time.
 */
public class LookupTask implements Runnable {
    public static void main(String[] args) {
        //Setting the service id, which is required.
        CensusAPI.setServiceId("TorranPS2Util");

        new LookupTask("SgtTorranVS").run();
    }

    private final String name;
    public LookupTask(String name) {
        this.name = name;
    }

    /**
     * Creates a player and prints the information to screen.
     */
    @Override
    public void run() {
        double start = System.nanoTime();

        PS2Player player;
        try {
            player = PS2PlayerFactory.createPlayerFromName(name);
        } catch (IllegalServiceIdException e) {
            e.printStackTrace();
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Player could not be created");
            return;
        }

        String builder = "Name: " + player.getName() + "\t" +
                "Faction: " + player.getFaction() + "\n" +
                "Online Status: " + player.isOnline() + "\t" +
                "Battle Rank: " + player.getBattleRank() + "\n" +
                "Logins: " + player.getLogins() + "\n" + "\n" +
                "Minutes played: " + player.getMinutesPlayed() + "\t" +
                "Hours played: " + player.getHoursPlayed() + "\n" + "\n" +
                "Total Certs Accumulated: " + Util.printInColour(Integer.toString(player.getTotalCerts()), Util.ANSI_YELLOW) + "\t" +
                "Available Certs: " + Util.printInColour(Integer.toString(player.getAvailableCerts()), Util.ANSI_YELLOW) + "\n" + "\n" +
                "Created: " + player.getCreationDate() + "\n" + "\n" +
                "Finished in " + Math.round(((System.nanoTime() - start) / 1.0E9)) + " second(s)";

        System.out.println(builder);
    }
}