package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Analizy {
    private final List<String> errorCodes = Arrays.asList("400", "500");

    public void unavailable(String source, String target) {
        try (BufferedReader in = new BufferedReader(new FileReader(source));
             PrintWriter out = new PrintWriter(new FileWriter(target))) {

            StringBuilder builder = new StringBuilder();
            boolean errorFind = false;

            List<String> lines = in.lines().collect(Collectors.toList());

            for (String line : lines) {
                String[] values = line.split(" ");

                String code = values[0];
                String time = values[1];

                if (errorCodes.contains(code) && !errorFind) {
                    builder.append(time).append(";");
                    errorFind = true;
                    continue;
                }

                if (!errorCodes.contains(code) && errorFind) {
                    builder.append(time).append(";");
                    builder.append(System.lineSeparator());
                    errorFind = false;
                }
            }

            out.println(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Analizy().unavailable("server.log", "unavailable.csv");
    }
}
