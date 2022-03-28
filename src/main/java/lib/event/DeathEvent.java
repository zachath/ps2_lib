//Zacharias Thorell

package lib.event;

public class DeathEvent extends Event {
    private final String attackerId;
    private final boolean headshot, critical;

    public DeathEvent(String playerId, String attacker, String headshot, String critical) {
        super(playerId);
        this.attackerId = attacker;
        this.headshot = headshot.equals("1");
        this.critical = critical.equals("1");
    }

    public String getAttackerId() {
        return attackerId;
    }

    public boolean isHeadshot() {
        return headshot;
    }

    public boolean isCritical() {
        return critical;
    }

    @Override
    public String toString() {
        return attackerId + " killed " + playerID;
    }
}
