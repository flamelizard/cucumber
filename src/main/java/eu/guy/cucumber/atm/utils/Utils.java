package eu.guy.cucumber.atm.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Utils {
    public static void sleep(Integer sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
        }
    }

    /*
    Tricky getResources()
    for instance .class.getResource()
    for static method .class.getClassLoader().getResource()
    Not running test will not include test-resource folder in classpath
    mvn compile does not copy test-resource folder to "target" folder in classpath
     */
    public static File getProjectFile(String filePath) throws
            FileNotFoundException {
        URL url = Utils.class.getClassLoader().getResource(filePath);
        if (url != null) {
            try {
                return new File(url.toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        throw new FileNotFoundException(filePath);
    }

    public static String slurpFile(String filepath) throws IOException {
        Path path = Paths.get(filepath);
        return Files.readAllLines(path)
                .stream()
                .collect(Collectors.joining("\n"));
    }

    public static void printClasspath() {
        String classPath = System.getProperty("java.class.path");
        for (String path : classPath.split(";")) {
            System.out.println("[classpath " + path);
        }
    }
}
