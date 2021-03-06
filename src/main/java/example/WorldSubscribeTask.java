//Zacharias Thorell

package example;

import ps2lib.CensusAPI;
import ps2lib.IllegalServiceIdException;
import ps2lib.event.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static ps2lib.CensusAPI.formatWorldSubscribePayLoad;

/**
 * An example of subscribing to world events.
 */
public class WorldSubscribeTask implements Runnable {
    public static void main(String[] args) throws IllegalServiceIdException, URISyntaxException {
        CensusAPI.setServiceId("TorranPS2Util");
        new WorldSubscribeTask(List.of("10", "13"), List.of(FacilityControlEvent.EVENT_NAME, ContinentUnlockEvent.EVENT_NAME, ContinentLockEvent.EVENT_NAME)).run();
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
            CLIENT.send(formatWorldSubscribePayLoad(worlds, events));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}