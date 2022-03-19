//Zacharias Thorell

package lib;

/**
 * Container class representing a Planetside 2 player profile.
 */
public class PS2Player {
    /*Values that cannot change*/
    private final String name;
    private final Faction faction;
    private final String creationDate;

    /*Values that can change with the call of update()*/
    private boolean online;
    private int logins;
    private int battleRank;
    private int minutesPlayed;
    private int hoursPlayed;
    private int totalCerts;
    private int availableCerts;

    protected PS2Player(String name, Faction faction, String creationDate, boolean online, int logins, int battleRank,
                        int minutesPlayed, int hoursPlayed, int totalCerts, int availableCerts) {
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
            PS2Player player = PS2PlayerFactory.createPlayer(name);
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
}
