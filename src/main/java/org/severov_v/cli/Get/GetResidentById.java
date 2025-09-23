package org.severov_v.cli.Get;

import org.severov_v.cli.Command;
import org.severov_v.cli.Menu;
import org.severov_v.entities.Request;
import org.severov_v.entities.Resident;
import org.severov_v.service.RequestService;
import org.severov_v.service.RequestServiceImpl;
import org.severov_v.service.ResidentService;
import org.severov_v.service.ResidentServiceImpl;

import java.util.Scanner;

public class GetResidentById implements Command {
    private final Scanner scanner;
    private final ResidentService service;

    public GetResidentById() {
        this.service = ResidentServiceImpl.getInstance();
        this.scanner = Menu.getScanner();
    }

    @Override
    public void execute() {
        System.out.print("Enter resident id: ");
        long id = scanner.nextLong();
        Resident resident = service.getById(id);
        if (resident == null) {
            System.out.println("resident not found.");
        } else {
            System.out.println(resident);
        }
    }

    @Override
    public String getCommandName() {
        return "Get resident by id";
    }
}
