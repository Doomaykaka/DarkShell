package dark_shell.models.cyberpunk;

import dark_shell.utils.SupportFunctions;

public class CombatInitiative {

    private long chanceForInitiative;
    private long chanceOfTheEnemysInitiative;
    private long initiative;
    private long enemysInitiative;
    private long heroInfiltration;
    private long locationSecurityLevel;

    private Boolean heroHaveCombatInitiative;

    private final int ENEMY_CHANCE = 10;
    private final int ENEMY_INITIATIVE_DIVIDER = 2;

    public CombatInitiative(long heroInfiltration, long locationSecurityLevel) {
        this.heroInfiltration = heroInfiltration;
        this.locationSecurityLevel = locationSecurityLevel;
    }

    public boolean heroHaveCombatInitiative() {
        this.chanceForInitiative = SupportFunctions.randomAtOneToTwenty();
        this.chanceOfTheEnemysInitiative = ENEMY_CHANCE;
        this.initiative = this.chanceForInitiative + this.heroInfiltration;
        this.enemysInitiative =
                this.chanceOfTheEnemysInitiative + this.locationSecurityLevel / ENEMY_INITIATIVE_DIVIDER;

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
        combatInitiativeRepresentation.append("Шанс инициативы врага: " + this.chanceOfTheEnemysInitiative);
        combatInitiativeRepresentation.append("\n");
        combatInitiativeRepresentation.append("Инициатива: Шанс инициативы + Инфильтрация = " + this.chanceForInitiative
                + " + "
                + this.heroInfiltration
                + " = "
                + this.initiative);
        combatInitiativeRepresentation.append("\n");
        combatInitiativeRepresentation.append(
                "Инициатива врага: Шанс инициативы врага + Уровень безопасности в локации / " + ENEMY_INITIATIVE_DIVIDER
                        + " = "
                        + this.chanceOfTheEnemysInitiative
                        + " + "
                        + this.locationSecurityLevel
                        + " / "
                        + ENEMY_INITIATIVE_DIVIDER
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
