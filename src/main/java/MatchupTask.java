//Zacharias Thorell

import lib.CensusAPI;
import lib.IllegalServiceIdException;
import lib.PS2Player;
import lib.PS2PlayerFactory;
import lib.event.Event;
import lib.event.EventFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Retrieves the 1000 (API limit) most recent DEATH events of player1 and creates a matchup with player2
 */
public class MatchupTask implements Runnable {
    private final String playername1, playername2;

    public MatchupTask(String playername1, String playername2) {
        this.playername1 = playername1;
        this.playername2 = playername2;
    }

    @Override
    public void run() {
        PS2Player player1 = null;
        PS2Player player2;

        try {
            player1 = PS2PlayerFactory.createPlayer(playername1);
            player2 = PS2PlayerFactory.createPlayer(playername1);
        } catch (IllegalServiceIdException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("A player could not be created");
            return;
        }

        //Get 1000 latest deaths of player1
        List<Event> eventList = new ArrayList<>();
        try {
            eventList = EventFactory.getDeathEvents(player1.getId());
        } catch (IllegalServiceIdException e) {
            e.printStackTrace();
        }

        System.out.println(eventList.size());
    }
}
