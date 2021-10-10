package ru.job4j.io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoServer {
    public static final String EXIT = "Exit";
    public static final String HELLO = "Hello";

    private static final Logger LOG = LoggerFactory.getLogger(EchoServer.class.getName());

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                try (OutputStream out = socket.getOutputStream();
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()))) {
                    out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                    for (String str = in.readLine(); str != null && !str.isEmpty(); str = in.readLine()) {
                        if (str.contains("/?msg=")) {
                            Pattern pattern = Pattern.compile("=([^\\s]+)");
                            Matcher matcher = pattern.matcher(str);
                            String parameter = "";
                            while (matcher.find()) {
                                parameter = matcher.group(1);
                            }

                            if (parameter.equals(EXIT)) {
                                server.close();
                            } else if (parameter.equals(HELLO)) {
                                out.write("Hello".getBytes());
                            } else {
                                out.write("What".getBytes());
                            }
                        }
                        System.out.println(str);
                    }
                    out.flush();
                }
            }
        } catch (IOException e) {
            LOG.error("Exception in Echo Server", e);
        }
    }
}
