package dark_shell.models.cyberpunk;

public class Enemy {

    private long theSecurityLevelOfTheLocation;
    private long health;
    private long attack;
    private long defense;

    private final int ENEMY_HEALTH_MULTIPLIER = 4;
    private final int ENEMY_ATTACK_RATIO = 10;

    private final int MIN_HEALTH = 0;
    private final int MIN_DAMAGE = 0;

    public Enemy(long theSecurityLevelOfTheLocation) {
        this.theSecurityLevelOfTheLocation = theSecurityLevelOfTheLocation;
    }

    public void calculateEnemy() {
        this.health = this.theSecurityLevelOfTheLocation * ENEMY_HEALTH_MULTIPLIER;
        this.attack = this.theSecurityLevelOfTheLocation + ENEMY_ATTACK_RATIO;
        this.defense = this.theSecurityLevelOfTheLocation;
    }

    public void takeDamage(long damageValue) {
        if (damageValue > MIN_DAMAGE) {
            this.health = this.health - damageValue;
        }
    }

    public boolean isDead() {
        return health <= MIN_HEALTH;
    }

    public long getTheSecurityLevelOfTheLocation() {
        return theSecurityLevelOfTheLocation;
    }

    public long getHealth() {
        return health;
    }

    public long getAttack() {
        return attack;
    }

    public long getDefense() {
        return defense;
    }

    @Override
    public String toString() {
        StringBuilder enemyRepresentation = new StringBuilder();

        enemyRepresentation.append("```");
        enemyRepresentation.append("\n");
        enemyRepresentation.append("Здоровье врага: Уровень безопасности в локации * " + ENEMY_HEALTH_MULTIPLIER
                + " = "
                + this.getTheSecurityLevelOfTheLocation()
                + " * "
                + ENEMY_HEALTH_MULTIPLIER
                + " = "
                + this.getHealth());
        enemyRepresentation.append("\n");
        enemyRepresentation.append("Урон врага: Уровень безопасности в локации + " + ENEMY_ATTACK_RATIO
                + " = "
                + this.getTheSecurityLevelOfTheLocation()
                + " + "
                + ENEMY_ATTACK_RATIO
                + " = "
                + this.getAttack());
        enemyRepresentation.append("\n");
        enemyRepresentation.append("Защита врага: Уровень опасности локации = " + this.getDefense());
        enemyRepresentation.append("\n");
        enemyRepresentation.append("```");

        return enemyRepresentation.toString();
    }
}
