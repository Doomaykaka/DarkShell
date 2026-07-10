package dark_shell.models.fantasy;

import dark_shell.utils.SupportFunctions;
import java.util.List;

public class Location {

    private long currentHeroLevel;
    private long basicDifficultyCoefficientOfTheLocation;
    private long floatingCoefficient;
    private long theDifficultyLevelOfTheLocation;
    private String currentDescription;
    private String safeZoneDescription;
    private String usualAreaDescription;
    private String dangerZoneDescription;
    private String theDeadlyDangerZoneDescription;

    private final int SAFE_ZONE_DESCRIPTION_INDEX = 0;
    private final int USUAL_AREA_DESCRIPTION_INDEX = 1;
    private final int DANGER_ZONE_DESCRIPTION_INDEX = 2;

    private final int BASIC_DIFFICULTY_RATIO_MULTIPLIER = 2;

    public Location(long currentHeroLevel, List<String> zoneDescriptions) {
        this.currentHeroLevel = currentHeroLevel;
        this.safeZoneDescription = zoneDescriptions.get(SAFE_ZONE_DESCRIPTION_INDEX);
        this.usualAreaDescription = zoneDescriptions.get(USUAL_AREA_DESCRIPTION_INDEX);
        this.dangerZoneDescription = zoneDescriptions.get(DANGER_ZONE_DESCRIPTION_INDEX);
    }

    public long calculateDifficultyLevel() {
        this.basicDifficultyCoefficientOfTheLocation = this.currentHeroLevel * BASIC_DIFFICULTY_RATIO_MULTIPLIER;
        this.floatingCoefficient = SupportFunctions.randomAtOneToTen();
        this.theDifficultyLevelOfTheLocation = this.basicDifficultyCoefficientOfTheLocation + this.floatingCoefficient;

        if (this.theDifficultyLevelOfTheLocation <= 5) {
            this.currentDescription = safeZoneDescription;
        } else if (this.theDifficultyLevelOfTheLocation <= 12) {
            this.currentDescription = usualAreaDescription;
        } else if (this.theDifficultyLevelOfTheLocation <= 20) {
            this.currentDescription = dangerZoneDescription;
        } else {
            this.currentDescription = theDeadlyDangerZoneDescription;
        }

        return this.theDifficultyLevelOfTheLocation;
    }

    public long getCurrentHeroLevel() {
        return currentHeroLevel;
    }

    public void setCurrentHeroLevel(long currentHeroLevel) {
        this.currentHeroLevel = currentHeroLevel;
    }

    public long getTheDifficultyLevelOfTheLocation() {
        return theDifficultyLevelOfTheLocation;
    }

    public String getCurrentDescription() {
        return currentDescription;
    }

    @Override
    public String toString() {
        StringBuilder locationRepresentation = new StringBuilder();

        locationRepresentation.append("```");
        locationRepresentation.append("\n");
        locationRepresentation.append("Текущий уровень: " + this.getCurrentHeroLevel());
        locationRepresentation.append("\n");
        locationRepresentation.append(
                "Базовый коэффициент сложности локации: Текущий уровень * " + BASIC_DIFFICULTY_RATIO_MULTIPLIER
                        + " = "
                        + this.getCurrentHeroLevel()
                        + " * "
                        + BASIC_DIFFICULTY_RATIO_MULTIPLIER
                        + " = "
                        + this.basicDifficultyCoefficientOfTheLocation);
        locationRepresentation.append("\n");
        locationRepresentation.append("Сложность локации: Базовый коэффициент сложности локации + rand(1 - 10) = "
                + this.basicDifficultyCoefficientOfTheLocation
                + " + "
                + this.floatingCoefficient
                + " = "
                + this.theDifficultyLevelOfTheLocation);
        locationRepresentation.append("\n");
        locationRepresentation.append(this.getCurrentDescription());
        locationRepresentation.append("\n");
        locationRepresentation.append("```");

        return locationRepresentation.toString();
    }
}
