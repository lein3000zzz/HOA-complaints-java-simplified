package org.severov_v.service;

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
    public Request getById(long id) {
        return repo.getById(id);
    }

    @Override
    public List<Request> getAll() {
        return repo.getAll();
    }

    @Override
    public synchronized void create(String[] params) {
        Objects.requireNonNull(params);

        long idComplaining = params.length > 0 ? Long.parseLong(params[0]) : -1;
        RequestType requestType = params.length > 1 ? RequestType.valueOf(params[1].toUpperCase()) : null;
        String houseAddress = params.length > 2 ? params[2] : null;
        String textRequest = params.length > 3 ? params[3] : null;

        RequestStatus status = RequestStatus.CREATED;

        validateParams(idComplaining, requestType, houseAddress, textRequest, status);

        long id = idGenerator.increment();

        Request newRequest = Request.builder()
                .id(id)
                .idComplaining(idComplaining)
                .type(requestType)
                .houseAddress(houseAddress)
                .complaintText(textRequest)
                .status(status)
                .build();

        repo.create(newRequest);
    }

    @Override
    public void update(String[] params) {
        Objects.requireNonNull(params);

        long idToUpdate = params.length > 0 ? Long.parseLong(params[0]) : -1;

        long idComplaining = params.length > 1 ? Long.parseLong(params[1]) : -1;
        RequestType requestType = params.length > 2 ? RequestType.valueOf(params[2].toUpperCase()) : null;
        String houseAddress = params.length > 3 ? params[3] : null;
        String textRequest = params.length > 4 ? params[4] : null;
        RequestStatus status = params.length > 5 ? RequestStatus.valueOf(params[5].toUpperCase()) : null;

        if (idToUpdate < 0) {
            throw new IllegalArgumentException("wrong ID to update");
        }

        validateParams(idComplaining, requestType, houseAddress, textRequest, status);

        Request updatedResident = Request.builder()
                .id(idToUpdate)
                .idComplaining(idComplaining)
                .type(requestType)
                .houseAddress(houseAddress)
                .complaintText(textRequest)
                .status(status)
                .build();

        repo.update(updatedResident);

    }

    private void validateParams(long idComplaining, RequestType requestType, String houseAddress, String textRequest, RequestStatus status) {
        if (idComplaining < 0) {
            throw new IllegalArgumentException("wrong idComplaining");
        }
        if (requestType == null) {
            throw new IllegalArgumentException("Request type is null");
        }
        if (houseAddress == null || houseAddress.isEmpty()) {
            throw new IllegalArgumentException("House address is null or empty");
        }
        if (textRequest == null || textRequest.isEmpty()) {
            throw new IllegalArgumentException("Text request is null or empty");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status is null");
        }
    }

    @Override
    public void delete(long id) {
        repo.delete(id);
    }

    @Override
    public void deleteAll() {
        repo.deleteAll();
    }

    @Override
    public List<Request> getByComplaint(String complaint) {
        return repo.getByComplaint(complaint);
    }

    @Override
    public List<Request> getByStatus(RequestStatus status) {
        return repo.getByStatus(status);
    }

    @Override
    public List<Request> getByRequestType(RequestType type) {
        return repo.getByRequestType(type);
    }

    @Override
    public List<Request> getByAddress(String address) {
        return repo.getByAddress(address);
    }

    @Override
    public List<Request> getByComplainingId(long id) {
        return repo.getByComplainingId(id);
    }
}
