package org.severov_v.cli;

import lombok.Getter;
import org.severov_v.cli.Create.CreateRequest;
import org.severov_v.cli.Create.CreateResident;
import org.severov_v.cli.Delete.DeleteAllRequests;
import org.severov_v.cli.Delete.DeleteAllResidents;
import org.severov_v.cli.Delete.DeleteRequestById;
import org.severov_v.cli.Delete.DeleteResidentById;
import org.severov_v.cli.Get.GetAllRequests;
import org.severov_v.cli.Get.GetAllResidents;
import org.severov_v.cli.Get.GetRequestById;
import org.severov_v.cli.Get.GetResidentById;
import org.severov_v.cli.Update.UpdateRequestById;
import org.severov_v.cli.Update.UpdateResidentById;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    @Getter
    private static final Scanner scanner = new Scanner(System.in);
    private static final Command[] commands = new Command[]{
            new CreateRequest(),
            new CreateResident(),
            new GetAllRequests(),
            new GetRequestById(),
            new GetAllResidents(),
            new GetResidentById(),
            new DeleteAllRequests(),
            new DeleteRequestById(),
            new DeleteAllResidents(),
            new DeleteResidentById(),
            new UpdateRequestById(),
            new UpdateResidentById(),
    };

    public static void run(){
        while (true){
            System.out.println();
            for (int i = 1; i <= commands.length; i++) {
                System.out.println(i + " " + commands[i - 1].getCommandName());
            }
            System.out.println("-1 Exit");
            int inputCommand;
            try {
                inputCommand = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException ime){
                System.out.println("wrong command");
                scanner.nextLine();
                continue;
            } catch (IllegalArgumentException iae){
                System.out.println(iae.getMessage());
                scanner.nextLine();
                continue;
            }

            if(inputCommand == -1){
                System.out.println("Program exit");
                return;
            }

            if(inputCommand < 1 || inputCommand > commands.length){
                System.out.println("Wrong command");
                continue;
            }

            try {
                commands[inputCommand - 1].execute();
            } catch (Exception e) {
                System.out.println("Error during command execution: " + e.getMessage());
            }
        }
    }
}
