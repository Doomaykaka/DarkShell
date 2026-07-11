package dark_shell.models.database;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "characters")
public class Character {
    @Id
    @TableGenerator(
            name = "character_gen",
            table = "character_gen",
            pkColumnName = "gen_name",
            valueColumnName = "gen_val",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "character_gen")
    private Long id;

    @NotNull @Column(nullable = false)
    private String name;

    @NotNull @Column(nullable = false)
    private Date creationDate;

    @NotNull @OneToOne
    private CyberpunkCharacteristic cyberpunkCharacteristics;

    @NotNull @OneToOne
    private FantasyCharacteristic fantasyCharacteristics;

    @NotNull @OneToOne
    private PostApocalypseCharacteristic postApocalypseCharacteristics;

    @NotNull @OneToOne
    private Statistic statistic;

    public Character() {}

    public Character(
            String name,
            Date creationDate,
            CyberpunkCharacteristic cyberpunkCharacteristics,
            FantasyCharacteristic fantasyCharacteristics,
            PostApocalypseCharacteristic postApocalypseCharacteristics,
            Statistic statistic) {
        final Long DEFAULT_ID = null;

        this.id = DEFAULT_ID;
        this.name = name;
        this.creationDate = creationDate;
        this.cyberpunkCharacteristics = cyberpunkCharacteristics;
        this.fantasyCharacteristics = fantasyCharacteristics;
        this.postApocalypseCharacteristics = postApocalypseCharacteristics;
        this.statistic = statistic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public CyberpunkCharacteristic getCyberpunkCharacteristics() {
        return cyberpunkCharacteristics;
    }

    public void setCyberpunkCharacteristics(CyberpunkCharacteristic cyberpunkCharacteristics) {
        this.cyberpunkCharacteristics = cyberpunkCharacteristics;
    }

    public FantasyCharacteristic getFantasyCharacteristics() {
        return fantasyCharacteristics;
    }

    public void setFantasyCharacteristics(FantasyCharacteristic fantasyCharacteristics) {
        this.fantasyCharacteristics = fantasyCharacteristics;
    }

    public PostApocalypseCharacteristic getPostApocalypseCharacteristics() {
        return postApocalypseCharacteristics;
    }

    public void setPostApocalypseCharacteristics(PostApocalypseCharacteristic postApocalypseCharacteristics) {
        this.postApocalypseCharacteristics = postApocalypseCharacteristics;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return Objects.equals(id, character.id)
                && Objects.equals(name, character.name)
                && Objects.equals(creationDate, character.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creationDate);
    }
}
