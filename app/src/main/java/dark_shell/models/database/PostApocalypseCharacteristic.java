package dark_shell.models.database;

import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "post_apocalypse_characteristics")
public class PostApocalypseCharacteristic {
    @Id
    @TableGenerator(
            name = "post_apocalypse_characteristic_gen",
            table = "post_apocalypse_characteristic_gen",
            pkColumnName = "gen_name",
            valueColumnName = "gen_val",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "post_apocalypse_characteristic_gen")
    private Long id;

    @NotNull @Column(nullable = false)
    private Long level;

    @NotNull @Column(nullable = false)
    private Long currentHP;

    @NotNull @Column(nullable = false)
    private Long maxHP;

    @NotNull @Column(nullable = false)
    private Long attack;

    @NotNull @Column(nullable = false)
    private Long defence;

    @NotNull @Column(nullable = false)
    private Long speed;

    @NotNull @Column(nullable = false)
    private Long luck;

    @NotNull @Column(nullable = false)
    private Long gold;

    @NotNull @Column(nullable = false)
    private Long experience;

    @NotNull @Column(nullable = false)
    private Long experienceToNextLevel;

    public PostApocalypseCharacteristic() {}

    public PostApocalypseCharacteristic(
            Long level,
            Long currentHP,
            Long maxHP,
            Long attack,
            Long defence,
            Long speed,
            Long luck,
            Long gold,
            Long experience,
            Long experienceToNextLevel) {
        final Long DEFAULT_ID = null;

        this.id = DEFAULT_ID;
        this.level = level;
        this.currentHP = currentHP;
        this.maxHP = maxHP;
        this.attack = attack;
        this.defence = defence;
        this.speed = speed;
        this.luck = luck;
        this.gold = gold;
        this.experience = experience;
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

    public Long getAttack() {
        return attack;
    }

    public void setAttack(Long attack) {
        this.attack = attack;
    }

    public Long getDefence() {
        return defence;
    }

    public void setDefence(Long defence) {
        this.defence = defence;
    }

    public Long getSpeed() {
        return speed;
    }

    public void setSpeed(Long speed) {
        this.speed = speed;
    }

    public Long getLuck() {
        return luck;
    }

    public void setLuck(Long luck) {
        this.luck = luck;
    }

    public Long getGold() {
        return gold;
    }

    public void setGold(Long gold) {
        this.gold = gold;
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
        PostApocalypseCharacteristic that = (PostApocalypseCharacteristic) o;
        return Objects.equals(id, that.id)
                && Objects.equals(level, that.level)
                && Objects.equals(currentHP, that.currentHP)
                && Objects.equals(maxHP, that.maxHP)
                && Objects.equals(attack, that.attack)
                && Objects.equals(defence, that.defence)
                && Objects.equals(speed, that.speed)
                && Objects.equals(luck, that.luck)
                && Objects.equals(gold, that.gold)
                && Objects.equals(experience, that.experience)
                && Objects.equals(experienceToNextLevel, that.experienceToNextLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, level, currentHP, maxHP, attack, defence, speed, luck, gold, experience, experienceToNextLevel);
    }
}
