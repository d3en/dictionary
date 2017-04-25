package ru.d3en.issart.dictionary.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Собственно сервер.
 * Слушает заданный при старте порт
 * и запускает новый поток для каждого нового клиента.
 */
public class Server {

    /**
     * Старт сервера
     * @param serverPort
     * @throws IOException
     */
    public void start(int serverPort) throws IOException {
        // Открываем основной сокет
        ServerSocket serverSocket = new ServerSocket(serverPort);
        System.out.println("Server Started");
        try {
            while (true) {
                Socket threadSocket = serverSocket.accept();
                // Для каждого нового соединения создаем новый поток
                try {
                    new ThreadServer(threadSocket);
                } catch (IOException e) {
                    threadSocket.close();
                }
            }
        } finally {
            serverSocket.close();
        }
    }

}
