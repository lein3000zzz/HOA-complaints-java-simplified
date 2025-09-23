package org.severov_v.repository;

import java.util.List;

public interface Repository<T, Integer> {
    void create(T object);
    void deleteAll();
    void update(int id, T newObject);
    void delete(int id);
    T getById(int id);
    List<T> getAll();
}
