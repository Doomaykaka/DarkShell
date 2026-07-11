package dark_shell.dao;

import dark_shell.dao.utils.EntityDAO;
import dark_shell.models.database.CyberpunkCharacteristic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class CyberpunkCharacteristicsDAO extends EntityDAO<CyberpunkCharacteristic> {
    public CyberpunkCharacteristicsDAO(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    protected CriteriaQuery<CyberpunkCharacteristic> getSelectQuery(CriteriaBuilder builder, Long id) {
        CriteriaQuery<CyberpunkCharacteristic> preparedQuery = null;

        preparedQuery = builder.createQuery(CyberpunkCharacteristic.class);
        Root<CyberpunkCharacteristic> root = preparedQuery.from(CyberpunkCharacteristic.class);
        preparedQuery = preparedQuery.select(root);
        preparedQuery = preparedQuery.where(builder.equal(root.get("id"), id));

        return preparedQuery;
    }

    @Override
    protected CriteriaQuery<CyberpunkCharacteristic> getSelectAllQuery(CriteriaBuilder builder) {
        CriteriaQuery<CyberpunkCharacteristic> preparedQuery = null;

        preparedQuery = builder.createQuery(CyberpunkCharacteristic.class);
        Root<CyberpunkCharacteristic> root = preparedQuery.from(CyberpunkCharacteristic.class);
        preparedQuery = preparedQuery.select(root);

        return preparedQuery;
    }

    @Override
    protected CyberpunkCharacteristic searchEntity(CyberpunkCharacteristic entity, EntityManager manager) {
        return entity == null || entity.getId() == null
                ? null
                : manager.find(CyberpunkCharacteristic.class, entity.getId());
    }
}
