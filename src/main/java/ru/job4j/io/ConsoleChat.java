package ru.job4j.io;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ConsoleChat {
    private boolean isStopped = false;
    private final String path;
    private final String botAnswers;
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    public void run() {
        List<String> phrases = readPhrases();
        List<String> log = new ArrayList<>();

        if (phrases == null || phrases.isEmpty()) {
            System.out.println("Не удалось загрузить ответы бота");
            return;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in))) {
            String userWord = reader.readLine();
            String botAnswer = "";
            log.add("Пользователь: " + userWord);

            while (!userWord.equals(OUT)) {

                isStopped = stopAnswerByInput(userWord);

                if (!isStopped) {
                    Random random = new Random();
                    int randomIndex = random.nextInt(phrases.size());
                    botAnswer = phrases.get(randomIndex);
                    System.out.println(botAnswer);
                    log.add("Bot: " + botAnswer);
                }

                userWord = reader.readLine();
                log.add("Пользователь: " + userWord);
            }
        } catch (IOException ex) {
            System.out.println("Возникла ошибка ввода/вывода");
        }

        if (!log.isEmpty()) {
            saveLog(log);
        }

    }

    private List<String> readPhrases() {
        try (BufferedReader br = new BufferedReader(new FileReader(botAnswers))) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveLog(List<String> log) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path, StandardCharsets.UTF_8, true))) {
            log.forEach(pw::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean stopAnswerByInput(String input) {
        boolean result = isStopped;

       if (input.equals(STOP)) {
           isStopped = true;
       } else if (input.equals(CONTINUE)) {
           isStopped = false;
       }

        return result;
    }

    public static void main(String[] args) {
        ConsoleChat cc = new ConsoleChat("./data/log.txt", "./data/answers.txt");
        cc.run();
    }
}
