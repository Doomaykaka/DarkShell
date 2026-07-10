package dark_shell.models.fantasy;

public class Hero {

    private long level;
    private long currentHP;
    private long maxHP;
    private long manaPoints;
    private long maxManaPoints;
    private long strength;
    private long dexterity;
    private long intelligence;
    private long willpower;
    private long reputation;
    private long gold;
    private long encumbrance;
    private long maxEncumbrance;
    private long experience;
    private long experienceToNextLevel;

    private long royalGuildFactionPoints;
    private long mageGuildFactionPoints;
    private long thievesGuildFactionPoints;
    private long barbarianTribeFactionPoints;

    private Tactics currentTactics;

    private static final long START_EXPERIENCE_TO_NEXT_LEVEL_VALUE = 150;
    private static final double EXPERIENCE_TO_NEXT_LEVEL_MULTIPLIER = 1.3;
    private static final double MIN_HP = 0;
    private static final double MIN_DAMAGE = 0;
    private static final double MIN_EXPERIENCE = 0;
    private static final double MIN_GOLD = 0;
    private static final double MIN_MANA = 0;

    public Hero(Tactics currentTactics) {
        this.level = 1;
        this.currentHP = 100;
        this.maxHP = 100;
        this.manaPoints = 80;
        this.maxManaPoints = 80;
        this.strength = 10;
        this.dexterity = 10;
        this.intelligence = 8;
        this.willpower = 8;
        this.reputation = 0;
        this.gold = 0;
        this.encumbrance = 0;
        this.maxEncumbrance = 50;
        this.experience = 0;
        this.experienceToNextLevel = calculateExperienceToNextLevel(level);

        this.royalGuildFactionPoints = 0;
        this.mageGuildFactionPoints = 0;
        this.thievesGuildFactionPoints = 0;
        this.barbarianTribeFactionPoints = 0;

        this.currentTactics = currentTactics;
    }

    public Hero(
            long level,
            long currentHP,
            long maxHP,
            long manaPoints,
            long maxManaPoints,
            long strength,
            long dexterity,
            long intelligence,
            long willpower,
            long reputation,
            long gold,
            long encumbrance,
            long maxEncumbrance,
            long experience,
            long experienceToNextLevel,
            long royalGuildFactionPoints,
            long mageGuildFactionPoints,
            long thievesGuildFactionPoints,
            long barbarianTribeFactionPoints,
            Tactics currentTactics) {
        this.level = level;
        this.currentHP = currentHP;
        this.maxHP = maxHP;
        this.manaPoints = manaPoints;
        this.maxManaPoints = maxManaPoints;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.willpower = willpower;
        this.reputation = reputation;
        this.gold = gold;
        this.encumbrance = encumbrance;
        this.maxEncumbrance = maxEncumbrance;
        this.experience = experience;
        this.experienceToNextLevel = experienceToNextLevel;

        this.royalGuildFactionPoints = royalGuildFactionPoints;
        this.mageGuildFactionPoints = mageGuildFactionPoints;
        this.thievesGuildFactionPoints = thievesGuildFactionPoints;
        this.barbarianTribeFactionPoints = barbarianTribeFactionPoints;

        this.currentTactics = currentTactics;
    }

    private long calculateExperienceToNextLevel(long level) {
        return Double.valueOf(
                        START_EXPERIENCE_TO_NEXT_LEVEL_VALUE * Math.pow(EXPERIENCE_TO_NEXT_LEVEL_MULTIPLIER, level - 1))
                .longValue();
    }

    public void takeDamage(long damageValue) {
        if (damageValue > MIN_DAMAGE) {
            this.currentHP = this.currentHP - damageValue;
        }
    }

    public void spendMana(long spendManaValue) {
        if (spendManaValue > MIN_MANA) {
            this.manaPoints = this.manaPoints - spendManaValue;
        }
    }

    public void giveExperience(long additionalExperience) {
        if (additionalExperience > MIN_EXPERIENCE) {
            this.experience = this.experience + additionalExperience;
        }
    }

    public void giveGold(long additionalGold) {
        if (additionalGold > MIN_GOLD) {
            this.gold = this.gold + additionalGold;
        }
    }

