package dark_shell.models.cyberpunk;

import dark_shell.utils.SupportFunctions;

public class UsersMove {

    private Enemy enemy;
    private Hero hero;
    private long heroAttackValue;
    private long heroAttackBuff;
    private long enemyOldHealth;

    private final int DEFENCE_DIVIDER = 2;

    public UsersMove(Enemy enemy, Hero hero) {
        this.enemy = enemy;
        this.hero = hero;
    }

    public void makeMove() {
        this.heroAttackBuff = SupportFunctions.randomAtOneToFive();
        this.heroAttackValue = this.hero.getTechnic() - this.enemy.getDefense() / DEFENCE_DIVIDER + this.heroAttackBuff;

        enemyOldHealth = this.enemy.getHealth();

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
        usersMoveRepresentation.append("Степень успешности атаки: rand(1 - 5) = " + this.heroAttackBuff);
        usersMoveRepresentation.append("\n");
        usersMoveRepresentation.append("Урон игрока: (Уровень техники игрока  "
                + " - Защита врага / " + DEFENCE_DIVIDER + ") + rand(1 - 5) = ("
                + this.hero.getTechnic()
                + " - "
                + this.enemy.getDefense()
                + " / "
                + DEFENCE_DIVIDER
                + ") + "
                + this.heroAttackBuff
                + " = "
                + this.heroAttackValue);
        usersMoveRepresentation.append("\n");
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
