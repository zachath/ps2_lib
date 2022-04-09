//Zacharias Thorell

//Dummy: {"service":"event","action":"help"}

import lib.CensusAPI;
import lib.IllegalServiceIdException;
import lib.PS2PlayerFactory;
import lib.event.Event;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SubscribeTask implements Runnable {
    private static LiveStreamingClient CLIENT;
    private static boolean CONNECTED;

    private final String characterId;
    private final List<String> events = new ArrayList<>();

    public SubscribeTask(String characterName, Collection<String> events) throws URISyntaxException, IllegalServiceIdException {
        if (CLIENT == null) {
            CLIENT = new LiveStreamingClient(new URI(CensusAPI.getEventStreamingUrl()));
        }
        this.characterId = PS2PlayerFactory.createPlayer(characterName).getId();
        this.events.addAll(events);
    }

    public SubscribeTask(String characterName, String event) throws URISyntaxException, IllegalServiceIdException {
        this(characterName, List.of(event));
    }

    @Override
    public void run() {
        if (!CONNECTED) {
            try {
                CLIENT.connectBlocking();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            CONNECTED = true;
        }

        CLIENT.send(formatPayLoad(characterId, events));
    }

    //TODO: move to API.
    private static String formatPayLoad(String characterId, Collection<String> events) {
        JSONObject toBeSent = new JSONObject();

        toBeSent.put("eventNames", new JSONArray(events));
        toBeSent.put("characters", new JSONArray().put(characterId));
        toBeSent.put("action","subscribe");
        toBeSent.put("service", "event");

        //TODO: Debug, remove.
        System.out.println("Sent: " + toBeSent);

        return toBeSent.toString();
    }

    private static class LiveStreamingClient extends WebSocketClient {
        public LiveStreamingClient(URI serverUri) {
            super(serverUri);
        }

        @Override
        public void onOpen(ServerHandshake serverHandshake) {
            System.out.println("Opened connection");
        }

        @Override
        public void onMessage(String s) {
            Event event = CensusAPI.handleLiveStreamingResponse(s);
            if (event != null) {
                System.out.println(event);
            }
        }

        @Override
        public void onClose(int code, String reason, boolean b) {
            System.out.println("Close - code: " + code + " reason: " + reason);
            CONNECTED = false;
            CLIENT = null;
        }

        @Override
        public void onError(Exception e) {
            System.out.println("Error: " + e);
        }
    }
}