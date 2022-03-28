//Zacharias Thorell

import lib.CensusAPI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Entry point for application, accepts inputs and executes commands until closed.
 */
public class Console {
    private static final String SERVICE_ID = "TorranPS2Util";

    private static final String CLOSE_COMMAND = "close";
    private static final String LOOKUP_COMMAND = "lookup";
    private static final String MATCHUP_COMMAND = "matchup";

    private static final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    private static boolean running = true;

    public static void main(String[] args) {
        CensusAPI.setServiceId(SERVICE_ID);

        try {
            while (running) {
                handleInput(consoleReader.readLine().toLowerCase().split("\\s+"));
            }

            System.out.println("Shutting Down");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes the given as a runnable task in a new thread if it is considered valid input.
     * @param input user input.
     */
    public static void handleInput(String[] input) {
        switch (input[0]) {
            case CLOSE_COMMAND -> running = false;
            case LOOKUP_COMMAND -> new Thread(new LookupTask(input[1])).start();
            case MATCHUP_COMMAND -> new Thread(new MatchupTask(input[1], input[2])).start();
            default -> System.out.println("Unsupported command: " + Arrays.toString(input));
        }
    }
}