package dark_shell.models.fantasy;

public class Enemy {

    private long theDifficultyLevelOfTheLocation;
    private long health;
    private long attack;
    private long defense;

    private final int ENEMY_HEALTH_MULTIPLIER = 6;
    private final int ENEMY_ATTACK_RATIO = 8;

    private final int MIN_HEALTH = 0;
    private final int MIN_DAMAGE = 0;

    public Enemy(long theDifficultyLevelOfTheLocation) {
        this.theDifficultyLevelOfTheLocation = theDifficultyLevelOfTheLocation;
    }

    public void calculateEnemy() {
        this.health = this.theDifficultyLevelOfTheLocation * ENEMY_HEALTH_MULTIPLIER;
        this.attack = this.theDifficultyLevelOfTheLocation + ENEMY_ATTACK_RATIO;
        this.defense = this.theDifficultyLevelOfTheLocation;
    }

    public void takeDamage(long damageValue) {
        if (damageValue > MIN_DAMAGE) {
            this.health = this.health - damageValue;
        }
    }

    public boolean isDead() {
        return health <= MIN_HEALTH;
    }

    public long getTheDifficultyLevelOfTheLocation() {
        return theDifficultyLevelOfTheLocation;
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
        enemyRepresentation.append("Здоровье врага: Сложность локации * " + ENEMY_HEALTH_MULTIPLIER
                + " = "
                + this.getTheDifficultyLevelOfTheLocation()
                + " * "
                + ENEMY_HEALTH_MULTIPLIER
                + " = "
                + this.getHealth());
        enemyRepresentation.append("\n");
        enemyRepresentation.append("Урон врага: Сложность локации + " + ENEMY_ATTACK_RATIO
                + " = "
                + this.getTheDifficultyLevelOfTheLocation()
                + " + "
                + ENEMY_ATTACK_RATIO
                + " = "
                + this.getAttack());
        enemyRepresentation.append("\n");
        enemyRepresentation.append("Защита врага: Сложность локации = " + this.getDefense());
        enemyRepresentation.append("\n");
        enemyRepresentation.append("```");

        return enemyRepresentation.toString();
    }
}
