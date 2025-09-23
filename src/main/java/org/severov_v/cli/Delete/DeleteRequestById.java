package org.severov_v.cli.Delete;

import org.severov_v.cli.Command;
import org.severov_v.cli.Menu;
import org.severov_v.service.RequestService;
import org.severov_v.service.RequestServiceImpl;

import java.util.Scanner;

public class DeleteRequestById implements Command {
    private final RequestService requestService;
    private final Scanner scanner;

    public DeleteRequestById() {
        requestService = RequestServiceImpl.getInstance();
        scanner = Menu.getScanner();
    }

    private int setId(){
        try {
            System.out.print("id: ");
            return scanner.nextInt();
        } catch (NumberFormatException nfe){
            System.out.println("Wrong id format");
            System.out.println("try again");
        }

        return setId();
    }

    @Override
    public void execute() {
        requestService.delete(setId());
    }

    @Override
    public String getCommandName() {
        return "Delete request by id";
    }

}
