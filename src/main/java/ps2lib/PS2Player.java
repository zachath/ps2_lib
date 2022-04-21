//Zacharias Thorell

package ps2lib;

/**
 * Container class representing a Planetside 2 player profile.
 */
public class PS2Player {
    /*Values that cannot change*/
    public final String id, name, creationDate;
    public final Faction faction;

    /*Values that can change with the call of update()*/
    private boolean online;
    private int logins, battleRank, minutesPlayed, hoursPlayed, totalCerts, availableCerts;

    protected PS2Player(String id, String name, Faction faction, String creationDate, boolean online, int logins, int battleRank,
                        int minutesPlayed, int hoursPlayed, int totalCerts, int availableCerts) {
        this.id = id;
        this.name = name;
        this.faction = faction;
        this.creationDate = creationDate;

        this.online = online;
        this.logins = logins;
        this.minutesPlayed = minutesPlayed;
        this.battleRank = battleRank;
        this.hoursPlayed = hoursPlayed;
        this.totalCerts = totalCerts;
        this.availableCerts = availableCerts;
    }

    /**
     * Updates the non-final fields of the instance by creating a new player instance with a new query to the API
     * and replacing the values with that of the newly created instance.
     */
    public void update() {
        try {
            PS2Player player = PS2PlayerFactory.createPlayerFromName(name);
            online = player.online;
            logins = player.logins;
            minutesPlayed = player.minutesPlayed;
            battleRank = player.battleRank;
            hoursPlayed = player.hoursPlayed;
            totalCerts = player.totalCerts;
            availableCerts = player.availableCerts;
        } catch (IllegalServiceIdException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Faction getFaction() {
        return faction;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public boolean isOnline() {
        return online;
    }

    public int getLogins() {
        return logins;
    }

    public int getBattleRank() {
        return battleRank;
    }

    public int getMinutesPlayed() {
        return minutesPlayed;
    }

    public int getHoursPlayed() {
        return hoursPlayed;
    }

    public int getTotalCerts() {
        return totalCerts;
    }

    public int getAvailableCerts() {
        return availableCerts;
    }

    /**
     * To characters are equal if they have the same id.
     * @param other Object to compare with.
     * @return if equal or not.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof PS2Player ps2Player) {
            return this.id.equals(ps2Player.id);
        }
        return false;
    }

    /**
     * @return the hashcode of the id.
     */
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
