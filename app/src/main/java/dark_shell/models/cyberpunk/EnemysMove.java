package dark_shell.models.cyberpunk;

import dark_shell.utils.SupportFunctions;

public class EnemysMove {

    private Enemy enemy;
    private Hero hero;
    private long enemyAttackValue;
    private long enemyAttackBuff;
    private long heroOldHealth;
    private boolean haveStress;

    private final long EMPTY_ATTACK_VALUE = 0;
    private final long COOL_DAMAGE_VALUE = 1;
    private final boolean HAVE_STRESS_DEFAULT_VALUE = false;

    public EnemysMove(Enemy enemy, Hero hero) {
        this.enemy = enemy;
        this.hero = hero;
        this.haveStress = HAVE_STRESS_DEFAULT_VALUE;
    }

    public void makeMove() {
        this.enemyAttackBuff = SupportFunctions.randomAtOneToFive();
        this.enemyAttackValue = enemy.getAttack() + this.enemyAttackBuff;

        this.heroOldHealth = this.hero.getCurrentHP();

        this.hero.takeDamage(this.enemyAttackValue);

        this.haveStress = this.enemyAttackValue > EMPTY_ATTACK_VALUE;

        if (this.haveStress) {
            this.hero.takeStressValue(COOL_DAMAGE_VALUE);
        }
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
        enemysMoveRepresentation.append("Урон врага: Урон врага + rand(1 - 5) = " + this.enemy.getAttack()
                + " + "
                + this.enemyAttackBuff
                + " = "
                + this.enemyAttackValue);
        enemysMoveRepresentation.append("\n");
        enemysMoveRepresentation.append("Новое здоровье игрока: Здоровье игрока - Урон врага = " + heroOldHealth
                + " - "
                + this.enemyAttackValue
                + " = "
                + this.hero.getCurrentHP());
        enemysMoveRepresentation.append("\n");

        if (this.haveStress) {
            long oldCoolValue = this.hero.getCool() + COOL_DAMAGE_VALUE;

            enemysMoveRepresentation.append("У игрока стресс: Хладнокровие игрока - " + COOL_DAMAGE_VALUE
                    + " = "
                    + oldCoolValue
                    + " - "
                    + COOL_DAMAGE_VALUE
                    + " = "
                    + this.hero.getCool());
            enemysMoveRepresentation.append("\n");
        }

        enemysMoveRepresentation.append("```");

        return enemysMoveRepresentation.toString();
    }
}
