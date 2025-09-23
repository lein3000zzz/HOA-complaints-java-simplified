package org.severov_v.cli.Create;

import org.severov_v.cli.Command;
import org.severov_v.cli.Menu;
import org.severov_v.service.ResidentService;
import org.severov_v.service.ResidentServiceImpl;

import java.util.Scanner;

public class CreateResident implements Command {
    private final Scanner scanner;
    private final ResidentService resService;

    public CreateResident() {
        this.resService = ResidentServiceImpl.getInstance();
        this.scanner = Menu.getScanner();
    }

    @Override
    public void execute() {
        try {
            String[] params = new String[2];

            System.out.print("Phone number: ");
            params[0] = scanner.nextLine().trim();
            System.out.print("Full name: ");
            params[1] = scanner.nextLine().trim().replaceAll(" ", "");

            resService.create(params);
            System.out.println("Resident added.");
        } catch (Exception e) {
            System.out.println("Invalid input, aborted: " + e.getMessage());
        }
    }

    @Override
    public String getCommandName() {
        return "Add resident";
    }
}