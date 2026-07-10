package dark_shell.models.post_apocalypse;

import dark_shell.utils.SupportFunctions;

public class EnemysMove {

    private Enemy enemy;
    private Hero hero;
    private long enemyAttackValue;
    private long enemyAttackDebuff;
    private long heroOldHealth;

    public EnemysMove(Enemy enemy, Hero hero) {
        this.enemy = enemy;
        this.hero = hero;
    }

    public void makeMove() {
        this.enemyAttackDebuff = SupportFunctions.randomAtOneToFive();
        this.enemyAttackValue = enemy.getAttack() - hero.getDefense() - this.enemyAttackDebuff;

        this.heroOldHealth = this.hero.getCurrentHP();

        this.hero.takeDamage(this.enemyAttackValue);
    }

    public boolean haveWinner() {
        return enemy.isDead() || hero.isDead();
    }

    public boolean enemyWins() {
        return hero.isDead();
    }

    @Override
    public String toString() {
        StringBuilder enemysMoveRepresentation = new StringBuilder();

        enemysMoveRepresentation.append("```");
        enemysMoveRepresentation.append("\n");
        enemysMoveRepresentation.append(
                "Урон врага: (Урон врага - Защита игрока) - rand(1 - 5) = (" + this.enemy.getAttack()
                        + " - "
                        + this.hero.getDefense()
                        + ") - "
                        + this.enemyAttackDebuff
                        + " = "
                        + this.enemyAttackValue);
        enemysMoveRepresentation.append("\n");
        enemysMoveRepresentation.append("Новое здоровье игрока: Здоровье игрока - Урон врага = " + heroOldHealth
                + " - "
                + this.enemyAttackValue
                + " = "
                + this.hero.getCurrentHP());
        enemysMoveRepresentation.append("\n");
        enemysMoveRepresentation.append("```");

        return enemysMoveRepresentation.toString();
    }
}
