package org.severov_v.cli.Get;

import org.severov_v.cli.Command;
import org.severov_v.entities.Request;
import org.severov_v.entities.Resident;
import org.severov_v.service.RequestService;
import org.severov_v.service.RequestServiceImpl;
import org.severov_v.service.ResidentService;
import org.severov_v.service.ResidentServiceImpl;

import java.util.List;

public class GetAllResidents implements Command {
    private final ResidentService service;

    public GetAllResidents() {
        this.service = ResidentServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        List<Resident> all = service.getAll();
        if (all.isEmpty()) {
            System.out.println("No residents found.");
            return;
        }
        all.forEach(System.out::println);
    }

    @Override
    public String getCommandName() {
        return "Get all residents";
    }
}