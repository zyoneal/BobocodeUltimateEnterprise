package ServerJavaServlets;

import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;

public class MorningClient {

    @SneakyThrows
    public static void main(String[] args) {
        // -- 1 task
        Socket clientSocket = new Socket("93.175.204.87", 8080);
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        printWriter.println("GET /morning?name=Viktor HTTP/1.1");
        printWriter.println("Host: 93.175.204.87");
        printWriter.println();
        printWriter.flush();
        InputStream inputStream = clientSocket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        bufferedReader.lines().forEach(System.out::println);
        System.out.println(bufferedReader.readLine());

        // -- 2 task
        Socket socket = new Socket("93.175.204.87",8080);
        try(var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        var writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            writer.println("GET /morning?name=Viktor HTTP/1.1");
            writer.println("Host: 93.175.204.87");
            writer.println("X-Mood: tired");
            writer.println();
            writer.flush();
            reader.lines().forEach(System.out::println);
        }
    }

}
