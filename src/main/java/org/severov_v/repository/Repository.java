package org.severov_v.repository;

import java.util.List;

public interface Repository<T, Integer> {
    void create(T object);
    void deleteAll();
    void update(T newObject);
    void delete(long id);
    T getById(long id);
    List<T> getAll();
}
