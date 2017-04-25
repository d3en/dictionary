package ru.d3en.issart.dictionary.client;

import ru.d3en.issart.dictionary.common.ConnectParams;
import ru.d3en.issart.dictionary.common.ProtocolConstants;

import java.net.InetAddress;

/**
 * Обработчик аргументов запуска клиента
 */
public class ArgsHandler {

    /**
     * Минимальное количество аргументов запуска клиента
     */
    private static final int MIN_ARGS_COUNT = 4;
    /**
     * Индекс значения IP адреса сервера в массиве аргументов запуска сервера
     */
    private static final int SERVER_IP_INDEX_OF_ARGS = 0;
    /**
     * Индекс значения порта в массиве аргументов запуска сервера
     */
    private static final int PORT_INDEX_OF_ARGS = 1;
    /**
     * Индекс значения команды запроса в массиве аргументов запуска сервера
     */
    private static final int COMMAND_INDEX_OF_ARGS = 2;
    /**
     * Индекс значения запрашиваемого слова в массиве аргументов запуска сервера
     */
    private static final int WORD_INDEX_OF_ARGS = 3;

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
        if (args.length < MIN_ARGS_COUNT) {
            System.out.println(
                    String.format(
                            "%s\n%s\nПримеры:\n%s\n%s\n%s",
                            "Команда для запуска клиента должна быть:",
                            "java -jar client.jar <server_IP> <server_PORT> <command> <param1> [<param2> ...]",
                            "java -jar client.jar 127.0.0.1 5050 add hello алло привет здравствуйте",
                            "java -jar client.jar 127.0.0.1 5050 get hello",
                            "java -jar client.jar 127.0.0.1 5050 delete hello привет"
                    )
            );
            result = false;
        }
        return result;
    }

    /**
     * Получает адрес сервера.
     * @return
     */
    public InetAddress getServerAddress() {
        InetAddress inetAddress = ConnectParams.INCORRECT_SERVER_ADDRESS;
        try {
            inetAddress = InetAddress.getByName(args[SERVER_IP_INDEX_OF_ARGS]);
        } catch (Exception e) {
            System.out.println("Неверно задан <server_IP>");
        }
        return inetAddress;
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

    /**
     * Формирует запрос серверу вида command:word,value1,value2, ...
     * @return
     */
    public String getCommandWithParams() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(args[COMMAND_INDEX_OF_ARGS]).append(ProtocolConstants.SPLIT_COMMAND_VALUES);
        for (int i = WORD_INDEX_OF_ARGS; i < args.length; i++) {
            stringBuilder.append(args[i]).append(ProtocolConstants.SPLIT_KEY_VALUES);
        }
        return stringBuilder.deleteCharAt(stringBuilder.length()-1).toString();
    }

    public static int getCommandIndexOfArgs() {
        return COMMAND_INDEX_OF_ARGS;
    }

}
