package dark_shell.dao;

import dark_shell.dao.utils.EntityDAO;
import dark_shell.models.database.*;
import dark_shell.models.database.Character;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class CharactersDAO extends EntityDAO<Character> {
    public CharactersDAO(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    private CriteriaQuery<CyberpunkCharacteristic> getCharacterCyberpunkCharacteristicsQuery(
            CriteriaBuilder builder, Character character) {
        CriteriaQuery<CyberpunkCharacteristic> preparedQuery = null;

        preparedQuery = builder.createQuery(CyberpunkCharacteristic.class);
        Root<Character> root = preparedQuery.from(Character.class);
        preparedQuery = preparedQuery.select(root.join("cyberpunkCharacteristics"));
        preparedQuery = preparedQuery.where(builder.equal(root.get("id"), character.getId()));

        return preparedQuery;
    }

    private CriteriaQuery<FantasyCharacteristic> getCharacterFantasyCharacteristicsQuery(
            CriteriaBuilder builder, Character character) {
        CriteriaQuery<FantasyCharacteristic> preparedQuery = null;

        preparedQuery = builder.createQuery(FantasyCharacteristic.class);
        Root<Character> root = preparedQuery.from(Character.class);
        preparedQuery = preparedQuery.select(root.join("fantasyCharacteristics"));
        preparedQuery = preparedQuery.where(builder.equal(root.get("id"), character.getId()));

        return preparedQuery;
    }

    private CriteriaQuery<PostApocalypseCharacteristic> getCharacterPostApocalypseCharacteristicCharacteristicsQuery(
            CriteriaBuilder builder, Character character) {
        CriteriaQuery<PostApocalypseCharacteristic> preparedQuery = null;

        preparedQuery = builder.createQuery(PostApocalypseCharacteristic.class);
        Root<Character> root = preparedQuery.from(Character.class);
        preparedQuery = preparedQuery.select(root.join("postApocalypseCharacteristics"));
        preparedQuery = preparedQuery.where(builder.equal(root.get("id"), character.getId()));

        return preparedQuery;
    }

    private CriteriaQuery<Statistic> getCharacterStatisticCharacteristicsQuery(
            CriteriaBuilder builder, Character character) {
        CriteriaQuery<Statistic> preparedQuery = null;

        preparedQuery = builder.createQuery(Statistic.class);
        Root<Character> root = preparedQuery.from(Character.class);
        preparedQuery = preparedQuery.select(root.join("statistic"));
        preparedQuery = preparedQuery.where(builder.equal(root.get("id"), character.getId()));

        return preparedQuery;
    }

    @Override
    protected CriteriaQuery<Character> getSelectQuery(CriteriaBuilder builder, Long id) {
        CriteriaQuery<Character> preparedQuery = null;

        preparedQuery = builder.createQuery(Character.class);
        Root<Character> root = preparedQuery.from(Character.class);
        preparedQuery = preparedQuery.select(root);
        preparedQuery = preparedQuery.where(builder.equal(root.get("id"), id));

        return preparedQuery;
    }

    @Override
    protected CriteriaQuery<Character> getSelectAllQuery(CriteriaBuilder builder) {
        CriteriaQuery<Character> preparedQuery = null;

        preparedQuery = builder.createQuery(Character.class);
        Root<Character> root = preparedQuery.from(Character.class);
        preparedQuery = preparedQuery.select(root);

        return preparedQuery;
    }

    @Override
    protected Character searchEntity(Character entity, EntityManager manager) {
        return entity == null || entity.getId() == null ? null : manager.find(Character.class, entity.getId());
    }
}
