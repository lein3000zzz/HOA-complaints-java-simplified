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
    public synchronized void update(Resident newObject) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getId() == newObject.getId()) {
                storage.set(i, newObject);
                return;
            }
        }
    }

    @Override
    public synchronized void delete(long id) {
        storage.remove(this.getById(id));
    }

    @Override
    public synchronized Resident getById(long id) {
        Optional<Resident> opt = storage.stream().filter(r -> r.getId() == id).findFirst();
        return opt.orElse(null);
    }

    @Override
    public synchronized List<Resident> getAll() {
        return List.copyOf(storage);
    }

    @Override
    public synchronized List<Resident> getByName(String nameToMatch) {
        List<Resident> matched = new ArrayList<>();

        for (Resident resident : storage) {
            if (resident.getFullName().contains(nameToMatch)) {
                matched.add(resident);
            }
        }

        return matched;
    }

    @Override
    public synchronized Resident getByPhone(String phoneToMatch) {
        Resident found = null;

        for (Resident resident : storage) {
            if (resident.getPhoneNumber().equals(phoneToMatch)) {
                found = resident;
                break;
            }
        }

        return found;
    }
}