package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Config {

    private static final String COMMENT_SYMBOL = "#";
    private static final String PARAMETER_SPLITTER_SYMBOL = "=";

    private final String path;
    private final Map<String, String> values = new HashMap<String, String>();

    public Config(final String path) {
        this.path = path;
    }

    public void load() {
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            in.lines().forEach((line) -> {
                if (line.contains(COMMENT_SYMBOL)) {
                    return;
                }

                if (!line.contains(PARAMETER_SPLITTER_SYMBOL)) {
                    throw new IllegalArgumentException();
                }

                String[] parameters = line.split(PARAMETER_SPLITTER_SYMBOL);

                if (parameters.length > 2) {
                    throw new IllegalArgumentException();
                }

                String key = parameters[0];
                String value = null;

                if (parameters.length > 1) {
                    value = parameters[1];
                }

                values.put(key, value);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String value(String key) {
        return values.get(key);
    }

    @Override
    public String toString() {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach(out::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    public static void main(String[] args) {
        System.out.println(new Config("app.properties"));
    }

}