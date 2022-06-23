package JavaNetworking;

import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    @SneakyThrows
    public static void main(String[] args) {
        try(var socket = new Socket(InetAddress.getLocalHost().getHostAddress(),8899)) {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            var message = "Hello \n";
            writer.write(message);
            writer.flush();
        }
    }

}
