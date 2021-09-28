package ru.job4j.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class Search {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            throw new IllegalArgumentException("Root folder or file extension is null. Usage java -jar dir.jar ROOT_FOLDER FILE_EXTENSION.");
        }

        String rootFolder = args[0];
        String fileExtension = args[1];

        Path start = Paths.get(rootFolder);

        if (!Files.exists(start)) {
            throw new IllegalArgumentException("Root folder does not exist");
        }

        search(start, p -> p.toFile().getName().endsWith(fileExtension)).forEach(System.out::println);
    }

    public static List<Path> search(Path root, Predicate<Path> condition) throws IOException {
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }
}
