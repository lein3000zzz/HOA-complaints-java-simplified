package org.severov_v;

import org.severov_v.cli.Menu;
import org.severov_v.server.ServerLauncherJetty;

public class Main {
    public static void main(String[] args) {
//        Menu.run();
        try {
            ServerLauncherJetty.getInstance().startServer();
        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}