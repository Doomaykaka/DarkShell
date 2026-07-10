package dark_shell.models.fantasy;

import dark_shell.utils.SupportFunctions;

public class UsersMagicalMove {

    private long chanceOfSpellBeingTriggered;
    private long spellTriggerRate;
    private boolean spellWorked;
    private long floatingPartOfDamage;
    private long damage;
    private long oldEnemyHealth;
    private long oldHeroHealth;
    private long oldHeroMana;
    private long heroIntelligenceDamage;
    private boolean heroHaveTemporaryLossOfIntelligence;

    private Hero hero;
    private Enemy enemy;
    private long theDifficultyLevelOfTheLocation;

    private final int SPELL_TRIGGER_EDGE = 0;
    private final int LOSS_EDGE = 1;
    private final int INTELLEGENCE_DEBUFF_COEFFICIENT = 1;

    public UsersMagicalMove(Hero hero, Enemy enemy, long theDifficultyLevelOfTheLocation) {
        this.hero = hero;
        this.enemy = enemy;
        this.theDifficultyLevelOfTheLocation = theDifficultyLevelOfTheLocation;
    }

    public boolean conjure() {
        this.chanceOfSpellBeingTriggered = SupportFunctions.randomAtOneToTwenty();
        this.spellTriggerRate =
                this.chanceOfSpellBeingTriggered + this.hero.getIntelligence() - this.theDifficultyLevelOfTheLocation;

        this.spellWorked = this.spellTriggerRate > SPELL_TRIGGER_EDGE;

        if (this.spellWorked) {
            this.floatingPartOfDamage = SupportFunctions.randomAtOneToSix();
            this.damage = this.spellTriggerRate - this.enemy.getDefense() + this.floatingPartOfDamage;

            this.oldEnemyHealth = this.enemy.getHealth();
            this.oldHeroMana = this.hero.getManaPoints();

            this.enemy.takeDamage(this.damage);
            this.hero.spendMana(this.theDifficultyLevelOfTheLocation);
        } else {
            this.heroIntelligenceDamage = SupportFunctions.randomAtOneToEight();
            this.heroHaveTemporaryLossOfIntelligence = this.spellTriggerRate == LOSS_EDGE;

            this.oldHeroHealth = this.hero.getCurrentHP();
            this.oldHeroMana = this.hero.getManaPoints();

            this.hero.takeDamage(this.heroIntelligenceDamage);
            this.hero.spendMana(this.theDifficultyLevelOfTheLocation);
        }

        return this.spellWorked;
    }

    @Override
    public String toString() {
        StringBuilder usersMagicalMoveRepresentation = new StringBuilder();

        usersMagicalMoveRepresentation.append("```");
        usersMagicalMoveRepresentation.append("\n");
        usersMagicalMoveRepresentation.append(
                "Шанс срабатывания заклинания: rand(1 - 20) = " + this.chanceOfSpellBeingTriggered);
        usersMagicalMoveRepresentation.append("\n");
        usersMagicalMoveRepresentation.append(
                "Коэффициент срабатывания заклинения: Шанс срабатывания заклинания + Интеллект - Сложность локации = "
                        + this.chanceOfSpellBeingTriggered
                        + " + "
                        + this.hero.getIntelligence()
                        + " - "
                        + theDifficultyLevelOfTheLocation
                        + " = "
                        + this.spellTriggerRate);
        usersMagicalMoveRepresentation.append("\n");

        if (this.spellWorked) {
            usersMagicalMoveRepresentation.append(
                    "Результат заклинания: Коэффициент срабатывания заклинения > " + SPELL_TRIGGER_EDGE
                            + " = "
                            + this.spellTriggerRate
                            + " > "
                            + SPELL_TRIGGER_EDGE
                            + " = Заклинание сработало");
            usersMagicalMoveRepresentation.append("\n");
            usersMagicalMoveRepresentation.append(
                    "Нанесённый урон: Результат заклинания - Защита врага + rand(1 - 6) = " + this.spellTriggerRate
                            + " - "
                            + this.enemy.getDefense()
                            + " + "
                            + this.floatingPartOfDamage
                            + " = "
                            + this.damage);
            usersMagicalMoveRepresentation.append("\n");
            usersMagicalMoveRepresentation.append(
                    "Новое здоровье врага: Здоровье врага - Урон игрока = " + this.oldEnemyHealth
                            + " - "
                            + this.damage
                            + " = "
                            + this.enemy.getHealth());
            usersMagicalMoveRepresentation.append("\n");
            usersMagicalMoveRepresentation.append(
                    "Новая мана игрока = Мана игрока - Сложность локации = " + this.oldHeroMana
                            + " - "
                            + this.theDifficultyLevelOfTheLocation
                            + " = "
                            + this.hero.getManaPoints());
            usersMagicalMoveRepresentation.append("\n");
        } else {
            usersMagicalMoveRepresentation.append(
                    "Результат заклинания: Коэффициент срабатывания заклинения > " + SPELL_TRIGGER_EDGE
                            + " = "
                            + this.spellTriggerRate
                            + " > "
                            + SPELL_TRIGGER_EDGE
                            + " = Заклинание не сработало");
            usersMagicalMoveRepresentation.append("\n");
            usersMagicalMoveRepresentation.append("Урон по разуму: rand(1 - 8) = " + this.heroIntelligenceDamage);
            usersMagicalMoveRepresentation.append("\n");

            if (this.heroHaveTemporaryLossOfIntelligence) {
                long intellegenceWithDebuff = this.hero.getIntelligence() - INTELLEGENCE_DEBUFF_COEFFICIENT;

                usersMagicalMoveRepresentation.append("Шанс провала: Результат заклинания == " + LOSS_EDGE
                        + " = "
                        + this.spellTriggerRate
                        + " == "
                        + LOSS_EDGE
                        + " = временная потеря пункта интеллекта");
                usersMagicalMoveRepresentation.append("\n");
                usersMagicalMoveRepresentation.append(
                        "Новый интеллект игрока (на ход) = Интеллект игрока - " + INTELLEGENCE_DEBUFF_COEFFICIENT
                                + " = "
                                + this.hero.getIntelligence()
                                + " - "
                                + INTELLEGENCE_DEBUFF_COEFFICIENT
                                + " = "
                                + intellegenceWithDebuff);
            } else {
                usersMagicalMoveRepresentation.append("Шанс провала: Результат заклинания == " + LOSS_EDGE
                        + " = "
                        + this.spellTriggerRate
                        + " != "
                        + LOSS_EDGE
                        + " = нет временной потеря пункта интеллекта");
            }

            usersMagicalMoveRepresentation.append("\n");
            usersMagicalMoveRepresentation.append(
                    "Новое здоровье игрока: Здоровье игрока - Урон по разуму = " + this.oldHeroHealth
                            + " - "
                            + this.heroIntelligenceDamage
                            + " = "
                            + this.hero.getCurrentHP());
            usersMagicalMoveRepresentation.append("\n");
            usersMagicalMoveRepresentation.append(
                    "Новая мана игрока = Мана игрока - Сложность локации = " + this.oldHeroMana
                            + " - "
                            + this.theDifficultyLevelOfTheLocation
                            + " = "
                            + this.hero.getManaPoints());
            usersMagicalMoveRepresentation.append("\n");
        }

        usersMagicalMoveRepresentation.append("```");

        return usersMagicalMoveRepresentation.toString();
    }
}
