package coordinator.repositories;

import java.util.List;

public interface Repository<TKey, TEntity> {
    TEntity Add(TEntity value);

    TEntity Update(TKey key, TEntity value);

    void Delete(TKey key);

    TEntity FindOneByKey(TKey key);

    TEntity FindOneByFilter(String key1, String value1);
    TEntity FindOneByFilter(String key1, String value1, String key2, String value2);

    List<TEntity> FindManyByFilter(Object filter);
}
