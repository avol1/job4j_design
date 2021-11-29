package ru.job4j.io.criteria;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import ru.job4j.io.ArgsName;
import ru.job4j.io.Search;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Criteria {

    public static final String DIRECTORY = "d";
    public static final String NAME = "n";
    public static final String TYPE = "t";
    public static final String OUTPUT = "o";

    public static void validateArgs(ArgsName params) throws IllegalArgumentException {
        String directory = params.get(DIRECTORY);
        String name = params.get(NAME);
        String type = params.get(TYPE);
        String output = params.get(OUTPUT);

        if (directory == null
                || name == null
                || type == null
                || output == null) {
            throw new IllegalArgumentException("Arguments missing");
        }

        File fileDirectory = new File(directory);

        if (!fileDirectory.exists()) {
            throw new IllegalArgumentException(String.format("Not exist %s", fileDirectory.getAbsoluteFile()));
        }
        if (!fileDirectory.isDirectory()) {
            throw new IllegalArgumentException(String.format("Not directory %s", fileDirectory.getAbsoluteFile()));
        }

        if (!type.equals(CriteriaType.MASK) && !type.equals(CriteriaType.NAME) && !type.equals(CriteriaType.REGEX)) {
            throw new IllegalArgumentException("Search type incorrect.  valid values: name, mask, regex");
        }
    }

    public static void save(List<Path> paths, String file) {
        try (PrintWriter out = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(file)
                ))) {
            paths.forEach(out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ArgsName params = ArgsName.of(args);

        validateArgs(params);

        String directory = params.get(DIRECTORY);
        String name = params.get(NAME);
        String type = params.get(TYPE);
        String output = params.get(OUTPUT);

        try {
            List<Path> paths = Search.search(Paths.get(directory), path -> {
                if (path.toFile().isDirectory()) {
                    return false;
                }

                switch (type) {
                    case CriteriaType.MASK:
                        final PathMatcher maskMatcher = FileSystems.getDefault()
                                .getPathMatcher("glob:" + name);
                        return maskMatcher.matches(path.getFileName());
                    case CriteriaType.NAME:
                        return path.getFileName().toString().equals(name);
                    case CriteriaType.REGEX:
                        return Pattern.matches(name, path.getFileName().toString());
                    default:
                        return false;
                }
            });
            if (!paths.isEmpty()) {
                save(paths, output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
