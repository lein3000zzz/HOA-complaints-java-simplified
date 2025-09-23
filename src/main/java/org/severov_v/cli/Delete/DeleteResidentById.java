package org.severov_v.cli.Delete;

import org.severov_v.cli.Command;
import org.severov_v.cli.Menu;
import org.severov_v.service.RequestService;
import org.severov_v.service.RequestServiceImpl;
import org.severov_v.service.ResidentService;
import org.severov_v.service.ResidentServiceImpl;

import java.util.Scanner;

public class DeleteResidentById implements Command {
    private final ResidentService residentService;
    private final Scanner scanner;

    public DeleteResidentById() {
        residentService = ResidentServiceImpl.getInstance();
        scanner = Menu.getScanner();
    }

    private int setId(){
        try {
            System.out.print("id: ");
            return scanner.nextInt();
        } catch (NumberFormatException nfe){
            System.out.println("Impossible id");
            System.out.println("Write id again");
        }

        return setId();
    }

    @Override
    public void execute() {
        residentService.delete(setId());
    }

    @Override
    public String getCommandName() {
        return "Delete resident by id";
    }

}