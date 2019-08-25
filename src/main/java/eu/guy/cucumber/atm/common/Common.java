package eu.guy.cucumber.atm.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Common {
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
    public static File getFileFromResources(String relPath) throws
            FileNotFoundException {
        URL url = Common.class.getClassLoader().getResource(relPath);
        if (url != null) {
            try {
                return new File(url.toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        throw new FileNotFoundException("Filepath " + relPath);
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

    public static Path getTestResourceDir() {
        return getProjectRoot().resolve("src/test/resources/");
    }

    public static Path getProjectRoot() {
        return Paths.get(System.getProperty("user.dir"));
    }
}