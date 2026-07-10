package dark_shell.models.cyberpunk;

import dark_shell.utils.SupportFunctions;
import java.util.List;

public class Location {

    private long currentHeroLevel;
    private long basicCoefficientOfTheSecuritySystemLevel;
    private long floatingCoefficient;
    private long theSecuritySystemLevelOfTheLocation;
    private String currentDescription;
    private String safeZoneDescription;
    private String usualAreaDescription;
    private String dangerZoneDescription;

    private final int SAFE_ZONE_DESCRIPTION_INDEX = 0;
    private final int USUAL_AREA_DESCRIPTION_INDEX = 1;
    private final int DANGER_ZONE_DESCRIPTION_INDEX = 2;

    private final int BASIC_SECURITY_SYSTEM_RATIO_MULTIPLIER = 3;

    public Location(long currentHeroLevel, List<String> zoneDescriptions) {
        this.currentHeroLevel = currentHeroLevel;
        this.safeZoneDescription = zoneDescriptions.get(SAFE_ZONE_DESCRIPTION_INDEX);
        this.usualAreaDescription = zoneDescriptions.get(USUAL_AREA_DESCRIPTION_INDEX);
        this.dangerZoneDescription = zoneDescriptions.get(DANGER_ZONE_DESCRIPTION_INDEX);
    }

    public long calculateSecuritySystemLevel() {
        this.basicCoefficientOfTheSecuritySystemLevel = this.currentHeroLevel * BASIC_SECURITY_SYSTEM_RATIO_MULTIPLIER;
        this.floatingCoefficient = SupportFunctions.randomAtOneToFifteen();
        this.theSecuritySystemLevelOfTheLocation =
                this.basicCoefficientOfTheSecuritySystemLevel + this.floatingCoefficient;

        if (this.theSecuritySystemLevelOfTheLocation <= 10) {
            this.currentDescription = safeZoneDescription;
        } else if (this.theSecuritySystemLevelOfTheLocation <= 20) {
            this.currentDescription = usualAreaDescription;
        } else {
            this.currentDescription = dangerZoneDescription;
        }

        return this.theSecuritySystemLevelOfTheLocation;
    }

    public long getCurrentHeroLevel() {
        return currentHeroLevel;
    }

    public void setCurrentHeroLevel(long currentHeroLevel) {
        this.currentHeroLevel = currentHeroLevel;
    }

    public long getBasicCoefficientOfTheSecuritySystemLevel() {
        return basicCoefficientOfTheSecuritySystemLevel;
    }

    public void setBasicCoefficientOfTheSecuritySystemLevel(long basicCoefficientOfTheSecuritySystemLevel) {
        this.basicCoefficientOfTheSecuritySystemLevel = basicCoefficientOfTheSecuritySystemLevel;
    }

    public long getFloatingCoefficient() {
        return floatingCoefficient;
    }

    public void setFloatingCoefficient(long floatingCoefficient) {
        this.floatingCoefficient = floatingCoefficient;
    }

    public long getTheSecuritySystemLevelOfTheLocation() {
        return theSecuritySystemLevelOfTheLocation;
    }

    public void setTheSecuritySystemLevelOfTheLocation(long theSecuritySystemLevelOfTheLocation) {
        this.theSecuritySystemLevelOfTheLocation = theSecuritySystemLevelOfTheLocation;
    }

    public String getCurrentDescription() {
        return currentDescription;
    }

    public void setCurrentDescription(String currentDescription) {
        this.currentDescription = currentDescription;
    }

    @Override
    public String toString() {
        StringBuilder locationRepresentation = new StringBuilder();

        locationRepresentation.append("```");
        locationRepresentation.append("\n");
        locationRepresentation.append("Текущий уровень: " + this.getCurrentHeroLevel());
        locationRepresentation.append("\n");
        locationRepresentation.append("Базовый коэффициент уровня системы безопасности: Текущий уровень * "
                + BASIC_SECURITY_SYSTEM_RATIO_MULTIPLIER
                + " = "
                + this.getCurrentHeroLevel()
                + " * "
                + BASIC_SECURITY_SYSTEM_RATIO_MULTIPLIER
                + " = "
                + this.getBasicCoefficientOfTheSecuritySystemLevel());
        locationRepresentation.append("\n");
        locationRepresentation.append(
                "Уровень системы безопасности в локации: Базовый коэффициент уровня системы безопасности + rand(1 - 15) = "
                        + this.getBasicCoefficientOfTheSecuritySystemLevel()
                        + " + "
                        + this.getFloatingCoefficient()
                        + " = "
                        + this.getTheSecuritySystemLevelOfTheLocation());
        locationRepresentation.append("\n");
        locationRepresentation.append(this.getCurrentDescription());
        locationRepresentation.append("\n");
        locationRepresentation.append("```");

        return locationRepresentation.toString();
    }
}
