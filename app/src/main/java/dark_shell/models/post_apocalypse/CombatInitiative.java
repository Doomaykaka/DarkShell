package dark_shell.models.post_apocalypse;

import dark_shell.utils.SupportFunctions;

public class CombatInitiative {

    private long chanceForInitiative;
    private long chanceOfTheEnemysInitiative;
    private long initiative;
    private long enemysInitiative;
    private long heroSpeed;

    private Boolean heroHaveCombatInitiative;

    private final int ENEMY_SPEED = 5;

    public CombatInitiative(long heroSpeed) {
        this.heroSpeed = heroSpeed;
    }

    public boolean heroHaveCombatInitiative() {
        this.chanceForInitiative = SupportFunctions.randomAtOneToTwenty();
        this.chanceOfTheEnemysInitiative = SupportFunctions.randomAtOneToTwenty();
        this.initiative = this.chanceForInitiative + this.heroSpeed;
        this.enemysInitiative = this.chanceOfTheEnemysInitiative + ENEMY_SPEED;

        if (heroHaveCombatInitiative == null) {
            this.heroHaveCombatInitiative = this.initiative > this.enemysInitiative;
        }

        return this.heroHaveCombatInitiative;
    }

    @Override
    public String toString() {
        StringBuilder combatInitiativeRepresentation = new StringBuilder();

        combatInitiativeRepresentation.append("```");
        combatInitiativeRepresentation.append("\n");
        combatInitiativeRepresentation.append("Шанс инициативы: rand(1 - 20) = " + this.chanceForInitiative);
        combatInitiativeRepresentation.append("\n");
        combatInitiativeRepresentation.append(
                "Шанс инициативы врага: rand(1 - 20) = " + this.chanceOfTheEnemysInitiative);
        combatInitiativeRepresentation.append("\n");
        combatInitiativeRepresentation.append("Инициатива: Шанс инициативы + Скорость = " + this.chanceForInitiative
                + " + "
                + this.heroSpeed
                + " = "
                + this.initiative);
        combatInitiativeRepresentation.append("\n");
        combatInitiativeRepresentation.append("Инициатива врага: Шанс инициативы врага + " + ENEMY_SPEED
                + " = "
                + this.chanceOfTheEnemysInitiative
                + " + "
                + ENEMY_SPEED
                + " = "
                + this.enemysInitiative);
        combatInitiativeRepresentation.append("\n");

        if (heroHaveCombatInitiative) {
            combatInitiativeRepresentation.append("Инициатива у игрока.");
        } else {
            combatInitiativeRepresentation.append("Инициатива у врага.");
        }

        combatInitiativeRepresentation.append("\n");
        combatInitiativeRepresentation.append("```");

        return combatInitiativeRepresentation.toString();
    }
}
