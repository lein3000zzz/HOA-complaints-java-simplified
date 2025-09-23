package org.severov_v.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Resident {
    private long id;
    private String phoneNumber;
    private String fullName;
}