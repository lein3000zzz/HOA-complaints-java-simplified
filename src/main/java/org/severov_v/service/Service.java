package org.severov_v.service;

import java.util.List;

public interface Service<T, Integer> {
    T getById(int id);
    List<T> getAll();
    void create(String[] parameters);
    void update(int id, String[] parameters);
    void delete(int id);
    void deleteAll();

    //какие методы должны реализовывать сервисы придумаете сами
}
