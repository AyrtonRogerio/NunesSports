package edu.nunes.sports.ayrton.gomes.persistence;

import edu.nunes.sports.ayrton.gomes.persistence.query.GenericQuery;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GenericJPARepository<T> {

    @Inject
    EntityManager entityManager;

    private final Class<T> persistClass;

    public GenericJPARepository(Class<T> persistClass) {
        this.persistClass = persistClass;
    }

    public EntityManager getManager() {
        return entityManager;
    }

    @Transactional
    public T insert(T t) {
        entityManager.persist(t);
        return t;
    }

    @Transactional
    public T update(T t) {
        entityManager.merge(t);
        return t;
    }

    @Transactional
    public T delete(String id) {
        T toRemove = entityManager.find(this.persistClass, id);
        if (toRemove != null) {
            entityManager.remove(toRemove);
        }
        return toRemove;
    }

    @Transactional
    public T delete(Object o) {
        T toRemove = entityManager.find(this.persistClass, o);
        if (toRemove != null) {
            entityManager.remove(toRemove);
        }
        return toRemove;
    }

    @Transactional
    public T findById(UUID id) {
        T result;
        result = entityManager.find(this.persistClass, id);
        return result;
    }

    @Transactional
    public T findByObject(Object o) {
        T result;
        result = entityManager.find(this.persistClass, o);
        return result;
    }

    public List<T> findByField(Field field, Object value) {
        CriteriaQuery<T> c = entityManager.getCriteriaBuilder().createQuery(this.persistClass);
        Root<T> from = c.from(this.persistClass);
        c.select(from);
        c.where(entityManager.getCriteriaBuilder().equal(from.get(field.getName()), value));

        return entityManager.createQuery(c).getResultList();
    }

    public T findOneByField(Field field, Object value) {
        List<T> results = findByField(field, value);
        if (results != null && !results.isEmpty()) {
            return findByField(field, value).get(0);
        }
        return null;
    }

    public List<T> list() {
        return list(0, Integer.MAX_VALUE);
    }

    public List<T> list(int page, int pagesize) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(this.persistClass);
        Root<T> from = query.from(this.persistClass);
        CriteriaQuery<T> select = query.select(from);
        TypedQuery<T> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult(page * pagesize);
        typedQuery.setMaxResults(pagesize);
        return typedQuery.getResultList();
    }

    public ResultListPage<T> executeQuery(GenericQuery genericQuery) {
        TypedQuery<T> query = this.entityManager.createQuery(genericQuery.getQuery(), persistClass);
        query.setFirstResult(genericQuery.getPage() * genericQuery.getSize());
        query.setMaxResults(genericQuery.getSize());
        TypedQuery<Long> countQuery = this.entityManager.createQuery(genericQuery.getCountQuery(), Long.class);

        Map<String, Object> parameters = genericQuery.getParams();
        if (genericQuery.getParams() != null) {
            for (Map.Entry<String, Object> pair : parameters.entrySet()) {
                query.setParameter(pair.getKey(), pair.getValue());
                countQuery.setParameter(pair.getKey(), pair.getValue());
            }
        }

        Long count = countQuery.getSingleResult();
        List<T> result = query.getResultList();

        return new ResultListPage<>(count, genericQuery.getPage(), genericQuery.getSize(), result);
    }

    public List<T> executeNonPaginatedQuery(GenericQuery genericQuery) {
        return this.executeNonPaginatedQuery(genericQuery, Integer.MAX_VALUE);
    }

    public List<T> executeNonPaginatedQuery(GenericQuery genericQuery, int limit) {
        TypedQuery<T> query = this.entityManager.createQuery(genericQuery.getQuery(), persistClass).setMaxResults(limit);

        Map<String, Object> parameters = genericQuery.getParams();
        if (genericQuery.getParams() != null) {
            for (Map.Entry<String, Object> pair : parameters.entrySet()) {
                query.setParameter(pair.getKey(), pair.getValue());
            }
        }

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<String> executeIdsQuery(GenericQuery genericQuery, int limit) {
        Query query = this.entityManager.createQuery(genericQuery.getQuery()).setMaxResults(limit);

        Map<String, Object> parameters = genericQuery.getParams();
        if (genericQuery.getParams() != null) {
            for (Map.Entry<String, Object> pair : parameters.entrySet()) {
                query.setParameter(pair.getKey(), pair.getValue());
            }
        }

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Object> executeNonPaginatedNativeQuery(GenericQuery genericQuery) {
        Query query = this.entityManager.createQuery(genericQuery.getQuery());

        Map<String, Object> parameters = genericQuery.getParams();
        if (genericQuery.getParams() != null) {
            for (Map.Entry<String, Object> pair : parameters.entrySet()) {
                query.setParameter(pair.getKey(), pair.getValue());
            }
        }

        return query.getResultList();
    }

    public Object executeUniqueValueQuery(GenericQuery genericQuery) {
        Query query = this.entityManager.createQuery(genericQuery.getQuery());

        Map<String, Object> parameters = genericQuery.getParams();
        if (genericQuery.getParams() != null) {
            for (Map.Entry<String, Object> pair : parameters.entrySet()) {
                query.setParameter(pair.getKey(), pair.getValue());
            }
        }
        return query.getSingleResult();

    }

}
