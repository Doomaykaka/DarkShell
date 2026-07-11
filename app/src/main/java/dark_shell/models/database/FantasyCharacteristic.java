package dark_shell.models.database;

import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "fantasy_characteristics")
public class FantasyCharacteristic {
    @Id
    @TableGenerator(
            name = "fantasy_characteristic_gen",
            table = "fantasy_characteristic_gen",
            pkColumnName = "gen_name",
            valueColumnName = "gen_val",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "fantasy_characteristic_gen")
    private Long id;

    @NotNull @Column(nullable = false)
    private Long level;

    @NotNull @Column(nullable = false)
    private Long currentHP;

    @NotNull @Column(nullable = false)
    private Long maxHP;

    @NotNull @Column(nullable = false)
    private Long manaPoints;

    @NotNull @Column(nullable = false)
    private Long maxManaPoints;

    @NotNull @Column(nullable = false)
    private Long strength;

    @NotNull @Column(nullable = false)
    private Long dexterity;

    @NotNull @Column(nullable = false)
    private Long intelligence;

    @NotNull @Column(nullable = false)
    private Long willpower;

    @NotNull @Column(nullable = false)
    private Long reputation;

    @NotNull @Column(nullable = false)
    private Long gold;

    @NotNull @Column(nullable = false)
    private Long encumbrance;

    @NotNull @Column(nullable = false)
    private Long maxEncumbrance;

    @NotNull @Column(nullable = false)
    private Long experience;

    @NotNull @Column(nullable = false)
    private Long experienceToNextLevel;

    @NotNull @Column(nullable = false)
    private Long royalGuildFactionPoints;

    @NotNull @Column(nullable = false)
    private Long mageGuildFactionPoints;

    @NotNull @Column(nullable = false)
    private Long thievesGuildFactionPoints;

    @NotNull @Column(nullable = false)
    private Long barbarianTribeFactionPoints;

    public FantasyCharacteristic() {}

    public FantasyCharacteristic(
            Long level,
            Long currentHP,
            Long maxHP,
            Long manaPoints,
            Long maxManaPoints,
            Long strength,
            Long dexterity,
            Long intelligence,
            Long willpower,
            Long reputation,
            Long gold,
            Long encumbrance,
            Long maxEncumbrance,
            Long experience,
            Long experienceToNextLevel,
            Long royalGuildFactionPoints,
            Long mageGuildFactionPoints,
            Long thievesGuildFactionPoints,
            Long barbarianTribeFactionPoints) {
        final Long DEFAULT_ID = null;

        this.id = DEFAULT_ID;
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

    public Long getManaPoints() {
        return manaPoints;
    }

    public void setManaPoints(Long manaPoints) {
        this.manaPoints = manaPoints;
    }

    public Long getMaxManaPoints() {
        return maxManaPoints;
    }

    public void setMaxManaPoints(Long maxManaPoints) {
        this.maxManaPoints = maxManaPoints;
    }

    public Long getStrength() {
        return strength;
    }

    public void setStrength(Long strength) {
        this.strength = strength;
    }

    public Long getDexterity() {
        return dexterity;
    }

    public void setDexterity(Long dexterity) {
        this.dexterity = dexterity;
    }

    public Long getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(Long intelligence) {
        this.intelligence = intelligence;
    }

    public Long getWillpower() {
        return willpower;
    }

    public void setWillpower(Long willpower) {
        this.willpower = willpower;
    }

    public Long getReputation() {
        return reputation;
    }

    public void setReputation(Long reputation) {
        this.reputation = reputation;
    }

    public Long getGold() {
        return gold;
    }

    public void setGold(Long gold) {
        this.gold = gold;
    }

    public Long getEncumbrance() {
        return encumbrance;
    }

    public void setEncumbrance(Long encumbrance) {
        this.encumbrance = encumbrance;
    }

    public Long getMaxEncumbrance() {
        return maxEncumbrance;
    }

    public void setMaxEncumbrance(Long maxEncumbrance) {
        this.maxEncumbrance = maxEncumbrance;
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

    public Long getRoyalGuildFactionPoints() {
        return royalGuildFactionPoints;
    }

    public void setRoyalGuildFactionPoints(Long royalGuildFactionPoints) {
        this.royalGuildFactionPoints = royalGuildFactionPoints;
    }

    public Long getMageGuildFactionPoints() {
        return mageGuildFactionPoints;
    }

    public void setMageGuildFactionPoints(Long mageGuildFactionPoints) {
        this.mageGuildFactionPoints = mageGuildFactionPoints;
    }

    public Long getThievesGuildFactionPoints() {
        return thievesGuildFactionPoints;
    }

    public void setThievesGuildFactionPoints(Long thievesGuildFactionPoints) {
        this.thievesGuildFactionPoints = thievesGuildFactionPoints;
    }

    public Long getBarbarianTribeFactionPoints() {
        return barbarianTribeFactionPoints;
    }

    public void setBarbarianTribeFactionPoints(Long barbarianTribeFactionPoints) {
        this.barbarianTribeFactionPoints = barbarianTribeFactionPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FantasyCharacteristic that = (FantasyCharacteristic) o;
        return Objects.equals(id, that.id)
                && Objects.equals(level, that.level)
                && Objects.equals(currentHP, that.currentHP)
                && Objects.equals(maxHP, that.maxHP)
                && Objects.equals(manaPoints, that.manaPoints)
                && Objects.equals(maxManaPoints, that.maxManaPoints)
                && Objects.equals(strength, that.strength)
                && Objects.equals(dexterity, that.dexterity)
                && Objects.equals(intelligence, that.intelligence)
                && Objects.equals(willpower, that.willpower)
                && Objects.equals(reputation, that.reputation)
                && Objects.equals(gold, that.gold)
                && Objects.equals(encumbrance, that.encumbrance)
                && Objects.equals(maxEncumbrance, that.maxEncumbrance)
                && Objects.equals(experience, that.experience)
                && Objects.equals(experienceToNextLevel, that.experienceToNextLevel)
                && Objects.equals(royalGuildFactionPoints, that.royalGuildFactionPoints)
                && Objects.equals(mageGuildFactionPoints, that.mageGuildFactionPoints)
                && Objects.equals(thievesGuildFactionPoints, that.thievesGuildFactionPoints)
                && Objects.equals(barbarianTribeFactionPoints, that.barbarianTribeFactionPoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                level,
                currentHP,
                maxHP,
                manaPoints,
                maxManaPoints,
                strength,
                dexterity,
                intelligence,
                willpower,
                reputation,
                gold,
                encumbrance,
                maxEncumbrance,
                experience,
                experienceToNextLevel,
                royalGuildFactionPoints,
                mageGuildFactionPoints,
                thievesGuildFactionPoints,
                barbarianTribeFactionPoints);
    }
}
