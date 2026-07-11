package dark_shell.dao;

import dark_shell.dao.utils.EntityDAO;
import dark_shell.models.database.PostApocalypseCharacteristic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class PostApocalypseCharacteristicsDAO extends EntityDAO<PostApocalypseCharacteristic> {
    public PostApocalypseCharacteristicsDAO(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    protected CriteriaQuery<PostApocalypseCharacteristic> getSelectQuery(CriteriaBuilder builder, Long id) {
        CriteriaQuery<PostApocalypseCharacteristic> preparedQuery = null;

        preparedQuery = builder.createQuery(PostApocalypseCharacteristic.class);
        Root<PostApocalypseCharacteristic> root = preparedQuery.from(PostApocalypseCharacteristic.class);
        preparedQuery = preparedQuery.select(root);
        preparedQuery = preparedQuery.where(builder.equal(root.get("id"), id));

        return preparedQuery;
    }

    @Override
    protected CriteriaQuery<PostApocalypseCharacteristic> getSelectAllQuery(CriteriaBuilder builder) {
        CriteriaQuery<PostApocalypseCharacteristic> preparedQuery = null;

        preparedQuery = builder.createQuery(PostApocalypseCharacteristic.class);
        Root<PostApocalypseCharacteristic> root = preparedQuery.from(PostApocalypseCharacteristic.class);
        preparedQuery = preparedQuery.select(root);

        return preparedQuery;
    }

    @Override
    protected PostApocalypseCharacteristic searchEntity(PostApocalypseCharacteristic entity, EntityManager manager) {
        return entity == null || entity.getId() == null
                ? null
                : manager.find(PostApocalypseCharacteristic.class, entity.getId());
    }
}
