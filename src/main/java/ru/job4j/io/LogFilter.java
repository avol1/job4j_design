package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogFilter {
    public static final String ERROR = "404";

    public static List<String> filter(String file) {
        List<String> result = new ArrayList<>();

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            result = in.lines()
                    .filter((line) -> {
                        String[] array = line.split(" ");
                        return (array.length > 1 && array[array.length - 2].equals(ERROR));
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void save(List<String> log, String file) {
        try (PrintWriter out = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(file)
                ))) {
            log.forEach(out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<String> log = filter("log.txt");
        save(log, "404.txt");
    }
}
