package org.severov_v.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Request {
    private long id;
    private long idComplaining;
    private String houseAddress;
    private RequestType type;
    private String complaintText;
    private RequestStatus status;
}