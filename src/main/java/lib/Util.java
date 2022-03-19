//Zacharias Thorell

package lib;

/**
 * Utility class for stuff not directly related to the Census API.
 */
public class Util {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    /**
     * Prints the given String in colour without the need remembering to reset.
     * @param s String to print.
     * @param colour Colour to print the string in.
     * @return A string in the format of: [colour][String][reset], when printed to screen this results in the string in the specified colour.
     */
    public static String printInColour(String s, String colour) {
        return colour + s + ANSI_RESET;
    }
}