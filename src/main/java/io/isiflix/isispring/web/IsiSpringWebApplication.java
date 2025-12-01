package io.isiflix.isispring.web;

import io.isiflix.isispring.explorer.ClassExplorer;
import io.isiflix.isispring.util.IsiLogger;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.util.List;

public class IsiSpringWebApplication {

    private IsiSpringWebApplication() {
        throw new IllegalStateException("Utility class");
    }

    public static void run(Class<?> sourceClass) {

        disableApacheLogging();

        try{
            IsiLogger.showBanner();
            IsiLogger.log("IsiSpringWebApplication", "Starting Tomcat server...");
            long initTime = System.currentTimeMillis();
            Tomcat tomcat = new Tomcat();
            Connector connector = new Connector();
            connector.setPort(8080);
            tomcat.setConnector(connector);

            Context ctx = tomcat.addContext("", new File(".").getAbsolutePath());
            Tomcat.addServlet(ctx, "IsiDispatcherServlet", new IsiDispatcherServlet());

            ctx.addServletMappingDecoded("/*", "IsiDispatcherServlet");
            tomcat.start();
            long endTime = System.currentTimeMillis();
            IsiLogger.log("IsiSpringWebApplication", "Tomcat server started in " + ((double) endTime - initTime) / 1000 + " ms.");
            List<String> allClasses = ClassExplorer.retriveAllClasses(sourceClass);
            allClasses.forEach(System.out::println);
            tomcat.getServer().await();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void disableApacheLogging() {
        java.util.logging.Logger.getLogger("org.apache").setLevel(java.util.logging.Level.OFF);
    }

}
