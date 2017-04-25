package ru.d3en.issart.dictionary.client;

import ru.d3en.issart.dictionary.common.ConnectParams;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Главный класс клиента
 */
public class Main {

    /**
     * точка входа в программу
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        // Обрабатываем аргументы запуска клиента
        ArgsHandler argsHandler = new ArgsHandler(args);
        if (!argsHandler.checkArgsCount()) return;
        InetAddress serverAddress = argsHandler.getServerAddress();
        if (serverAddress == ConnectParams.INCORRECT_SERVER_ADDRESS) return;
        int serverPort = argsHandler.getServerPort();
        if (serverPort == ConnectParams.INCORRECT_PORT_NUMBER) return;

        Client client = new Client(serverAddress, serverPort);
        /*
        System.out.println(String.format("Попытка подключения к %s:%s ...",
                serverAddress.getCanonicalHostName(),
                serverPort));
        */
        try {
            // Подключаемся к серверу
            client.connect();
            //System.out.println("Соединение к серверу установлено.");
        } catch (IOException e) {
            System.out.println("Ошибка подключения к серверу.");
            return;
        }
        try {
            // Отправляем запрос серверу
            client.send(argsHandler.getCommandWithParams());
        } catch (IOException e) {
            System.out.println("Ошибка обмена данными с сервером.");
        } finally {
            try {
                // Закрываем соединение к серверу
                client.close();
            } catch (Exception e) {
                System.out.println("Ошибка закрытия соединения с сервером.");
            }
            //System.out.println("Соединение к серверу закрыто.");
        }
    }

}
