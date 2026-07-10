package dark_shell.models.cyberpunk;

public class Reward {

    private Location location;
    private Hero hero;
    private long experience;
    private long yen;

    private final int EXPERIENCE_MULTIPLIER = 10;
    private final int YEN_MULTIPLIER = 2;

    public Reward(Location location, Hero hero) {
        this.location = location;
        this.hero = hero;
    }

    public void giveReward() {
        this.experience = location.getTheSecuritySystemLevelOfTheLocation() * EXPERIENCE_MULTIPLIER;
        this.yen = location.getTheSecuritySystemLevelOfTheLocation() * YEN_MULTIPLIER;

        this.hero.giveExperience(this.experience);
        this.hero.giveYen(this.yen);
    }

    @Override
    public String toString() {
        StringBuilder rewardRepresentation = new StringBuilder();

        rewardRepresentation.append("```");
        rewardRepresentation.append("\n");
        rewardRepresentation.append("Опыт: Уровень опасности локации * " + EXPERIENCE_MULTIPLIER
                + " = "
                + location.getTheSecuritySystemLevelOfTheLocation()
                + " * "
                + EXPERIENCE_MULTIPLIER
                + " = "
                + this.experience);
        rewardRepresentation.append("\n");
        rewardRepresentation.append("Деньги: Уровень опасности локации * " + YEN_MULTIPLIER
                + " = "
                + location.getTheSecuritySystemLevelOfTheLocation()
                + " * "
                + YEN_MULTIPLIER
                + " = "
                + this.yen);
        rewardRepresentation.append("\n");
        rewardRepresentation.append("```");

        return rewardRepresentation.toString();
    }
}
