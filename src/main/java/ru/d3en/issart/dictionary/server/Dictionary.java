package ru.d3en.issart.dictionary.server;

import ru.d3en.issart.dictionary.common.ProtocolConstants;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Класс Словарь.
 * Находится в памяти приложения в единственном экземпляре - Singleton.
 * Обрабатывает запросы и формирует ответ.
 */
public class Dictionary {

    /**
     * Хранилище слов и его значений.
     */
    private ConcurrentSkipListMap<String, ConcurrentSkipListSet<String>> map;

    // Singleton
    private static volatile Dictionary instance;
    //
    public static Dictionary getInstance() {
        Dictionary localInstance = instance;
        if (localInstance == null) {
            synchronized (Dictionary.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Dictionary();
                }
            }
        }
        return localInstance;
    }

    /**
     * Конструктор по умолчанию.
     * Модификатор доступа privat предотвращает создание экземпляра класса напрямую, минуя getInstance().
     */
    private Dictionary() {
        map = new ConcurrentSkipListMap();
    }

    /**
     * Парсит входящее сообщение, вызывает запрашиваемую команду и возвращает ответ.
     * @param inMessage
     * @return
     */
    public String handleMessage(String inMessage) {
        String[] pair = inMessage.split(ProtocolConstants.SPLIT_COMMAND_VALUES);
        // Команда
        String command = pair[0];
        String[] values = pair[1].split(ProtocolConstants.SPLIT_KEY_VALUES);
        // Слово
        String keyMap = values[0];
        // Значения слова
        ConcurrentSkipListSet<String> valuesMap = new ConcurrentSkipListSet<>();
        for (int i = 1; i < values.length; i++) {
            valuesMap.add(values[i]);
        }
        // Определение и вызов запрашиваемой команды.
        String outMessage;
        if ("add".equals(command)) {
            outMessage = add(keyMap, valuesMap);
        } else if ("get".equals(command)) {
            outMessage = get(keyMap);
        } else if ("delete".equals(command)) {
            outMessage = delete(keyMap, valuesMap);
        } else outMessage = ProtocolConstants.BAD_COMMAND;
        // Возвращаем ответ.
        return outMessage;
    }

    /**
     * Добавляет слово в словарь.
     * @param keyMap
     * @param valuesMap
     * @return
     */
    private String add(String keyMap, ConcurrentSkipListSet<String> valuesMap) {
        String outMessage = ProtocolConstants.SUCCESS_ADDING_VALUES;
        try {
            if (map.containsKey(keyMap)) {
                map.get(keyMap).addAll(valuesMap);
            } else {
                map.put(keyMap, valuesMap);
            }
        } catch (Exception e) {
            outMessage = ProtocolConstants.FAILURE_ADDING_VALUES;
        }
        return outMessage;
    }

    /**
     * Получает значения слова из словаря
     * @param keyMap
     * @return
     */
    private String get(String keyMap) {
        String outMessage;
        StringBuilder stringBuilder = new StringBuilder();
        // Если слово найдено в словаре
        if (map.containsKey(keyMap)) {
            // но при этом значения слова не определены
            if (map.get(keyMap).isEmpty()) {
                outMessage = ProtocolConstants.BAD_VALUES;
            } else {
                // если же значения слова определены
                for (String value : map.get(keyMap)) {
                    stringBuilder.append(value).append(ProtocolConstants.SPLIT_KEY_VALUES);
                }
                outMessage = stringBuilder.deleteCharAt(stringBuilder.length()-1).toString();
            }
        } else {
            outMessage = ProtocolConstants.BAD_WORD;
        }
        return outMessage;
    }

    /**
     * Удаляет слово / значения слова
     * @param keyMap
     * @param valuesMap
     * @return
     */
    private String delete(String keyMap, ConcurrentSkipListSet<String> valuesMap) {
        String outMessage = ProtocolConstants.SUCCESS_DELETING_VALUES;
        // Если слово найдено в словаре
        if (map.containsKey(keyMap)) {
            // и при этом список удаляемых значений не пуст
            if (!valuesMap.isEmpty()) {
                if (!map.get(keyMap).removeAll(valuesMap)) {
                    outMessage = ProtocolConstants.BAD_WORD_VALUES;
                }
            } else {
                // если список удаляемых значений пуст, то удаляем всё слово
                map.remove(keyMap);
            }
        } else {
            outMessage = ProtocolConstants.BAD_WORD_VALUES;
        }
        return outMessage;
    }

}
