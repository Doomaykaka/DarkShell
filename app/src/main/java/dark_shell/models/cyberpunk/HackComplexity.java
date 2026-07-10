package dark_shell.models.cyberpunk;

public class HackComplexity {

    private Location location;
    private Hero hero;
    private long systemHackComplexity;
    private long hackPower;
    private long hackStepsCount;

    private final int HACK_STEPS_COUNT_DEVIDER = 5;

    public HackComplexity(Location location, Hero hero) {
        this.location = location;
        this.hero = hero;
    }

    public long calculateHackComplexity() {
        this.systemHackComplexity = this.location.getTheSecuritySystemLevelOfTheLocation();
        this.hackPower = this.hero.getTechnic();
        this.hackStepsCount = this.systemHackComplexity / HACK_STEPS_COUNT_DEVIDER;

        return this.hackStepsCount;
    }

    public long getHackPower() {
        return hackPower;
    }

    @Override
    public String toString() {
        StringBuilder combatInitiativeRepresentation = new StringBuilder();

        combatInitiativeRepresentation.append("```");
        combatInitiativeRepresentation.append("\n");
        combatInitiativeRepresentation.append(
                "Сложность взлома системы: Уровень системы безопасности в локации = " + this.systemHackComplexity);
        combatInitiativeRepresentation.append("\n");
        combatInitiativeRepresentation.append(
                "Сила взлома: Уровень навыка техники + бонусы от имплантов = " + this.hero.getTechnic()
                        + " + 0 = "
                        + this.hackPower);
        combatInitiativeRepresentation.append("\n");
        combatInitiativeRepresentation.append(
                "Число этапов взлома: Сложность взлома системы / " + HACK_STEPS_COUNT_DEVIDER
                        + " = "
                        + this.systemHackComplexity
                        + " / "
                        + HACK_STEPS_COUNT_DEVIDER
                        + " = "
                        + this.hackStepsCount);
        combatInitiativeRepresentation.append("\n");
        combatInitiativeRepresentation.append("```");

        return combatInitiativeRepresentation.toString();
    }
}