    public boolean isDead() {
        return this.currentHP <= MIN_HP;
    }

    public long getLevel() {
        return level;
    }

    public long getCurrentHP() {
        return currentHP;
    }

    public long getMaxHP() {
        return maxHP;
    }

    public long getManaPoints() {
        return manaPoints;
    }

    public long getMaxManaPoints() {
        return maxManaPoints;
    }

    public long getStrength() {
        return strength;
    }

    public long getDexterity() {
        return dexterity;
    }

    public long getIntelligence() {
        return intelligence;
    }

    public long getWillpower() {
        return willpower;
    }

    public long getReputation() {
        return reputation;
    }

    public long getGold() {
        return gold;
    }

    public long getEncumbrance() {
        return encumbrance;
    }

    public long getMaxEncumbrance() {
        return maxEncumbrance;
    }

    public long getExperience() {
        return experience;
    }

    public long getExperienceToNextLevel() {
        return experienceToNextLevel;
    }

    public long getRoyalGuildFactionPoints() {
        return royalGuildFactionPoints;
    }

    public long getMageGuildFactionPoints() {
        return mageGuildFactionPoints;
    }

    public long getThievesGuildFactionPoints() {
        return thievesGuildFactionPoints;
    }

    public long getBarbarianTribeFactionPoints() {
        return barbarianTribeFactionPoints;
    }

    public Tactics getCurrentTactics() {
        return currentTactics;
    }

    @Override
    public String toString() {
        StringBuilder heroRepresentation = new StringBuilder();

        heroRepresentation.append("```");
        heroRepresentation.append("\n");
        heroRepresentation.append("[СТАТУС]");
        heroRepresentation.append("\n");
        heroRepresentation.append("LEVEL: " + this.level);
        heroRepresentation.append("\n");
        heroRepresentation.append("EXP: " + this.getExperience()
                + " / "
                + this.experienceToNextLevel
                + " (OLD_MAX_EXP * "
                + EXPERIENCE_TO_NEXT_LEVEL_MULTIPLIER
                + ")");
        heroRepresentation.append("\n");
        heroRepresentation.append("\n");
        heroRepresentation.append("\n");
        heroRepresentation.append("[ХАРАКТЕРИСТИКИ]");
        heroRepresentation.append("\n");
        heroRepresentation.append("HP: " + this.getCurrentHP() + " / " + this.getMaxHP());
        heroRepresentation.append("\n");
        heroRepresentation.append("MP: " + this.getManaPoints() + " / " + this.getMaxManaPoints() + " (INT * 10)");
        heroRepresentation.append("\n");
        heroRepresentation.append("STR: " + this.getStrength());
        heroRepresentation.append("\n");
        heroRepresentation.append("DEX: " + this.getDexterity());
        heroRepresentation.append("\n");
        heroRepresentation.append("INT: " + this.getIntelligence());
        heroRepresentation.append("\n");
        heroRepresentation.append("WILL: " + this.getWillpower());
        heroRepresentation.append("\n");
        heroRepresentation.append("REP: " + this.getReputation());
        heroRepresentation.append("\n");
        heroRepresentation.append("GOLD: " + this.getGold());
        heroRepresentation.append("\n");
        heroRepresentation.append("ENC: " + this.getEncumbrance() + " / " + this.getMaxEncumbrance() + " (STR * 5)");
        heroRepresentation.append("\n");
        heroRepresentation.append("\n");
        heroRepresentation.append("\n");
        heroRepresentation.append("[ФРАКЦИИ]");
        heroRepresentation.append("\n");
        heroRepresentation.append("Королевская гвардия: " + this.getRoyalGuildFactionPoints());
        heroRepresentation.append("\n");
        heroRepresentation.append("Гильдия магов: " + this.getMageGuildFactionPoints());
        heroRepresentation.append("\n");
        heroRepresentation.append("Гильдия воров: " + this.getThievesGuildFactionPoints());
        heroRepresentation.append("\n");
        heroRepresentation.append("Племена варваров: " + this.getBarbarianTribeFactionPoints());
        heroRepresentation.append("\n");
        heroRepresentation.append("```");

        return heroRepresentation.toString();
    }

    public static enum Tactics {
        FIGHT,
        MAGIC,
    }
}
