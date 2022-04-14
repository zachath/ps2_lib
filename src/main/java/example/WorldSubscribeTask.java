//Zacharias Thorell

package example;

import lib.CensusAPI;
import lib.IllegalServiceIdException;
import lib.event.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static lib.CensusAPI.formatPayLoadWorld;

/**
 * An example of subscribing to world events.
 */
public class WorldSubscribeTask implements Runnable {
    public static void main(String[] args) throws IllegalServiceIdException, URISyntaxException {
        CensusAPI.setServiceId("TorranPS2Util");
        new WorldSubscribeTask(List.of("10, 13"), List.of(FacilityControlEvent.EVENT_NAME, ContinentUnlockEvent.EVENT_NAME, ContinentLockEvent.EVENT_NAME)).run();
    }

    private static LiveStreamingClient CLIENT;

    private final List<String> worlds = new ArrayList<>();
    private final List<String> events = new ArrayList<>();

    public WorldSubscribeTask(Collection<String> worlds, Collection<String> events) throws URISyntaxException, IllegalServiceIdException {
        if (CLIENT == null) {
            CLIENT = new LiveStreamingClient(new URI(CensusAPI.getEventStreamingUrl()));
        }

        this.worlds.addAll(worlds);
        this.events.addAll(events);
    }

    @Override
    public void run() {
        try {
            CLIENT.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CLIENT.send(formatPayLoadWorld(worlds, events));
    }
}