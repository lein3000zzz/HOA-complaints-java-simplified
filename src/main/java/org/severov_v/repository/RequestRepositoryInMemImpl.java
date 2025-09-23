package org.severov_v.repository;

import org.severov_v.entities.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RequestRepositoryInMemImpl implements RequestRepository {
    private static RequestRepositoryInMemImpl repo;
    private final List<Request> storage = new ArrayList<>();

    private RequestRepositoryInMemImpl() {}

    public static RequestRepository getInstance() {
        if (repo == null) {
            repo = new RequestRepositoryInMemImpl();
        }
        return repo;
    }

    @Override
    public synchronized void create(Request object) {
        storage.add(object);
    }

    @Override
    public synchronized void deleteAll() {
        storage.clear();
    }

    @Override
    public synchronized void update(int id, Request newObject) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getId() == id) {
                storage.set(i, newObject);
                return;
            }
        }
    }

    @Override
    public synchronized Request getById(int id) {
        Request found = null;
        for (Request request : storage) {
            if (request.getId() == id) {
                found = request;
                break;
            }
        }
        return found;
    }

    @Override
    public synchronized List<Request> getAll() {
        return List.copyOf(storage);
    }
}

