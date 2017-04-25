package ru.d3en.issart.dictionary;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.d3en.issart.dictionary.client.ArgsHandler;
import ru.d3en.issart.dictionary.common.ProtocolConstants;
import ru.d3en.issart.dictionary.server.Dictionary;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Проверка функциональности взаимодействия без сетевого подключения
 */
public class DictionaryTest {

    private Dictionary dictionary;

    @Before
    public void initTest() {
        dictionary = Dictionary.getInstance();
    }

    @After
    public void afterTest() {
        dictionary = null;
    }

    // Несколько формальных тестов

    @Test
    public void testAddWordValues() throws Exception {
        String runParams = "add hello алло привет здравствуйте";
        assertEquals(ProtocolConstants.SUCCESS_ADDING_VALUES,
                dictionary.handleMessage(
                        getMessageToDictionary(runParams)
                )
        );
    }

    @Test
    public void testGetExistingWord() throws Exception {
        String runParams = "get hello";
        testAddWordValues();
        Set<String> expectedSet = new HashSet<>(Arrays.asList(
                getArgsByString("алло привет здравствуйте")));
        Set<String> actualSet = new HashSet<>(Arrays.asList(
                dictionary.handleMessage(getMessageToDictionary(runParams))
                        .split(ProtocolConstants.SPLIT_KEY_VALUES)
        ));
        assertEquals(expectedSet, actualSet);
    }

    @Test
    public void testGetNotExistingWord() throws Exception {
        testAddWordValues();
        assertEquals(ProtocolConstants.BAD_WORD,
                dictionary.handleMessage(
                        getMessageToDictionary("get hello2")
                )
        );
    }

    @Test
    public void testGetEmptyValues() throws Exception {
        dictionary.handleMessage(getMessageToDictionary("add hello3"));
        assertEquals(ProtocolConstants.BAD_VALUES,
                dictionary.handleMessage(
                        getMessageToDictionary("get hello3")
                )
        );
    }

    @Test
    public void testDeleteExistingValues() throws Exception {
        String runParams = "delete hello привет";
        testAddWordValues();
        assertEquals(ProtocolConstants.SUCCESS_DELETING_VALUES,
                dictionary.handleMessage(
                        getMessageToDictionary(runParams)
                )
        );
    }

    @Test
    public void testDeleteNotExistingValues() throws Exception {
        String runParams = "delete hello пока";
        testAddWordValues();
        assertEquals(ProtocolConstants.BAD_WORD_VALUES,
                dictionary.handleMessage(
                        getMessageToDictionary(runParams)
                )
        );
    }

    // Получение отформатированного сообщения для сервера по строке параметров
    private String getMessageToDictionary(String strArgs) {
        // Вставляем фиктивные артгументы, имитирующие адрес, порт сервера...
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ArgsHandler.getCommandIndexOfArgs(); i++) {
            stringBuilder.append(i).append(" ");
        }
        stringBuilder.append(strArgs);
        // Создаем обработчик аргументов запуска клиента
        ArgsHandler clientArgsHandler =  new ArgsHandler(getArgsByString(stringBuilder.toString()));
        // формируем с помощью него сообщение к Dictionary
        return clientArgsHandler.getCommandWithParams();
    }

    // Получение массива строк по строке параметров
    private String[] getArgsByString(String strArgs) {
        String[] result = strArgs.split("\\s+");
        return result;
    }

}