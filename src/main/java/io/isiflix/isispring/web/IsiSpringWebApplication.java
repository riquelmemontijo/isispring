package io.isiflix.isispring.web;

import io.isiflix.isispring.annotation.IsiController;
import io.isiflix.isispring.annotation.IsiGetMethod;
import io.isiflix.isispring.annotation.IsiPostMethod;
import io.isiflix.isispring.explorer.ClassExplorer;
import io.isiflix.isispring.util.IsiLogger;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class IsiSpringWebApplication {

    private IsiSpringWebApplication() {
        throw new IllegalStateException("Utility class");
    }

    public static void run(Class<?> sourceClass) {

        disableApacheLogging();

        try{
            long initTime = System.currentTimeMillis();

            IsiLogger.showBanner();
            IsiLogger.log("IsiSpringWebApplication", "Starting Tomcat server...");

            extractMetaData(sourceClass);

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

            tomcat.getServer().await();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void disableApacheLogging() {
        java.util.logging.Logger.getLogger("org.apache").setLevel(java.util.logging.Level.OFF);
    }

    private static void extractMetaData(Class<?> sourceClass) throws Exception{
        List<String> allClasses = ClassExplorer.retriveAllClasses(sourceClass);
        for (String className : allClasses) {
            Class<?> clazz = Class.forName(className);
            if(clazz.isAnnotationPresent(IsiController.class)){
                IsiLogger.log("Metadata Explorer: ", "Found a Controller named " + clazz.getSimpleName());
                extractMethods(clazz);
            }
        }
    }

    private static void extractMethods(Class<?> sourceClass) throws Exception{
        Method methods[] = sourceClass.getMethods();
        for (Method method : methods) {
            if(method.isAnnotationPresent(IsiGetMethod.class)){
                String path = method.getAnnotation(IsiGetMethod.class).value();
                IsiLogger.log("Metada Explorer: ", " GET method " + method.getName() + " with path " + path);
            } else if (method.isAnnotationPresent(IsiPostMethod.class)) {
                String path = method.getAnnotation(IsiPostMethod.class).value();
                IsiLogger.log("Metada Explorer: ", " POST method " + method.getName() + " with path " + path);
            }
        }
    }

}
