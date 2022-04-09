//Zacharias Thorell
import lib.CensusAPI;
import lib.IllegalServiceIdException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Entry point for application, accepts inputs and executes commands until closed.
 */
public class Console {
    private static final String SERVICE_ID = "TorranPS2Util";

    public static void main(String[] args) throws IllegalServiceIdException, URISyntaxException {
        CensusAPI.setServiceId(SERVICE_ID);

        new Thread(new SubscribeTask("SgtTorranVS", List.of("PlayerLogin", "PlayerLogout"))).start();
    }
}