package ru.d3en.issart.dictionary.common;

import java.net.InetAddress;

/**
 * Контейнер констант параметров коннекта
 */
public class ConnectParams {
    /**
     * Минимально допустимый для приложения номер порта
     */
    public static final int MIN_PORT_NUMBER = 81;
    /**
     * Максимально допустимый номер порта
     */
    public static final int MAX_PORT_NUMBER = 65535;
    /**
     * Значение некоррктно заданного номера порта
     */
    public static final int INCORRECT_PORT_NUMBER = 0;
    /**
     * Значение некоррктно заданного адреса сервера
     */
    public static final InetAddress INCORRECT_SERVER_ADDRESS = null;
}
