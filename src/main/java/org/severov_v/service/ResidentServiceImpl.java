package org.severov_v.service;

import org.severov_v.entities.Resident;
import org.severov_v.repository.ResidentRepository;
import org.severov_v.repository.ResidentRepositoryInMemImpl;

import java.util.List;
import java.util.Objects;

public class ResidentServiceImpl implements ResidentService {
    private static ResidentService service;
    private final ResidentRepository repo;

    private ResidentServiceImpl() {
        this.repo = ResidentRepositoryInMemImpl.getInstance();
    }

    public static ResidentService getInstance() {
        if (service == null) {
            service = new ResidentServiceImpl();
        }
        return service;
    }

    @Override
    public Resident getById(long id) {
        return repo.getById(id);
    }

    @Override
    public List<Resident> getAll() {
        return repo.getAll();
    }

    @Override
    public synchronized void create(String[] params) {
        Objects.requireNonNull(params);

        String phoneNumber = params.length > 0 ? params[0] : null;
        String fullName = params.length > 1 ? params[1] : null;

        checkPhoneNumberAndFullName(phoneNumber, fullName);

        if (repo.getByPhone(phoneNumber) != null) {
            throw new IllegalArgumentException("Resident with this phone number already exists");
        }

        Resident newResident = Resident.builder()
                .phoneNumber(phoneNumber)
                .fullName(fullName)
                .build();

        repo.create(newResident);
    }

    @Override
    public void update(String[] params) {
        Objects.requireNonNull(params);

        long idToUpdate = params.length > 0 ? Long.parseLong(params[0]) : -1;

        String phoneNumber = params.length > 1 ? params[1] : null;
        String fullName = params.length > 2 ? params[2] : null;

        if (idToUpdate < 0) {
            throw new IllegalArgumentException("wrong ID");
        }

        checkPhoneNumberAndFullName(phoneNumber, fullName);

        Resident updatedResident = Resident.builder()
                .id(idToUpdate)
                .phoneNumber(phoneNumber)
                .fullName(fullName)
                .build();

        repo.update(updatedResident);
    }

    private void checkPhoneNumberAndFullName(String phoneNumber, String fullName) {
        checkPhoneNumber(phoneNumber);
        checkFullName(fullName);
    }

    private void checkPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number is null or empty");
        }
    }

    private void checkFullName(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            throw new IllegalArgumentException("fullName is null or empty");
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
    public List<Resident> getByName(String name) {
        return repo.getByName(name);
    }

    @Override
    public Resident getByPhone(String phone) {
        return repo.getByPhone(phone);
    }
}