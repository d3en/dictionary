package ru.d3en.issart.dictionary.client;

import ru.d3en.issart.dictionary.common.ProtocolConstants;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Предназначен для работы с сервером: подключение, отправка сообщения, отключение.
 */
public class Client {

    /**
     * InetAddress сервера
     */
    private InetAddress serverAddress;
    /**
     * Порт сервера
     */
    private int serverPort;
    /**
     * Socket к серверу
     */
    private Socket socket;

    /**
     * Конструктор
     * @param serverAddress
     * @param serverPort
     */
    public Client(InetAddress serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    /**
     * Устанавливает соединение с сервером с заданными адресом и портом
     * @throws IOException
     */
    public void connect() throws IOException {
        socket = new Socket(serverAddress, serverPort);
    }

    /**
     * Отправляет сообщение на сервер, получает ответ и выводит его на System.out
     * @param outMessage
     * @throws IOException
     */
    public void send(String outMessage) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())), true);
        out.println(outMessage);
        String inMessage = in.readLine();
        System.out.println(inMessage.replace(ProtocolConstants.SPLIT_KEY_VALUES, "\n"));
    }

    /**
     * Закрывает соединение с сервером
     * @throws IOException
     */
    public void close() throws IOException {
        socket.close();
    }

}
