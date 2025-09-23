package org.severov_v.cli.Delete;

import org.severov_v.cli.Command;
import org.severov_v.service.RequestService;
import org.severov_v.service.RequestServiceImpl;
import org.severov_v.service.ResidentService;
import org.severov_v.service.ResidentServiceImpl;

public class DeleteAllRequests implements Command {
    private final RequestService requestService;

    public DeleteAllRequests() {
        requestService = RequestServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        requestService.deleteAll();
        System.out.println("Current list of requests: " + requestService.getAll());
    }

    @Override
    public String getCommandName() {
        return "Delete all requests";
    }
}
