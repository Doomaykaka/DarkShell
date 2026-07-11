package dark_shell.models.database;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;


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

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull @Column(nullable = false)
    private Date creationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "character", orphanRemoval = true)
    private List<CyberpunkCharacteristic> cyberpunkCharacteristics = new ArrayList<GenerationScript>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<Workspace> workspaces = new ArrayList<Workspace>();

    @Transient
    private static final boolean DEFAULT_USER_DETAILS_VALUE = false;

    @Transient
    private static final String USER_ROLE_NAME = "ROLE_USER";

    @Transient
    private static final long serialVersionUID = 1L;

    public Character() {}

    public Character(String login, String password, String gitAccountDataPath) {
        final Long DEFAULT_ID = null;

        this.id = DEFAULT_ID;

        this.login = login;
        this.password = password;
        this.gitAccountDataPath = gitAccountDataPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGitAccountDataPath() {
        return gitAccountDataPath;
    }

    public void setGitAccountDataPath(String gitAccountDataPath) {
        this.gitAccountDataPath = gitAccountDataPath;
    }

    public List<GenerationScript> getGenerationScripts() {
        return generationScripts;
    }

    public void setGenerationScripts(List<GenerationScript> generationScripts) {
        this.generationScripts = generationScripts;
    }

    public List<Workspace> getWorkspaces() {
        return workspaces;
    }

    public void setWorkspaces(List<Workspace> workspaces) {
        this.workspaces = workspaces;
    }

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(USER_ROLE_NAME));
    }

    @Override
    @Transient
    public String getUsername() {
        return login;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return DEFAULT_USER_DETAILS_VALUE;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return DEFAULT_USER_DETAILS_VALUE;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return DEFAULT_USER_DETAILS_VALUE;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return DEFAULT_USER_DETAILS_VALUE;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((gitAccountDataPath == null) ? 0 : gitAccountDataPath.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null) return false;
        } else if (!id.equals(other.id)) return false;
        if (login == null) {
            if (other.login != null) return false;
        } else if (!login.equals(other.login)) return false;
        if (password == null) {
            if (other.password != null) return false;
        } else if (!password.equals(other.password)) return false;
        if (gitAccountDataPath == null) {
            if (other.gitAccountDataPath != null) return false;
        } else if (!gitAccountDataPath.equals(other.gitAccountDataPath)) return false;
        if (generationScripts == null) {
            if (other.generationScripts != null) return false;
        } else if (!generationScripts.equals(other.generationScripts)) return false;
        if (workspaces == null) {
            if (other.workspaces != null) return false;
        } else if (!workspaces.equals(other.workspaces)) return false;
        return true;
    }
}
