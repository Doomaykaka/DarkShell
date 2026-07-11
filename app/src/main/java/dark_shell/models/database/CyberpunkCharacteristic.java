package dark_shell.models.database;

import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cyberpunk_characteristics")
public class CyberpunkCharacteristic {
    @Id
    @TableGenerator(
            name = "cyberpunk_characteristic_gen",
            table = "cyberpunk_characteristic_gen",
            pkColumnName = "gen_name",
            valueColumnName = "gen_val",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "cyberpunk_characteristic_gen")
    private Long id;

    @NotNull @Column(nullable = false)
    private Long level;

    @NotNull @Column(nullable = false)
    private Long currentHP;

    @NotNull @Column(nullable = false)
    private Long maxHP;

    @NotNull @Column(nullable = false)
    private Long infiltration;

    @NotNull @Column(nullable = false)
    private Long technic;

    @NotNull @Column(nullable = false)
    private Long cool;

    @NotNull @Column(nullable = false)
    private Long reputation;

    @NotNull @Column(nullable = false)
    private Long noise;

    @NotNull @Column(nullable = false)
    private Long yen;

    @NotNull @Column(nullable = false)
    private Long experience;

    @NotNull @Column(nullable = false)
    private Long experienceToNextLevel;

    public CyberpunkCharacteristic() {}

    public CyberpunkCharacteristic(
            Long level,
            Long currentHP,
            Long maxHP,
            Long infiltration,
            Long technic,
            Long cool,
            Long reputation,
            Long noise,
            Long experience,
            Long yen,
            Long experienceToNextLevel) {
        final Long DEFAULT_ID = null;

        this.id = DEFAULT_ID;
        this.level = level;
        this.currentHP = currentHP;
        this.maxHP = maxHP;
        this.infiltration = infiltration;
        this.technic = technic;
        this.cool = cool;
        this.reputation = reputation;
        this.noise = noise;
        this.experience = experience;
        this.yen = yen;
        this.experienceToNextLevel = experienceToNextLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(Long currentHP) {
        this.currentHP = currentHP;
    }

    public Long getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(Long maxHP) {
        this.maxHP = maxHP;
    }

    public Long getInfiltration() {
        return infiltration;
    }

    public void setInfiltration(Long infiltration) {
        this.infiltration = infiltration;
    }

    public Long getTechnic() {
        return technic;
    }

    public void setTechnic(Long technic) {
        this.technic = technic;
    }

    public Long getCool() {
        return cool;
    }

    public void setCool(Long cool) {
        this.cool = cool;
    }

    public Long getReputation() {
        return reputation;
    }

    public void setReputation(Long reputation) {
        this.reputation = reputation;
    }

    public Long getNoise() {
        return noise;
    }

    public void setNoise(Long noise) {
        this.noise = noise;
    }

    public Long getYen() {
        return yen;
    }

    public void setYen(Long yen) {
        this.yen = yen;
    }

    public Long getExperience() {
        return experience;
    }

    public void setExperience(Long experience) {
        this.experience = experience;
    }

    public Long getExperienceToNextLevel() {
        return experienceToNextLevel;
    }

    public void setExperienceToNextLevel(Long experienceToNextLevel) {
        this.experienceToNextLevel = experienceToNextLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CyberpunkCharacteristic that = (CyberpunkCharacteristic) o;
        return Objects.equals(id, that.id)
                && Objects.equals(level, that.level)
                && Objects.equals(currentHP, that.currentHP)
                && Objects.equals(maxHP, that.maxHP)
                && Objects.equals(infiltration, that.infiltration)
                && Objects.equals(technic, that.technic)
                && Objects.equals(cool, that.cool)
                && Objects.equals(reputation, that.reputation)
                && Objects.equals(noise, that.noise)
                && Objects.equals(yen, that.yen)
                && Objects.equals(experience, that.experience)
                && Objects.equals(experienceToNextLevel, that.experienceToNextLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                level,
                currentHP,
                maxHP,
                infiltration,
                technic,
                cool,
                reputation,
                noise,
                yen,
                experience,
                experienceToNextLevel);
    }
}
