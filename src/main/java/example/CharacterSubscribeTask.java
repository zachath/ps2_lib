//Zacharias Thorell

package example;

import lib.CensusAPI;
import lib.IllegalServiceIdException;
import lib.PS2PlayerFactory;
import lib.event.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static lib.CensusAPI.formatPayLoadCharacter;

/**
 * An example of subscribing to different character events for a specific character.
 */
public class CharacterSubscribeTask implements Runnable {
    public static void main(String[] args) throws IllegalServiceIdException, URISyntaxException {
        //Setting the service id, which is required.
        CensusAPI.setServiceId("TorranPS2Util");

        new CharacterSubscribeTask(List.of("SgtTorranVS"), List.of(PlayerLoginCharacterEvent.EVENT_NAME, PlayerLogoutCharacterEvent.EVENT_NAME, DeathCharacterEvent.EVENT_NAME)).run();
    }

    private static LiveStreamingClient CLIENT;

    private final List<String> characterIds = new ArrayList<>();
    private final List<String> events = new ArrayList<>();

    public CharacterSubscribeTask(Collection<String> characterNames, Collection<String> events) throws URISyntaxException, IllegalServiceIdException {
        if (CLIENT == null) {
            CLIENT = new LiveStreamingClient(new URI(CensusAPI.getEventStreamingUrl()));
        }

        for (String characterName : characterNames) {
            characterIds.add(PS2PlayerFactory.createPlayerFromName(characterName).getId());
        }

        this.events.addAll(events);
    }

    public CharacterSubscribeTask(String characterName, String event) throws URISyntaxException, IllegalServiceIdException {
        this(List.of(characterName), List.of(event));
    }

    @Override
    public void run() {
        try {
            CLIENT.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CLIENT.send(formatPayLoadCharacter(characterIds, events));
    }
}