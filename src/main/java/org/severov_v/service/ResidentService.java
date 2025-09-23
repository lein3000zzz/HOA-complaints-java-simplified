package org.severov_v.service;

import org.severov_v.entities.Resident;

import java.util.List;

public interface ResidentService extends Service<Resident, Integer> {
    List<Resident> getByName(String name);
    Resident getByPhone(String phone);
//    List<Resident> getByHouseId(int houseId);
}

