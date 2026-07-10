package dark_shell.models.fantasy;

import dark_shell.utils.SupportFunctions;

public class UsersFightMove {

    private Enemy enemy;
    private Hero hero;
    private long heroAttackValue;
    private long heroAttackBuff;

    private long enemyOldHealth;

    public UsersFightMove(Enemy enemy, Hero hero) {
        this.enemy = enemy;
        this.hero = hero;
    }

    public void makeMove() {
        this.heroAttackBuff = SupportFunctions.randomAtOneToSix();
        this.heroAttackValue = this.hero.getStrength() - this.enemy.getDefense() + this.heroAttackBuff;

        this.enemyOldHealth = this.enemy.getHealth();

        this.enemy.takeDamage(this.heroAttackValue);
    }

    public boolean haveWinner() {
        return enemy.isDead() || hero.isDead();
    }

    public boolean heroWins() {
        return enemy.isDead();
    }

    @Override
    public String toString() {
        StringBuilder usersMoveRepresentation = new StringBuilder();

        usersMoveRepresentation.append("```");
        usersMoveRepresentation.append("\n");
        usersMoveRepresentation.append(
                "Урон игрока: (Сила игрока - Защита врага) + rand(1 - 6) = (" + this.hero.getStrength()
                        + " - "
                        + this.enemy.getDefense()
                        + ") + "
                        + this.heroAttackBuff
                        + " = "
                        + this.heroAttackValue);
        usersMoveRepresentation.append("\n");
        usersMoveRepresentation.append("Новое здоровье врага: Здоровье врага - Урон игрока = " + this.enemyOldHealth
                + " - "
                + this.heroAttackValue
                + " = "
                + this.enemy.getHealth());
        usersMoveRepresentation.append("\n");
        usersMoveRepresentation.append("```");

        return usersMoveRepresentation.toString();
    }
}
