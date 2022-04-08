//Zacharias Thorell

//Dummy: {"service":"event","action":"help"}

import lib.CensusAPI;
import lib.IllegalServiceIdException;
import lib.PS2PlayerFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class SubscribeTask implements Runnable {
    private static LiveStreamingClient CLIENT;
    private static boolean CONNECTED;

    private final String characterId, event;

    public SubscribeTask(String characterName, String event) throws URISyntaxException, IllegalServiceIdException {
        if (CLIENT == null) {
            CLIENT = new LiveStreamingClient(new URI(CensusAPI.getEventStreamingUrl()));
        }
        this.characterId = PS2PlayerFactory.createPlayer(characterName).getId();
        this.event = event;
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

        CLIENT.send(formatPayLoad(characterId, event));
    }

    private String formatPayLoad(String characterId, String event) {
        JSONObject toBeSent = new JSONObject();

        toBeSent.put("eventNames", new JSONArray().put(event)); //"[" + event + "]"
        toBeSent.put("characters", new JSONArray().put(characterId));
        toBeSent.put("action","subscribe");
        toBeSent.put("service", "event");

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
            //System.out.println("Got message: " + s);
            JSONObject msg = new JSONObject(s);

            System.out.println("Got: " + msg);
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