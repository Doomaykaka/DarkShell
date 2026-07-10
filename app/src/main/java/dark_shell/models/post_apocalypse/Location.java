package dark_shell.models.post_apocalypse;

import dark_shell.utils.SupportFunctions;
import java.util.List;

public class Location {

    private long currentHeroLevel;
    private long basicHazardRatio;
    private long theDangerLevelOfTheLocation;
    private String currentDescription;
    private String safeZoneDescription;
    private String usualAreaDescription;
    private String dangerZoneDescription;
    private String theDeadlyDangerZoneDescription;

    private final int SAFE_ZONE_DESCRIPTION_INDEX = 0;
    private final int USUAL_AREA_DESCRIPTION_INDEX = 1;
    private final int DANGER_ZONE_DESCRIPTION_INDEX = 2;
    private final int THE_DEADLY_DANGER_ZONE_DESCRIPTION_INDEX = 3;

    private final int BASIC_HAZARD_RATIO_MULTIPLIER = 2;

    public Location(long currentHeroLevel, List<String> zoneDescriptions) {
        this.currentHeroLevel = currentHeroLevel;
        this.safeZoneDescription = zoneDescriptions.get(SAFE_ZONE_DESCRIPTION_INDEX);
        this.usualAreaDescription = zoneDescriptions.get(USUAL_AREA_DESCRIPTION_INDEX);
        this.dangerZoneDescription = zoneDescriptions.get(DANGER_ZONE_DESCRIPTION_INDEX);
        this.theDeadlyDangerZoneDescription = zoneDescriptions.get(THE_DEADLY_DANGER_ZONE_DESCRIPTION_INDEX);
    }

    public long calculateDangerLevel() {
        this.basicHazardRatio = this.currentHeroLevel * BASIC_HAZARD_RATIO_MULTIPLIER;
        this.theDangerLevelOfTheLocation = this.basicHazardRatio + SupportFunctions.randomAtOneToTen();

        if (this.theDangerLevelOfTheLocation <= 5) {
            this.currentDescription = safeZoneDescription;
        } else if (this.theDangerLevelOfTheLocation <= 10) {
            this.currentDescription = usualAreaDescription;
        } else if (this.theDangerLevelOfTheLocation <= 15) {
            this.currentDescription = dangerZoneDescription;
        } else {
            this.currentDescription = theDeadlyDangerZoneDescription;
        }

        return this.theDangerLevelOfTheLocation;
    }

    public String getCurrentDangerLevelDescription() {
        return this.currentDescription;
    }

    public long getCurrentHeroLevel() {
        return currentHeroLevel;
    }

    public long getBasicHazardRatio() {
        return basicHazardRatio;
    }

    public long getTheDangerLevelOfTheLocation() {
        return theDangerLevelOfTheLocation;
    }

    public String getCurrentDescription() {
        return currentDescription;
    }

    @Override
    public String toString() {
        StringBuilder locationRepresentation = new StringBuilder();

        long randomForTheDangerLevelOfTheLocation = this.getTheDangerLevelOfTheLocation() - this.getBasicHazardRatio();

        locationRepresentation.append("```");
        locationRepresentation.append("\n");
        locationRepresentation.append("Текущий уровень: " + this.getCurrentHeroLevel());
        locationRepresentation.append("\n");
        locationRepresentation.append(
                "Базовый коэффициент опасности: Текущий уровень * " + BASIC_HAZARD_RATIO_MULTIPLIER
                        + " = "
                        + this.getCurrentHeroLevel()
                        + " * "
                        + BASIC_HAZARD_RATIO_MULTIPLIER
                        + " = "
                        + this.getBasicHazardRatio());
        locationRepresentation.append("\n");
        locationRepresentation.append("Уровень опасности локации: Базовый коэффициент опасности + rand(1 - 10) = "
                + this.getBasicHazardRatio()
                + " + "
                + randomForTheDangerLevelOfTheLocation
                + " = "
                + this.getTheDangerLevelOfTheLocation());
        locationRepresentation.append("\n");
        locationRepresentation.append(this.getCurrentDescription());
        locationRepresentation.append("\n");
        locationRepresentation.append("```");

        return locationRepresentation.toString();
    }
}
