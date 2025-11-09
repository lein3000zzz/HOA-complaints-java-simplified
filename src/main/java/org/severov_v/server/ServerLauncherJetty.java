package org.severov_v.server;

import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.SimpleInstanceManager;
import org.eclipse.jetty.ee10.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.ee10.jsp.JettyJspServlet;
import org.eclipse.jetty.ee10.webapp.WebAppContext;
import org.eclipse.jetty.server.Server;
import org.severov_v.servlets.RequestServlet;
import org.severov_v.servlets.ResidentServlet;

import java.nio.file.Path;
import java.net.URL;
import java.nio.file.Paths;

public class ServerLauncherJetty implements ServerLauncher {
    private static ServerLauncherJetty server;

    public static ServerLauncher getInstance() {
        if (server == null) {
            server = new ServerLauncherJetty();
        }
        return server;
    }

    @Override
    public void startServer() throws Exception {
        int port = Integer.getInteger("PORT", 8080); // Я не знал раньше, что так можно получать порты из системных переменных
        Server server = new Server(port);

        WebAppContext webApp = new WebAppContext();
        webApp.setContextPath("/");

        URL cp = getClass().getClassLoader().getResource("webapp");
        if (cp != null) {
            webApp.setWar(cp.toURI().toString());
        } else {
            Path devWebapp = Paths.get("src/main/webapp").toAbsolutePath(); // когда не из джарника запускается
            webApp.setWar(devWebapp.toUri().toString());
        }

        webApp.setAttribute(InstanceManager.class.getName(), new SimpleInstanceManager());
        webApp.addServletContainerInitializer(new JettyJasperInitializer(), (Class<?>) null);
        webApp.addServlet(JettyJspServlet.class, "*.jsp");

        webApp.addServlet(ResidentServlet.class, "/residents");
        webApp.addServlet(RequestServlet.class, "/requests");

        server.setHandler(webApp);
        server.start();

        System.out.println("Jetty started on http://localhost:" + port);

        server.join();
    }
}
