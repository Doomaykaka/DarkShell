package dark_shell.models.post_apocalypse;

import dark_shell.utils.SupportFunctions;

public class Reward {

    private Location location;
    private Hero hero;
    private long experience;
    private long gold;
    private long theChanceOfGettingAnItem;
    private boolean itemFound;

    private final int EXPERIENCE_MULTIPLIER = 10;
    private final int GOLD_MULTIPLIER = 2;
    private final int ITEM_FOUND_EDGE = 6;

    public Reward(Location location, Hero hero) {
        this.location = location;
        this.hero = hero;
    }

    public void giveReward() {
        this.experience = location.getTheDangerLevelOfTheLocation() * EXPERIENCE_MULTIPLIER;
        this.gold = location.getTheDangerLevelOfTheLocation() * GOLD_MULTIPLIER;
        this.theChanceOfGettingAnItem = SupportFunctions.randomAtOneToTwenty();
        this.itemFound = this.theChanceOfGettingAnItem < ITEM_FOUND_EDGE;

        this.hero.giveExperience(this.experience);
        this.hero.giveGold(this.gold);
    }

    public boolean isItemFound() {
        return itemFound;
    }

    @Override
    public String toString() {
        StringBuilder rewardRepresentation = new StringBuilder();

        rewardRepresentation.append("```");
        rewardRepresentation.append("\n");
        rewardRepresentation.append("Опыт: Уровень опасности локации * " + EXPERIENCE_MULTIPLIER
                + " = "
                + location.getTheDangerLevelOfTheLocation()
                + " * "
                + EXPERIENCE_MULTIPLIER
                + " = "
                + this.experience);
        rewardRepresentation.append("\n");
        rewardRepresentation.append("Золото: Уровень опасности локации * " + GOLD_MULTIPLIER
                + " = "
                + location.getTheDangerLevelOfTheLocation()
                + " * "
                + GOLD_MULTIPLIER
                + " = "
                + this.gold);
        rewardRepresentation.append("\n");
        if (itemFound) {
            rewardRepresentation.append("Предметы: rand(1 - 20) < " + ITEM_FOUND_EDGE
                    + " = "
                    + this.theChanceOfGettingAnItem
                    + " < "
                    + ITEM_FOUND_EDGE
                    + " = предмет найден");
        } else {
            rewardRepresentation.append("Предметы: rand(1 - 20) < " + ITEM_FOUND_EDGE
                    + " = "
                    + this.theChanceOfGettingAnItem
                    + " !< "
                    + ITEM_FOUND_EDGE
                    + " = предметы не найдены");
        }
        rewardRepresentation.append("\n");
        rewardRepresentation.append("```");

        return rewardRepresentation.toString();
    }
}
