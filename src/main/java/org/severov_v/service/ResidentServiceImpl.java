package org.severov_v.service;

import org.severov_v.entities.Resident;
import org.severov_v.repository.ResidentRepository;
import org.severov_v.repository.ResidentRepositoryInMemImpl;
import org.severov_v.utils.Autoincrement;

import java.util.List;

public class ResidentServiceImpl implements ResidentService {
    private static ResidentService obj;
    private final ResidentRepository repo;
    private final Autoincrement idGenerator = new Autoincrement();

    private ResidentServiceImpl() {
        this.repo = ResidentRepositoryInMemImpl.getInstance();
    }

    public static ResidentService getInstance() {
        if (obj == null) {
            obj = new ResidentServiceImpl();
        }
        return obj;
    }

    @Override
    public Resident getById(int id) {
        return repo.getById(id);
    }

    @Override
    public List<Resident> getAll() {
        return repo.getAll();
    }

    @Override
    public void create(String[] params) {
        Resident resident = new Resident();
        repo.create(resident);
    }
}