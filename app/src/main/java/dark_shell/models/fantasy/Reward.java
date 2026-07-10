package dark_shell.models.fantasy;

public class Reward {

    private Location location;
    private Hero hero;
    private long experience;
    private long gold;

    private final int EXPERIENCE_MULTIPLIER = 10;
    private final int GOLD_MULTIPLIER = 2;

    public Reward(Location location, Hero hero) {
        this.location = location;
        this.hero = hero;
    }

    public void giveReward() {
        this.experience = location.getTheDifficultyLevelOfTheLocation() * EXPERIENCE_MULTIPLIER;
        this.gold = location.getTheDifficultyLevelOfTheLocation() * GOLD_MULTIPLIER;

        this.hero.giveExperience(this.experience);
        this.hero.giveGold(this.gold);
    }

    @Override
    public String toString() {
        StringBuilder rewardRepresentation = new StringBuilder();

        rewardRepresentation.append("```");
        rewardRepresentation.append("\n");
        rewardRepresentation.append("Опыт: Уровень сложности локации * " + EXPERIENCE_MULTIPLIER
                + " = "
                + location.getTheDifficultyLevelOfTheLocation()
                + " * "
                + EXPERIENCE_MULTIPLIER
                + " = "
                + this.experience);
        rewardRepresentation.append("\n");
        rewardRepresentation.append("Золото: Уровень сложности локации * " + GOLD_MULTIPLIER
                + " = "
                + location.getTheDifficultyLevelOfTheLocation()
                + " * "
                + GOLD_MULTIPLIER
                + " = "
                + this.gold);
        rewardRepresentation.append("\n");
        rewardRepresentation.append("```");

        return rewardRepresentation.toString();
    }
}
