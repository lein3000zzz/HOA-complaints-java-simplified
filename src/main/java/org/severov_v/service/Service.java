package org.severov_v.service;

import java.util.List;

public interface Service<T, Integer> {
    T getById(long id);
    List<T> getAll();
    void create(String[] parameters);
    void update(String[] parameters);
    void delete(long id);
    void deleteAll();

    //какие методы должны реализовывать сервисы придумаете сами
}
