package dark_shell.models.post_apocalypse;

public class Enemy {

    private long theDangerLevelOfTheLocation;
    private long health;
    private long attack;
    private long defense;

    private final int ENEMY_HEALTH_MULTIPLIER = 5;
    private final int ENEMY_ATTACK_RATIO = 5;
    private final int ENEMY_DEFENSE_DEVIDER = 2;

    private final int MIN_HEALTH = 0;

    public Enemy(long theDangerLevelOfTheLocation) {
        this.theDangerLevelOfTheLocation = theDangerLevelOfTheLocation;
    }

    public void calculateEnemy() {
        this.health = this.theDangerLevelOfTheLocation * ENEMY_HEALTH_MULTIPLIER;
        this.attack = this.theDangerLevelOfTheLocation + ENEMY_ATTACK_RATIO;
        this.defense = this.theDangerLevelOfTheLocation / ENEMY_DEFENSE_DEVIDER;
    }

    public void takeDamage(long damageValue) {
        this.health = this.health - damageValue;
    }

    public boolean isDead() {
        return health <= MIN_HEALTH;
    }

    public long getTheDangerLevelOfTheLocation() {
        return theDangerLevelOfTheLocation;
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
        enemyRepresentation.append("Здоровье врага: Уровень опасности локации * " + ENEMY_HEALTH_MULTIPLIER
                + " = "
                + this.getTheDangerLevelOfTheLocation()
                + " * "
                + ENEMY_HEALTH_MULTIPLIER
                + " = "
                + this.getHealth());
        enemyRepresentation.append("\n");
        enemyRepresentation.append("Урон врага: Уровень опасности локации + " + ENEMY_ATTACK_RATIO
                + " = "
                + this.getTheDangerLevelOfTheLocation()
                + " + "
                + ENEMY_ATTACK_RATIO
                + " = "
                + this.getAttack());
        enemyRepresentation.append("\n");
        enemyRepresentation.append("Защита врага: Уровень опасности локации / " + ENEMY_DEFENSE_DEVIDER
                + " = "
                + this.getTheDangerLevelOfTheLocation()
                + " / "
                + ENEMY_DEFENSE_DEVIDER
                + " = "
                + this.getDefense());
        enemyRepresentation.append("\n");
        enemyRepresentation.append("```");

        return enemyRepresentation.toString();
    }
}
