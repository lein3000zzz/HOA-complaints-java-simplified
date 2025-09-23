package org.severov_v.service;

import lombok.Getter;
import org.severov_v.entities.Request;
import org.severov_v.entities.RequestStatus;
import org.severov_v.entities.RequestType;
import org.severov_v.repository.RequestRepository;
import org.severov_v.repository.RequestRepositoryInMemImpl;
import org.severov_v.utils.Autoincrement;

import java.util.List;
import java.util.Objects;

public class RequestServiceImpl implements RequestService {
    private static RequestService obj;
    private final RequestRepository repo;
    private final Autoincrement idGenerator = new Autoincrement();

    private RequestServiceImpl() {
        this.repo = RequestRepositoryInMemImpl.getInstance();
    }

    public static RequestService getInstance() {
        if (obj == null) {
            obj = new RequestServiceImpl();
        }
        return obj;
    }

    @Override
    public Request getById(int id) {
        return repo.getById(id);
    }

    @Override
    public List<Request> getAll() {
        return repo.getAll();
    }

    @Override
    public synchronized void create(String[] params) {
        Objects.requireNonNull(params);

        int idComplaining = params.length > 0 ? Integer.parseInt(params[0]) : 0;
        RequestType requestType = params.length > 1 ? RequestType.valueOf(params[1].toUpperCase()) : null;
        String textRequest = params.length > 2 ? params[2] : null;

        if (idComplaining < 0) {
            throw new IllegalArgumentException();
        }
        if (requestType == null) {
            throw new IllegalArgumentException();
        }
        if (textRequest == null || textRequest.isEmpty()) {
            throw new IllegalArgumentException();
        }

        long id = idGenerator.increment();
        RequestStatus status = RequestStatus.CREATED;

        Request newRequest = Request.builder()
                .id(id)
                .idComplaining(idComplaining)
                .type(requestType)
                .complaintText(textRequest)
                .status(status)
                .build();

        repo.create(newRequest);
    }

    @Override
    public void update(int id, String[] parameters) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Request> getByComplaint(String complaint) {
        return List.of();
    }

    @Override
    public List<Request> getByStatus(RequestStatus status) {
        return List.of();
    }

    @Override
    public List<Request> getByEmployeeId(RequestType type) {
        return List.of();
    }

    @Override
    public List<Request> getByAddress(String address) {
        return List.of();
    }
}
