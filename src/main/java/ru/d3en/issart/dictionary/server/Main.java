package ru.d3en.issart.dictionary.server;

import ru.d3en.issart.dictionary.common.ConnectParams;

import java.io.IOException;

/**
 * Главный класс сервера
 */
public class Main {

    /**
     * точка входа в программу
     * @param args
     */
    public static void main(String[] args) {

        // Обрабатываем аргументы запуска сервера
        ArgsHandler argsHandler = new ArgsHandler(args);
        if (argsHandler.checkArgsCount()) {
            // Получаем номер порта
            int serverPort = argsHandler.getServerPort();
            if (serverPort != ConnectParams.INCORRECT_PORT_NUMBER) {
                // Запускаем сервер
                Server server = new Server();
                try {
                    server.start(serverPort);
                } catch (IOException e) {
                    System.out.println(String.format("Не удалось запустить сервер на порту %s", serverPort));
                }
            }
        }

    }

}
