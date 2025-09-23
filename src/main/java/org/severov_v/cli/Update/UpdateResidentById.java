package org.severov_v.cli.Update;

import org.severov_v.cli.Command;
import org.severov_v.cli.Menu;
import org.severov_v.service.RequestService;
import org.severov_v.service.RequestServiceImpl;
import org.severov_v.service.ResidentService;
import org.severov_v.service.ResidentServiceImpl;

import java.util.Scanner;

public class UpdateResidentById implements Command {

    private final Scanner scanner;
    private final ResidentService residentService;

    public UpdateResidentById() {
        residentService = ResidentServiceImpl.getInstance();
        scanner = Menu.getScanner();
    }

    private String[] setParameters(){
        String[] params = new String[3];

        System.out.print("input id to update: ");
        params[0] = scanner.nextLine();

        System.out.print("input new phone number: ");
        params[1] = scanner.nextLine();

        System.out.print("input new fullName: ");
        params[2] = scanner.nextLine();

        return params;
    }
    @Override
    public void execute() {
        residentService.update(setParameters());
    }

    @Override
    public String getCommandName() {
        return "Update resident";
    }
}