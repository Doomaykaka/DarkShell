package dark_shell.models.post_apocalypse;

import dark_shell.utils.SupportFunctions;

public class UsersMove {

    private Enemy enemy;
    private Hero hero;
    private long heroCriticalAttackChance;
    private long heroCriticalAttackValue;
    private long heroAttackValue;
    private long heroAttackDebuff;
    private boolean haveCriticalAttack = false;
    private long enemyOldHealth;

    private final int CRITICAL_ATTACK_MULTIPLIER = 2;
    private final int EMPTY_HERO_CRITICAL_ATTACK_VALUE = 0;

    public UsersMove(Enemy enemy, Hero hero) {
        this.enemy = enemy;
        this.hero = hero;
    }

    public void makeMove() {
        this.heroCriticalAttackChance = SupportFunctions.randomAtOneToFive();
        this.haveCriticalAttack = this.heroCriticalAttackChance < this.hero.getLuck();
        this.heroAttackDebuff = SupportFunctions.randomAtOneToFive();
        this.heroAttackValue = this.hero.getAttack() - this.enemy.getDefense() - this.heroAttackDebuff;

        if (haveCriticalAttack) {
            this.heroCriticalAttackValue = this.heroAttackValue * CRITICAL_ATTACK_MULTIPLIER;
        } else {
            this.heroCriticalAttackValue = 0;
        }

        enemyOldHealth = this.enemy.getHealth();

        if (haveCriticalAttack) {
            this.enemy.takeDamage(this.heroCriticalAttackValue);
        } else {
            this.enemy.takeDamage(this.heroAttackValue);
        }
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

        if (this.heroCriticalAttackChance < this.hero.getLuck()) {
            usersMoveRepresentation.append("Проверка критического урона: rand(1 - 5) = " + this.heroCriticalAttackChance
                    + " < Удачи ("
                    + this.hero.getLuck()
                    + ") = есть крит");
        } else {
            usersMoveRepresentation.append("Проверка критического урона: rand(1 - 5) = " + this.heroCriticalAttackChance
                    + " !< Удачи ("
                    + this.hero.getLuck()
                    + ") = нет крита");
        }

        usersMoveRepresentation.append("\n");
        usersMoveRepresentation.append(
                "Урон игрока: (Урон игрока - Защита врага) - rand(1 - 5) = (" + this.hero.getAttack()
                        + " - "
                        + this.enemy.getDefense()
                        + ") - "
                        + this.heroAttackDebuff
                        + " = "
                        + this.heroAttackValue);
        usersMoveRepresentation.append("\n");

        if (this.haveCriticalAttack) {
            usersMoveRepresentation.append(
                    "Критический урон игрока: Урон игрока * Множитель критического урона при наличии = "
                            + this.heroAttackValue
                            + " * "
                            + CRITICAL_ATTACK_MULTIPLIER
                            + " = "
                            + this.heroCriticalAttackValue);
        } else {
            usersMoveRepresentation.append(
                    "Критический урон игрока: Урон игрока * Множитель критического урона при наличии = "
                            + this.heroAttackValue
                            + " * "
                            + EMPTY_HERO_CRITICAL_ATTACK_VALUE
                            + " = "
                            + EMPTY_HERO_CRITICAL_ATTACK_VALUE);
        }

        usersMoveRepresentation.append("\n");

        if (this.haveCriticalAttack) {
            usersMoveRepresentation.append(
                    "Новое здоровье врага: Здоровье врага - (Урон игрока или Критический урон игрока при наличии) = "
                            + this.enemyOldHealth
                            + " - ("
                            + this.heroCriticalAttackValue
                            + ") = "
                            + this.enemy.getHealth());
        } else {
            usersMoveRepresentation.append(
                    "Новое здоровье врага: Здоровье врага - (Урон игрока или Критический урон игрока при наличии) = "
                            + this.enemyOldHealth
                            + " - ("
                            + this.heroAttackValue
                            + ") = "
                            + this.enemy.getHealth());
        }

        usersMoveRepresentation.append("\n");
        usersMoveRepresentation.append("```");

        return usersMoveRepresentation.toString();
    }
}
