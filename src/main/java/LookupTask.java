//Zacharias Thorell

import lib.IllegalServiceIdException;
import lib.PS2Player;
import lib.PS2PlayerFactory;
import lib.Util;

/**
 * The task of looking up a player
 * Should be run in another thread in case of long response time.
 */
public class LookupTask implements Runnable {

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
            player = PS2PlayerFactory.createPlayer(name);
        } catch (IllegalServiceIdException e) {
            e.printStackTrace();
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