package org.severov_v.repository;

import org.severov_v.entities.Resident;

import java.util.List;

public interface ResidentRepository extends Repository<Resident, Integer> {
    List<Resident> getByName(String name);
    Resident getByPhone(String phone);
}
