package ru.job4j.io;

import java.io.FileOutputStream;

public class Matrix {
    public static void multiple(int size) {
        try (FileOutputStream out = new FileOutputStream("result.txt")) {
            StringBuilder builder = new StringBuilder();

            for (int row = 0; row < size; row++) {
                for (int cell = 0; cell < size; cell++) {
                    builder.append((row + 1) * (cell + 1)).append(" ");
                }
                builder.append(System.lineSeparator());
            }

            out.write(builder.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
