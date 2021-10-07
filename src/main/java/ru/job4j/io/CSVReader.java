package ru.job4j.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CSVReader {
    public static final String STD_OUT = "stdout";

    public void handle(ArgsName argsName) throws Exception {
        validateArgs(argsName);

        String source = argsName.get("path");
        String delimiter = argsName.get("delimiter");
        String target = argsName.get("out");
        String filter = argsName.get("filter");
        List<String> filterNames = Arrays.asList(filter.split(","));
        List<Integer> filterIndex = new ArrayList<>();
        StringBuilder insertData = new StringBuilder();

        try (Scanner scanner = new Scanner(new File(source)).useDelimiter(System.lineSeparator())) {
            if (scanner.hasNext()) {
                String header = scanner.nextLine();
                String[] columns = header.split(delimiter);

                header = "";

                for (int i = 0; i < columns.length; i++) {
                    if (filterNames.isEmpty() || filterNames.contains(columns[i])) {
                        filterIndex.add(i);
                        header = header.equals("") ? columns[i] : header + delimiter + columns[i];
                    }
                }

                insertData.append(header.concat(System.lineSeparator()));
            }
            if (filterIndex.isEmpty()) {
                throw new IllegalArgumentException("Не удалось определить поля для записи");
            }

            while (scanner.hasNext()) {
                String line = scanner.next();
                String[] data = line.split(delimiter);

                line = "";

                for (int i = 0; i < data.length; i++) {
                    if (filterIndex.contains(i)) {
                        line = line.equals("") ? data[i] : line + delimiter + data[i];
                    }
                }
                line += System.lineSeparator();
                insertData.append(line);
            }
        }

        if (insertData.toString().equals("")) {
            throw new Exception("Нет данных для записи");
        }

        handleOut(target, insertData.toString());
    }

    private void validateArgs(ArgsName argsName) throws IllegalArgumentException {
        String source = argsName.get("path");
        String delimiter = argsName.get("delimiter");
        String target = argsName.get("out");
        String filter = argsName.get("filter");

        if (source == null
            || delimiter == null
            || target == null
            || filter == null) {
            throw new IllegalArgumentException("Аргументы переданы некорректно");
        }

        File sourceFile = new File(source);

        if (!sourceFile.exists()) {
            throw new IllegalArgumentException("Аргумент path отсутствует");
        }

        if (!target.equals(STD_OUT)) {
            File targetFile = new File(target);

            if (!targetFile.exists()) {
                throw new IllegalArgumentException("Аргумент out отсутствует или указан некорректно");
            }
        }
    }

    private void handleOut(String target, String data) throws Exception {
        if (target.equals(STD_OUT)) {
            System.out.println(data);
        } else {
            Files.writeString(Paths.get(target), data);
        }
    }

    public static void main(String[] args) {
        ArgsName argsName = ArgsName.of(args);
        try {
            new CSVReader().handle(argsName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
