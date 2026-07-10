package dark_shell.models.post_apocalypse;

public class Hero {

    private long level;
    private long currentHP;
    private long maxHP;
    private long attack;
    private long defense;
    private long speed;
    private long luck;
    private long experience;
    private long experienceToNextLevel;
    private long gold;

    private static final long START_EXPERIENCE_TO_NEXT_LEVEL_VALUE = 100;
    private static final double EXPERIENCE_TO_NEXT_LEVEL_MULTIPLIER = 1.5;
    private static final double MIN_HP = 0;
    private static final double MIN_DAMAGE = 0;
    private static final double MIN_EXPERIENCE = 0;
    private static final double MIN_GOLD = 0;

    public Hero() {
        this.level = 1;
        this.currentHP = 100;
        this.maxHP = 100;
        this.attack = 10;
        this.defense = 5;
        this.speed = 8;
        this.luck = 3;
        this.experience = 0;
        this.experienceToNextLevel = calculateExperienceToNextLevel(level);
        this.gold = 0;
    }

    public Hero(
            long level,
            long currentHP,
            long maxHP,
            long attack,
            long defense,
            long speed,
            long luck,
            long experience,
            long gold) {
        this.level = level;
        this.currentHP = currentHP;
        this.maxHP = maxHP;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.luck = luck;
        this.experience = experience;
        this.experienceToNextLevel = calculateExperienceToNextLevel(level);
        this.gold = gold;
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

    public long getAttack() {
        return attack;
    }

    public long getDefense() {
        return defense;
    }

    public long getSpeed() {
        return speed;
    }

    public long getLuck() {
        return luck;
    }

    public long getExperience() {
        return experience;
    }

    public long getExperienceToNextLevel() {
        return experienceToNextLevel;
    }

    public long getGold() {
        return gold;
    }

    @Override
    public String toString() {
        StringBuilder heroRepresentation = new StringBuilder();

        heroRepresentation.append("```");
        heroRepresentation.append("\n");
        heroRepresentation.append("LEVEL: " + this.getLevel());
        heroRepresentation.append("\n");
        heroRepresentation.append("HP: " + this.currentHP + "/" + this.maxHP);
        heroRepresentation.append("\n");
        heroRepresentation.append("ATK: " + this.getAttack());
        heroRepresentation.append("\n");
        heroRepresentation.append("DEF: " + this.getDefense());
        heroRepresentation.append("\n");
        heroRepresentation.append("SPD: " + this.getSpeed());
        heroRepresentation.append("\n");
        heroRepresentation.append("LUCK: " + this.getLuck());
        heroRepresentation.append("\n");
        heroRepresentation.append("EXP: " + this.getExperience());
        heroRepresentation.append("\n");
        heroRepresentation.append("NEXT_LVL: " + this.getExperienceToNextLevel());
        heroRepresentation.append("\n");
        heroRepresentation.append("GOLD: " + this.getGold());
        heroRepresentation.append("\n");
        heroRepresentation.append("```");

        return heroRepresentation.toString();
    }
}
