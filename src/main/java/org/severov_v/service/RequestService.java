package org.severov_v.service;

import org.severov_v.entities.Request;
import org.severov_v.entities.RequestStatus;
import org.severov_v.entities.RequestType;

import java.util.List;

public interface RequestService extends Service<Request, Integer> {
    List<Request> getByComplaint(String complaint);
    List<Request> getByStatus(RequestStatus status);
    List<Request> getByEmployeeId(RequestType type);
    List<Request> getByAddress(String address);
//    List<Request> getByHouseId(int houseId);
}
