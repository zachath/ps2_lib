//Zacharias Thorell

package lib;

/**
 * Representing the factions of Planetside 2.
 */
public enum Faction {
    TR("Terran Republic", "TR", Util.ANSI_RED),
    NC("New Conglomerate", "NC", Util.ANSI_BLUE),
    VS("Vanu Sovereignty", "VS", Util.ANSI_PURPLE),
    NSO("NSO Operative", "NSO", Util.ANSI_WHITE);

    final String fullName;
    final String shortHand;
    final String colour;
    Faction(String fullName, String shortHand, String colour) {
        this.fullName = fullName;
        this.shortHand = shortHand;
        this.colour = colour;
    }

    /**
     * @return the colour of this faction.
     */
    public String getFactionColour() {
        return colour;
    }

    /**
     * Returns a String of the factions full name in the faction specific colour.
     * @return String representation of the faction.
     */
    @Override
    public String toString() {
        return Util.printInColour(fullName, colour);
    }
}
