package org.severov_v.cli.Get;

import org.severov_v.cli.Command;
import org.severov_v.cli.Menu;
import org.severov_v.entities.Request;
import org.severov_v.service.RequestService;
import org.severov_v.service.RequestServiceImpl;

import java.util.Scanner;

public class GetRequestById implements Command {
    private final Scanner scanner;
    private final RequestService service;

    public GetRequestById() {
        this.service = RequestServiceImpl.getInstance();
        this.scanner = Menu.getScanner();
    }

    @Override
    public void execute() {
        System.out.print("Enter request id: ");
        int id = scanner.nextInt();
        Request r = service.getById(id);
        if (r == null) {
            System.out.println("Request not found.");
        } else {
            System.out.println(r);
        }
    }

    @Override
    public String getCommandName() {
        return "Get request by id";
    }
}
