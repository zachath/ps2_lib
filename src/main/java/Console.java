//Zacharias Thorell

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Entry point for application, accepts inputs and executes commands until closed.
 */
public class Console {
    private static final String CLOSE_COMMAND = "close";
    private static final String LOOKUP_COMMAND = "lookup";

    private static final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    private static boolean running = true;

    public static void main(String[] args) {
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
        if (input[0].equals(CLOSE_COMMAND)) {
            running = false;
        }
        else if (input[0].equals(LOOKUP_COMMAND)) {
            new Thread(new LookupTask(input[1])).start();
        }
        else {
            System.out.println("Unsupported command: " + Arrays.toString(input));
        }
    }
}