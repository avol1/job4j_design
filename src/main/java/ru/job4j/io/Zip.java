package ru.job4j.io;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    public static final String DIRECTORY = "d";
    public static final String EXCLUDE = "e";
    public static final String OUTPUT = "o";

    public static void packFiles(List<Path> sources, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            for (Path path : sources) {
                zip.putNextEntry(new ZipEntry(path.toFile().getPath()));
                try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(path.toFile()))) {
                    zip.write(out.readAllBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void packSingleFile(File source, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            zip.putNextEntry(new ZipEntry(source.getPath()));
            try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(source))) {
                zip.write(out.readAllBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void validateArgs(ArgsName params) throws IllegalArgumentException {
        String directory = params.get(DIRECTORY);
        String exclude = params.get(EXCLUDE);
        String output = params.get(OUTPUT);

        if (directory == null
                || exclude == null
                || output == null) {
            throw new IllegalArgumentException(String.format("Arguments missing"));
        }

        File fileDirectory = new File(directory);

        if (!fileDirectory.exists()) {
            throw new IllegalArgumentException(String.format("Not exist %s", fileDirectory.getAbsoluteFile()));
        }
        if (!fileDirectory.isDirectory()) {
            throw new IllegalArgumentException(String.format("Not directory %s", fileDirectory.getAbsoluteFile()));
        }
    }

    public static void main(String[] args) {
        ArgsName params = ArgsName.of(args);

        validateArgs(params);

        String directory = params.get(DIRECTORY);
        String exclude = params.get(EXCLUDE);
        String output = params.get(OUTPUT);

        try {
            List<Path> paths = Search.search(Paths.get(directory), path -> !path.toFile().getName().endsWith(exclude));
            packFiles(paths, new File(output));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
