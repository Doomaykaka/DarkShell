package dark_shell.models.cyberpunk;

import dark_shell.utils.SupportFunctions;

public class UsersHackStep {

    private Location location;
    private Hero hero;
    private long hackComplexity;
    private long hackStepBuff;
    private long alarmChance;
    private boolean haveAlarm;
    private long mindDamage;
    private long oldHeroHealth;
    private long oldHeroNoise;
    private long currentHackStep;

    private final int HACK_STEP_ALARM_EDGE = 0;
    private final int HACK_STEP_ALARM_NOISE_ADDITIONAL = 3;
    private final int HACK_STEP_TECHNIC_DIVIDER = 2;

    public UsersHackStep(Location location, Hero hero, long hackComplexity) {
        this.location = location;
        this.hero = hero;
        this.hackComplexity = hackComplexity;
    }

    public boolean hack(long currentHackStep) {
        boolean success = false;

        this.currentHackStep = ++currentHackStep;

        this.hackStepBuff = SupportFunctions.randomAtOneToTwenty();
        long securitySystemLevel = this.location.calculateSecuritySystemLevel();
        long heroTechnic = this.hero.getTechnic();
        this.alarmChance = this.hackStepBuff + heroTechnic / HACK_STEP_TECHNIC_DIVIDER - securitySystemLevel;
        this.haveAlarm = this.alarmChance <= HACK_STEP_ALARM_EDGE;

        success = !this.haveAlarm;

        if (this.haveAlarm) {
            this.oldHeroHealth = this.hero.getCurrentHP();
            this.mindDamage = SupportFunctions.randomAtOneToSix();
            this.oldHeroNoise = this.hero.getNoise();

            this.hero.takeDamage(mindDamage);
            this.hero.addNoise(HACK_STEP_ALARM_NOISE_ADDITIONAL);
        }

        return success;
    }

    @Override
    public String toString() {
        StringBuilder usersHackStepRepresentation = new StringBuilder();

        usersHackStepRepresentation.append("```");
        usersHackStepRepresentation.append("\n");

        if (this.alarmChance > HACK_STEP_ALARM_EDGE) {
            usersHackStepRepresentation.append("Этап взлома (" + this.currentHackStep
                    + "/"
                    + this.hackComplexity
                    + "): rand(1 - 20) + Уровень навыка техники (TECH) / " + HACK_STEP_TECHNIC_DIVIDER
                    + " - Уровень системы безопасности в локации (SL) > "
                    + HACK_STEP_ALARM_EDGE
                    + " = "
                    + this.hackStepBuff
                    + " + "
                    + this.hero.getTechnic()
                    + " / "
                    + HACK_STEP_TECHNIC_DIVIDER
                    + " - "
                    + this.location.calculateSecuritySystemLevel()
                    + " = "
                    + this.alarmChance
                    + " > "
                    + HACK_STEP_ALARM_EDGE
                    + " = успех");
        } else {
            usersHackStepRepresentation.append("Этап взлома (" + this.currentHackStep
                    + "/"
                    + this.hackComplexity
                    + "): rand(1 - 20) + Уровень навыка техники (TECH) - Уровень системы безопасности в локации (SL) > "
                    + HACK_STEP_ALARM_EDGE
                    + " = "
                    + this.hackStepBuff
                    + " + "
                    + this.hero.getTechnic()
                    + " - "
                    + this.location.calculateSecuritySystemLevel()
                    + " > "
                    + HACK_STEP_ALARM_EDGE
                    + " = "
                    + this.alarmChance
                    + " !> "
                    + HACK_STEP_ALARM_EDGE
                    + " = обнаружение");
            usersHackStepRepresentation.append("\n");
            usersHackStepRepresentation.append("\n");
            usersHackStepRepresentation.append("Урон по разуму: rand(1 - 6) = " + this.mindDamage);
            usersHackStepRepresentation.append("\n");
            usersHackStepRepresentation.append(
                    "Новое здоровье игрока: Здоровье игрока - Урон по разуму = " + this.oldHeroHealth
                            + " - "
                            + this.mindDamage
                            + " = "
                            + this.hero.getCurrentHP());
            usersHackStepRepresentation.append("\n");
            usersHackStepRepresentation.append("\n");
            usersHackStepRepresentation.append(
                    "Рост тревоги в системе: текущий уровень тревоги (NOISE) + " + HACK_STEP_ALARM_NOISE_ADDITIONAL
                            + " = "
                            + this.oldHeroNoise
                            + " + "
                            + HACK_STEP_ALARM_NOISE_ADDITIONAL
                            + " = "
                            + this.hero.getNoise());
        }

        usersHackStepRepresentation.append("\n");
        usersHackStepRepresentation.append("```");

        return usersHackStepRepresentation.toString();
    }
}
