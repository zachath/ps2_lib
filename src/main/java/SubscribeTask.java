//Zacharias Thorell

//Dummy: {"service":"event","action":"help"}

import lib.CensusAPI;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class SubscribeTask implements Runnable {
    private final String characterName, event;
    private final LiveStreamingClient client;

    public SubscribeTask(String characterName, String event) throws URISyntaxException {
        this.characterName = characterName;
        this.event = event;
        client = new LiveStreamingClient(new URI(CensusAPI.getEventStreamingUrl()));
    }

    @Override
    public void run() {
        client.connect();
    }

    private class LiveStreamingClient extends WebSocketClient {

        public LiveStreamingClient(URI serverUri) {
            super(serverUri);
        }

        @Override
        public void onOpen(ServerHandshake serverHandshake) {
            System.out.println("Connection opened");
            send("{\"service\":\"event\",\"action\":\"help\"}");
        }

        @Override
        public void onMessage(String s) {
            System.out.println("Got message: " + s);
        }

        @Override
        public void onClose(int code, String reason, boolean b) {
            System.out.println("Close - code: " + code + " reason: " + reason);
        }

        @Override
        public void onError(Exception e) {
            System.out.println("Error");
        }
    }
}
