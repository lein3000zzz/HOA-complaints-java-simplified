package org.severov_v.repository;

import org.severov_v.entities.Request;
import org.severov_v.entities.RequestStatus;
import org.severov_v.entities.RequestType;

import java.util.List;

public interface RequestRepository extends Repository<Request, Integer> {
    List<Request> getByComplaint(String complaint);
    List<Request> getByStatus(RequestStatus status);
    List<Request> getByRequestType(RequestType type);
    List<Request> getByComplainingId(long id);
    List<Request> getByAddress(String address);
}
