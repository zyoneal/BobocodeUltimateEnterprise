package JavaNetworking;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        var users = new HashSet<String>();
        CompletableFuture.runAsync(() -> {
            while (true) {
                System.out.println("Unique users: " + users.size());
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        var executorService = Executors.newFixedThreadPool(40);
        try (ServerSocket serverSocket = new ServerSocket(8899)) {
            while (true) {
                executorService.execute(() -> {
                    try (var client = serverSocket.accept()) {
                        var reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        var line = reader.readLine();
                        String clientHostAddress = client.getInetAddress().getHostAddress();
                        System.out.println(clientHostAddress + " - " + line);
                        users.add(clientHostAddress);
                    } catch (Exception e) {
                        System.err.println("Oops");
                    }
                });
            }
        }
    }
}