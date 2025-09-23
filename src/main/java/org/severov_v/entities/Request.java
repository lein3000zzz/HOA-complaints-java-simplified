package org.severov_v.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Request {
    private long id;
    private long idComplaining;
    private String houseAddress;
    private RequestType type;
    private String complaintText;
    private RequestStatus status;
}