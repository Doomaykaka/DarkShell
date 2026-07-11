package dark_shell.models.database;

import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "statistics")
public class Statistic {
    @Id
    @TableGenerator(
            name = "statistic_gen",
            table = "statistic_gen",
            pkColumnName = "gen_name",
            valueColumnName = "gen_val",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "statistic_gen")
    private Long id;

    @NotNull @Column(nullable = false)
    private Date lastPlayDate;

    @NotNull @Column(nullable = false)
    private Long daysInGame;

    @NotNull @Column(nullable = false)
    private Long eventsConducted;

    @NotNull @Column(nullable = false)
    private Long numberOfSuccessfulEvents;

    @NotNull @Column(nullable = false)
    private Long maxLevel;

    @NotNull @Column(nullable = false)
    private Long maxExperience;

    public Statistic() {}

    public Statistic(
            Date lastPlayDate,
            long daysInGame,
            long eventsConducted,
            long numberOfSuccessfulEvents,
            long maxLevel,
            long maxExperience) {
        final Long DEFAULT_ID = null;

        this.id = DEFAULT_ID;
        this.lastPlayDate = lastPlayDate;
        this.daysInGame = daysInGame;
        this.eventsConducted = eventsConducted;
        this.numberOfSuccessfulEvents = numberOfSuccessfulEvents;
        this.maxLevel = maxLevel;
        this.maxExperience = maxExperience;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLastPlayDate() {
        return lastPlayDate;
    }

    public void setLastPlayDate(Date lastPlayDate) {
        this.lastPlayDate = lastPlayDate;
    }

    public Long getDaysInGame() {
        return daysInGame;
    }

    public void setDaysInGame(Long daysInGame) {
        this.daysInGame = daysInGame;
    }

    public Long getEventsConducted() {
        return eventsConducted;
    }

    public void setEventsConducted(Long eventsConducted) {
        this.eventsConducted = eventsConducted;
    }

    public Long getNumberOfSuccessfulEvents() {
        return numberOfSuccessfulEvents;
    }

    public void setNumberOfSuccessfulEvents(Long numberOfSuccessfulEvents) {
        this.numberOfSuccessfulEvents = numberOfSuccessfulEvents;
    }

    public Long getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(Long maxLevel) {
        this.maxLevel = maxLevel;
    }

    public Long getMaxExperience() {
        return maxExperience;
    }

    public void setMaxExperience(Long maxExperience) {
        this.maxExperience = maxExperience;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Statistic statistic = (Statistic) o;
        return Objects.equals(id, statistic.id)
                && Objects.equals(lastPlayDate, statistic.lastPlayDate)
                && Objects.equals(daysInGame, statistic.daysInGame)
                && Objects.equals(eventsConducted, statistic.eventsConducted)
                && Objects.equals(numberOfSuccessfulEvents, statistic.numberOfSuccessfulEvents)
                && Objects.equals(maxLevel, statistic.maxLevel)
                && Objects.equals(maxExperience, statistic.maxExperience);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, lastPlayDate, daysInGame, eventsConducted, numberOfSuccessfulEvents, maxLevel, maxExperience);
    }
}
