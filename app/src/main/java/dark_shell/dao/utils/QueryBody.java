package dark_shell.dao.utils;

import javax.persistence.EntityManager;

public interface QueryBody {
    public void execute(EntityManager manager);
}
