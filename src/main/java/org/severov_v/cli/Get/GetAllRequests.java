package org.severov_v.cli.Get;

import org.severov_v.cli.Command;
import org.severov_v.entities.Request;
import org.severov_v.service.RequestService;
import org.severov_v.service.RequestServiceImpl;

import java.util.List;

public class GetAllRequests implements Command {
    private final RequestService service;

    public GetAllRequests() {
        this.service = RequestServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        List<Request> all = service.getAll();
        if (all.isEmpty()) {
            System.out.println("No requests found.");
            return;
        }
        all.forEach(System.out::println);
    }

    @Override
    public String getCommandName() {
        return "Get all requests";
    }
}