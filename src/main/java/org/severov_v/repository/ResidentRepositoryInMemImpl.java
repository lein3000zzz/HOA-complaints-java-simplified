package org.severov_v.repository;

import org.severov_v.entities.Request;
import org.severov_v.entities.Resident;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ResidentRepositoryInMemImpl implements ResidentRepository {
    private static ResidentRepositoryInMemImpl obj;
    private final List<Resident> storage = new ArrayList<>();

    private ResidentRepositoryInMemImpl() {}

    public static ResidentRepository getInstance() {
        if (obj == null) {
            obj = new ResidentRepositoryInMemImpl();
        }
        return obj;
    }

    @Override
    public synchronized void create(Resident object) {
        storage.add(object);
    }

    @Override
    public synchronized void deleteAll() {
        storage.clear();
    }

    @Override
    public synchronized void update(int id, Resident newObject) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getId() == id) {
                storage.set(i, newObject);
                return;
            }
        }
    }

    @Override
    public synchronized Resident getById(int id) {
        Optional<Resident> opt = storage.stream().filter(r -> r.getId() == id).findFirst();
        return opt.orElse(null);
    }

    @Override
    public synchronized List<Resident> getAll() {
        return List.copyOf(storage);
    }

//    @Override
//    public synchronized List<Resident> getByHouseId(int houseId) {
//        List<Resident> result = new ArrayList<>();
//        for (Resident r : storage) {
//            if (r.getHouseAddress() == houseId) result.add(r);
//        }
//        return result;
//    }
}