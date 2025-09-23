package org.severov_v.repository;

import org.severov_v.entities.Request;
import org.severov_v.entities.RequestStatus;
import org.severov_v.entities.RequestType;
import org.severov_v.utils.Autoincrement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RequestRepositoryInMemImpl implements RequestRepository {
    private static RequestRepositoryInMemImpl repo;
    private final List<Request> storage = new ArrayList<>();
    private final Autoincrement idGenerator = new Autoincrement();

    private RequestRepositoryInMemImpl() {}

    public static RequestRepository getInstance() {
        if (repo == null) {
            repo = new RequestRepositoryInMemImpl();
        }
        return repo;
    }

    @Override
    public synchronized void create(Request object) {
        object.setId(idGenerator.increment());
        storage.add(object);
    }

    @Override
    public synchronized void deleteAll() {
        storage.clear();
    }

    @Override
    public synchronized void update(Request newObject) {
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
    public synchronized Request getById(long id) {
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

    @Override
    public synchronized List<Request> getByComplaint(String complaintToMatch) {
        List<Request> matched = new ArrayList<>();

        for (Request request : storage) {
            if (request.getComplaintText().contains(complaintToMatch)) {
                matched.add(request);
            }
        }

        return matched;
    }

    @Override
    public synchronized List<Request> getByStatus(RequestStatus status) {
        List<Request> matched = new ArrayList<>();

        for (Request request : storage) {
            if (request.getStatus().equals(status)) {
                matched.add(request);
            }
        }

        return matched;
    }

    @Override
    public synchronized List<Request> getByRequestType(RequestType type) {
        List<Request> matched = new ArrayList<>();

        for (Request request : storage) {
            if (request.getType().equals(type)) {
                matched.add(request);
            }
        }

        return matched;
    }

    @Override
    public List<Request> getByComplainingId(long id) {
        List<Request> matched = new ArrayList<>();

        for (Request request : storage) {
            if (request.getIdComplaining() == id) {
                matched.add(request);
            }
        }

        return matched;
    }

    @Override
    public synchronized List<Request> getByAddress(String addressToMatch) {
        List<Request> matched = new ArrayList<>();

        for (Request request : storage) {
            if (request.getHouseAddress().contains(addressToMatch)) {
                matched.add(request);
            }
        }

        return matched;
    }
}

