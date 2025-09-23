package org.severov_v.cli.Delete;

import org.severov_v.cli.Command;
import org.severov_v.service.ResidentService;
import org.severov_v.service.ResidentServiceImpl;

public class DeleteAllResidents implements Command {
    private final ResidentService residentService;

    public DeleteAllResidents() {
        residentService = ResidentServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        residentService.deleteAll();
        System.out.println("Current list of residents: " + residentService.getAll());
    }

    @Override
    public String getCommandName() {
        return "Delete all residents";
    }

}
