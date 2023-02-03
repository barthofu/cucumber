package org.cucumber.models.so;

import lombok.Getter;
import lombok.Setter;
import org.cucumber.common.dto.SocketMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Getter
@Setter
public class SocketClient {

    private static int idCounter = 0;
    private static int queueCounter = 0;
    //********//

    private int id;
    private Socket socket;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    boolean isActive;

    public SocketClient(Socket socket) {
        id = ++idCounter;
        this.socket = socket;

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            System.out.println("New Connection (" + id + ")");
            isActive = true;
        } catch (IOException ioException) {
            System.out.println("Can't get client streams");
        }
    }


    public void send(SocketMessage message) {
        try {
            this.out.writeObject(message);
            this.out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeClient() {
        try {
            this.in.close();
            this.out.close();
            this.socket.close();
        } catch (IOException ioException) {
            System.out.println("can't close client (" + id + ")");
        }
    }

}
