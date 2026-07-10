package dark_shell.models.cyberpunk;

public class Hero {

    private long level;
    private long currentHP;
    private long maxHP;
    private long infiltration;
    private long technic;
    private long cool;
    private long reputation;
    private long noise;
    private long yen;
    private long experience;
    private long experienceToNextLevel;

    private Tactics currentTactics;

    private static final long START_EXPERIENCE_TO_NEXT_LEVEL_VALUE = 100;
    private static final double EXPERIENCE_TO_NEXT_LEVEL_MULTIPLIER = 1.4;
    private static final double MIN_HP = 0;
    private static final double MIN_DAMAGE = 0;
    private static final double MIN_EXPERIENCE = 0;
    private static final double MIN_YEN = 0;
    private static final double MIN_NOISE = 0;
    private static final double MIN_STRESS = 0;

    public Hero(Tactics currentTactics) {
        this.level = 1;
        this.currentHP = 100;
        this.maxHP = 100;
        this.infiltration = 10;
        this.technic = 5;
        this.cool = 8;
        this.reputation = 3;
        this.noise = 3;
        this.yen = 3;
        this.experience = 0;
        this.experienceToNextLevel = calculateExperienceToNextLevel(level);
        this.currentTactics = currentTactics;
    }

    public Hero(
            long level,
            long currentHP,
            long maxHP,
            long infiltration,
            long technic,
            long cool,
            long reputation,
            long noise,
            long yen,
            long experience,
            long experienceToNextLevel,
            Tactics currentTactics) {
        this.level = level;
        this.currentHP = currentHP;
        this.maxHP = maxHP;
        this.infiltration = infiltration;
        this.technic = technic;
        this.cool = cool;
        this.reputation = reputation;
        this.noise = noise;
        this.yen = yen;
        this.experience = experience;
        this.experienceToNextLevel = experienceToNextLevel;
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

    public void takeStressValue(long stressValue) {
        if (stressValue > MIN_STRESS) {
            this.cool = this.cool - stressValue;
        }
    }

    public boolean inPanic() {
        return this.cool <= MIN_STRESS;
    }

    public void addNoise(long noiseValue) {
        if (noiseValue > MIN_NOISE) {
            this.noise = this.noise + noiseValue;
        }
    }

    public void giveExperience(long additionalExperience) {
        if (additionalExperience > MIN_EXPERIENCE) {
            this.experience = this.experience + additionalExperience;
        }
    }

    public void giveYen(long additionalYen) {
        if (additionalYen > MIN_YEN) {
            this.yen = this.yen + additionalYen;
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

    public long getInfiltration() {
        return infiltration;
    }

    public long getTechnic() {
        return technic;
    }

    public long getCool() {
        return cool;
    }

    public long getReputation() {
        return reputation;
    }

    public long getNoise() {
        return noise;
    }

    public long getYen() {
        return yen;
    }

    public long getMaxHP() {
        return maxHP;
    }

    public long getExperience() {
        return experience;
    }

    public long getExperienceToNextLevel() {
        return experienceToNextLevel;
    }

    public Tactics getCurrentTactics() {
        return currentTactics;
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
        heroRepresentation.append("INF: " + this.getInfiltration());
        heroRepresentation.append("\n");
        heroRepresentation.append("TECH: " + this.getTechnic());
        heroRepresentation.append("\n");
        heroRepresentation.append("COOL: " + this.getCool());
        heroRepresentation.append("\n");
        heroRepresentation.append("REP: " + this.getReputation());
        heroRepresentation.append("\n");
        heroRepresentation.append("NOISE: " + this.getNoise());
        heroRepresentation.append("\n");
        heroRepresentation.append("EXP: " + this.getExperience());
        heroRepresentation.append("\n");
        heroRepresentation.append("NEXT_LVL: " + this.getExperienceToNextLevel());
        heroRepresentation.append("\n");
        heroRepresentation.append("YEN: " + this.getYen());
        heroRepresentation.append("\n");
        heroRepresentation.append("```");

        return heroRepresentation.toString();
    }

    public static enum Tactics {
        FIGHT,
        HACK,
    }
}
