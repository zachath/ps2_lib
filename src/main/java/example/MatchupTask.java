//Zacharias Thorell

package example;

import ps2lib.*;
import ps2lib.event.DeathCharacterEvent;
import ps2lib.event.EventFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Retrieves the 1000 (API limit) most recent DEATH events of player1 and creates a matchup with player2
 */
public class MatchupTask implements Runnable {
    public static void main(String[] args) {
        //Setting the service id, which is required.
        CensusAPI.setServiceId("TorranPS2Util");

        //Matchup betwen SgtTorran (me) and xxArtikxx (he kills me so often, how does he always find me in a game this big?!)
        new MatchupTask("SgtTorran", "xxArtikxx").run();
    }

    private final String playerName1, playerName2;

    public MatchupTask(String playerName1, String playerName2) {
        this.playerName1 = playerName1;
        this.playerName2 = playerName2;
    }

    @Override
    public void run() {
        PS2Player player1 = null;
        PS2Player player2 = null;

        try {
            player1 = PS2PlayerFactory.createPlayerFromName(playerName1);
            player2 = PS2PlayerFactory.createPlayerFromName(playerName2);
        } catch (IllegalServiceIdException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("A player could not be created");
            return;
        }

        //Get 1000 latest deaths of the players
        List<DeathCharacterEvent> player1Deaths = new ArrayList<>();
        List<DeathCharacterEvent> player2Deaths = new ArrayList<>();
        try {
            player1Deaths = EventFactory.getDeathEvents(player1.getId());
            player2Deaths = EventFactory.getDeathEvents(player2.getId());
        } catch (IllegalServiceIdException e) {
            e.printStackTrace();
        }

        final PS2Player finalPlayer1 = player1;
        final PS2Player finalPlayer2 = player2;

        int player1DeathsByPlayer2 = player1Deaths.stream().filter(e -> e.attackerId.equals(finalPlayer2.getId())).toList().size();
        int player2DeathsByPlayer1 = player2Deaths.stream().filter(e -> e.attackerId.equals(finalPlayer1.getId())).toList().size();

        String out = player1.getName() +
                " " +
                "(" +
                player1.getFaction() +
                ") " +
                player2DeathsByPlayer1 +
                " vs " +
                player2.getName() +
                " " +
                "(" +
                player2.getFaction() +
                ") " +
                player1DeathsByPlayer2;

        System.out.println(out);
    }
}
