//Zacharias Thorell

package ps2lib;

/**
 * Representing the factions of Planetside 2.
 */
public enum Faction {
    TR("Terran Republic", "TR"),
    NC("New Conglomerate", "NC"),
    VS("Vanu Sovereignty", "VS"),
    NSO("NSO Operative", "NSO");

    public final String fullName;
    public final String shortHand;

    Faction(String fullName, String shortHand) {
        this.fullName = fullName;
        this.shortHand = shortHand;
    }

    /**
     * Returns a String of the factions full name in the faction specific colour (ONLY COMMAND LINE).
     * @return String representation of the faction.
     */
    @Override
    public String toString() {
        return fullName;
    }
}
