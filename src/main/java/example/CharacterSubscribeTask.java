//Zacharias Thorell

package example;

import ps2lib.CensusAPI;
import ps2lib.IllegalServiceIdException;
import ps2lib.PS2PlayerFactory;
import ps2lib.event.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static ps2lib.CensusAPI.formatCharacterSubscribePayLoad;

/**
 * An example of subscribing to different character events for a specific character.
 */
public class CharacterSubscribeTask implements Runnable {
    public static void main(String[] args) throws IllegalServiceIdException, URISyntaxException {
        //Setting the service id, which is required.
        CensusAPI.setServiceId("TorranPS2Util");

        new CharacterSubscribeTask(List.of("SgtTorran"), List.of(BattleRankUpCharacterEvent.EVENT_NAME, ItemAdded.EVENT_NAME, PlayerFacilityDefendCharacterEvent.EVENT_NAME)).run();
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
            CLIENT.send(formatCharacterSubscribePayLoad(characterIds, events));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}