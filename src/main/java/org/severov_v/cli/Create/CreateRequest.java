package org.severov_v.cli.Create;

import org.severov_v.cli.Command;
import org.severov_v.cli.Menu;
import org.severov_v.service.RequestService;
import org.severov_v.service.RequestServiceImpl;

import java.util.Scanner;

public class CreateRequest implements Command {
    private final Scanner scanner;
    private final RequestService reqService;

    public CreateRequest() {
        this.reqService = RequestServiceImpl.getInstance();
        this.scanner = Menu.getScanner();
    }

    @Override
    public void execute() {
        try {
            String[] params = new String[4];

            System.out.print("idComplaining: ");
            params[0] = scanner.nextLine().trim();
            System.out.print("type (APARTMENT/HOUSE): ");
            params[1] = scanner.nextLine().trim();
            System.out.print("houseAddress: ");
            params[2] = scanner.nextLine().trim();
            System.out.print("complaintText: ");
            params[3] = scanner.nextLine().trim();

            reqService.create(params);
            System.out.println("Request added.");
        } catch (Exception e) {
            System.out.println("Invalid input, aborted: " + e.getMessage());
        }
    }

    @Override
    public String getCommandName() {
        return "Add request";
    }
}
