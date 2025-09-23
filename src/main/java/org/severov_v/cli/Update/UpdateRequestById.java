package org.severov_v.cli.Update;

import org.severov_v.cli.Command;
import org.severov_v.cli.Menu;
import org.severov_v.service.RequestService;
import org.severov_v.service.RequestServiceImpl;

import java.util.Scanner;

public class UpdateRequestById implements Command {

    private final Scanner scanner;
    private final RequestService requestService;

    public UpdateRequestById() {
        requestService = RequestServiceImpl.getInstance();
        scanner = Menu.getScanner();
    }

    private String[] setParameters(){
        String[] params = new String[6];

        System.out.print("input id to update: ");
        params[0] = scanner.nextLine();

        System.out.print("input idComplaining: ");
        params[1] = scanner.nextLine();

        System.out.print("input new RequestType: ");
        params[2] = scanner.nextLine();

        System.out.print("input houseAddress: ");
        params[3] = scanner.nextLine();

        System.out.print("input request text: ");
        params[4] = scanner.nextLine();

        System.out.print("input request status: ");
        params[5] = scanner.nextLine();

        return params;
    }
    @Override
    public void execute() {
        requestService.update(setParameters());
    }

    @Override
    public String getCommandName() {
        return "Update request";
    }
}
