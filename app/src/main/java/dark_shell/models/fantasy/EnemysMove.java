package dark_shell.models.fantasy;

import dark_shell.utils.SupportFunctions;

public class EnemysMove {

    private Enemy enemy;
    private Hero hero;
    private long enemyAttackValue;
    private long enemyAttackDebuff;
    private long heroOldHealth;
    private boolean haveStress;

    public EnemysMove(Enemy enemy, Hero hero) {
        this.enemy = enemy;
        this.hero = hero;
    }

    public void makeMove() {
        this.enemyAttackDebuff = SupportFunctions.randomAtOneToTwenty();
        this.enemyAttackValue = enemy.getAttack() - this.enemyAttackDebuff;

        this.heroOldHealth = this.hero.getCurrentHP();

        this.hero.takeDamage(this.enemyAttackValue);
    }

    public boolean haveWinner() {
        return enemy.isDead() || hero.isDead();
    }

    public boolean enemyWins() {
        return hero.isDead();
    }

    public boolean haveStress() {
        return this.haveStress;
    }

    @Override
    public String toString() {
        StringBuilder enemysMoveRepresentation = new StringBuilder();

        enemysMoveRepresentation.append("```");
        enemysMoveRepresentation.append("\n");
        enemysMoveRepresentation.append(
                "Урон врага: (Урон врага - Защита игрока в виде брони) - rand(1 - 4) = (" + this.enemy.getAttack()
                        + " - 0) - "
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
