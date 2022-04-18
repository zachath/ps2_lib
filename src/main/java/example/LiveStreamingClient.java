//Zacharias Thorell

package example;

import lib.CensusAPI;
import lib.IllegalServiceIdException;
import lib.event.Event;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * Websocket client handling communication with the API server websocket.
 */
public class LiveStreamingClient extends WebSocketClient {
    public LiveStreamingClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("Opened connection");
    }

    @Override
    public void onMessage(String s) {
        try {
            Event event = CensusAPI.handleLiveStreamingResponse(s);
            if (event != null) {
                System.out.println(event);
            }
        } catch (IllegalServiceIdException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean b) {
        System.out.println("Close - code: " + code + " reason: " + reason);
    }

    @Override
    public void onError(Exception e) {
        System.out.println("Error: " + e);
    }
}