package ru.d3en.issart.dictionary.server;

import ru.d3en.issart.dictionary.common.ConnectParams;

/**
 * Обработчик аргументов запуска сервера
 */
public class ArgsHandler {

    /**
     * Необходимое количество аргументов запуска сервера
     */
    private final int ARGS_COUNT = 1;
    /**
     * Индекс значения порта в массиве аргументов запуска сервера
     */
    private final int PORT_INDEX_OF_ARGS = 0;
    /**
     * Массив аргументов запуска сервера
     */
    private String[] args;

    /**
     * Конструктор
     * @param args
     */
    public ArgsHandler(String[] args) {
        this.args = args;
    }

    /**
     * Проверяет количество переданных аргументов.
     * В случае несоответствия выводит предупреждение в System.out и возвращает false.
     * @return
     */
    public boolean checkArgsCount() {
        boolean result = true;
        if (args.length != ARGS_COUNT) {
            System.out.println(
                    String.format(
                            "%s\n%s\nПример:\n%s",
                            "Команда для запуска сервера должна быть:",
                            "java -jar server.jar <server_PORT>",
                            "java -jar server.jar 5050"
                    )
            );
            result = false;
        }
        return result;
    }

    /**
     * Получает значение порта сервера,
     * проверяя на вхождение в диапазон допустимых значений.
     * @return
     */
    public int getServerPort() {
        int serverPort;
        try {
            serverPort = Integer.valueOf(args[PORT_INDEX_OF_ARGS]);
            if (serverPort < ConnectParams.MIN_PORT_NUMBER || serverPort > ConnectParams.MAX_PORT_NUMBER) {
                System.out.println(
                        String.format(
                                "Неверно задан <server_PORT>.\nДиапазон допустимых значений %s .. %s",
                                ConnectParams.MIN_PORT_NUMBER, ConnectParams.MAX_PORT_NUMBER));
                serverPort = ConnectParams.INCORRECT_PORT_NUMBER;
            }
        } catch (NumberFormatException e) {
            serverPort = ConnectParams.INCORRECT_PORT_NUMBER;
        }
        return serverPort;
    }

}
