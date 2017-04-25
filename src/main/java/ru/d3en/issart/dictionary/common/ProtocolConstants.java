package ru.d3en.issart.dictionary.common;

/**
 * Контейнер констант разделителей и ответов сервера
 * при обмене сообщениями клиента и сервера
 * Запрос клиента серверу, пример: command:word,value1,value2, ...
 * Ответ сервера: <string> или value1,value2, ...
 */
public class ProtocolConstants {
    /**
     * Разделитель между командой и параметрами запроса
     */
    public static final String SPLIT_COMMAND_VALUES = ":";
    /**
     * Разделитель между словом и значениями слова и между самими значениями
     */
    public static final String SPLIT_KEY_VALUES = ",";

    public static final String SUCCESS_ADDING_VALUES = "<значения слова успешно добавлены>";
    public static final String FAILURE_ADDING_VALUES = "<ошибка при добавлении значений слова в словарь>";
    public static final String BAD_WORD = "<слово отсутвует в словаре>";
    public static final String SUCCESS_DELETING_VALUES = "<значения слова успешно удалены>";
    public static final String BAD_VALUES = "<значения слова отсутвуют в словаре>";
    public static final String BAD_WORD_VALUES = "<слово/значение отсутвует в словаре>";
    public static final String BAD_COMMAND = "<команда не опознана>";
}
