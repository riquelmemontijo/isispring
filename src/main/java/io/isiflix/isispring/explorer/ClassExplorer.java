package io.isiflix.isispring.explorer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassExplorer {

    public static List<String> retriveAllClasses(Class<?> sourceClass){
        return packageExplorer(sourceClass.getPackageName());
    }

    public static List<String> packageExplorer(String packageName){
        ArrayList<String> classes = new ArrayList<>();
        try {
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("\\.", "/"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                if(line.endsWith(".class")){
                    classes.add(packageName + "." + line.substring(0, line.indexOf(".class")));
                } else {
                    classes.addAll(packageExplorer(packageName + "." + line));
                }
            }
            return classes;
        }catch (Exception e){
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
