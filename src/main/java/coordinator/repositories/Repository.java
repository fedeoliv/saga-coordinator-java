package coordinator.repositories;

import java.util.List;

public interface Repository<TKey, TEntity> {
    TEntity add(TEntity value);
    TEntity update(TKey key, TEntity value);
    void delete(TKey key);
    TEntity findByKey(TKey key);
    TEntity findByFilter(String key1, String value1);
    TEntity findByFilter(String key1, String value1, String key2, String value2);
    List<TEntity> findManyByFilter(Object filter);
}
