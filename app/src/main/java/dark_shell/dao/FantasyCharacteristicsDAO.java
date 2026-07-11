package dark_shell.dao;

import dark_shell.dao.utils.EntityDAO;
import dark_shell.models.database.FantasyCharacteristic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FantasyCharacteristicsDAO extends EntityDAO<FantasyCharacteristic> {
    public FantasyCharacteristicsDAO(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    protected CriteriaQuery<FantasyCharacteristic> getSelectQuery(CriteriaBuilder builder, Long id) {
        CriteriaQuery<FantasyCharacteristic> preparedQuery = null;

        preparedQuery = builder.createQuery(FantasyCharacteristic.class);
        Root<FantasyCharacteristic> root = preparedQuery.from(FantasyCharacteristic.class);
        preparedQuery = preparedQuery.select(root);
        preparedQuery = preparedQuery.where(builder.equal(root.get("id"), id));

        return preparedQuery;
    }

    @Override
    protected CriteriaQuery<FantasyCharacteristic> getSelectAllQuery(CriteriaBuilder builder) {
        CriteriaQuery<FantasyCharacteristic> preparedQuery = null;

        preparedQuery = builder.createQuery(FantasyCharacteristic.class);
        Root<FantasyCharacteristic> root = preparedQuery.from(FantasyCharacteristic.class);
        preparedQuery = preparedQuery.select(root);

        return preparedQuery;
    }

    @Override
    protected FantasyCharacteristic searchEntity(FantasyCharacteristic entity, EntityManager manager) {
        return entity == null || entity.getId() == null
                ? null
                : manager.find(FantasyCharacteristic.class, entity.getId());
    }
}
