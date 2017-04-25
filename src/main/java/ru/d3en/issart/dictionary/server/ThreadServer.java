package ru.d3en.issart.dictionary.server;

import java.io.*;
import java.net.Socket;

/**
 * Поток обрабатывает входящее сообщение от клиента и отправляет ответ
 */
public class ThreadServer extends Thread {

    /**
     * socket для обмена данными с клиентом.
     */
    private Socket socket;

    /**
     * Конструктор. Принимает socket для работы и запускает новый поток.
     * @param socket
     * @throws IOException
     */
    public ThreadServer(Socket socket) throws IOException {
        this.socket = socket;
        start();
    }

    /**
     * Обрабатывает входящее сообщение от клиента и отправляет ответ.
     */
    public void run() {
        try {
            // Получаем вход и выход для обмена данными
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            // Читаем из socket
            String inMessage = in.readLine();
            // Отправляем ответ
            String outMessage = Dictionary.getInstance().handleMessage(inMessage);
            out.println(outMessage);
        }
        catch (IOException e) {
            System.err.println("Ошибка обмена данными с клиентом.");
        }
        finally {
            try {
                // Закрываем соединение
                socket.close();
            }
            catch (IOException e) {
                System.err.println("Ошибка закрытия соединения с клиентом.");
            }
        }
    }
}
