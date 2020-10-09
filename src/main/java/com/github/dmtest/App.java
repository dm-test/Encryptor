package com.github.dmtest;

import com.github.dmtest.utils.cripto.*;
import com.github.dmtest.utils.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class App {
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        TextReader ftr = new FileTextReader();
        String originalText = ftr.readText();
        LOG.info("Исходный текст из файла: {}", originalText);
        LOG.info("Выберите метод шифрования: \n 1 - Шифр Цезаря \n 2 - Аффинный шифр \n 3 - Шифр Виженера \n 4 - Шифрование гаммированием");
        ConsoleTextReader ctr = new ConsoleTextReader();
        int selectedCipher = ctr.readInt();
        LOG.info("Введите тип операции: \n 1 - Шифрование \n 2 - Дешифровка \n 3 - Взлом");
        Command command = ctr.readCommand();
        Cipher cipher;
        String newText;
        switch (selectedCipher) {
            case 1:
                LOG.info("Введите значение сдвига");
                int shift = ctr.readInt();
                cipher = new CesarCipher(shift);
                newText = cipher.processText(originalText, command);
                break;
            case 2:
                List<Integer> availableKey1List = AffineCipher.getAvailableKey1List();
                LOG.info("Введите значение ключа1. Допустимые значения: {}", availableKey1List);
                int key1 = ctr.readAvailableInt(availableKey1List);
                LOG.info("Введите значение ключа2");
                int key2 = ctr.readInt();
                cipher = new AffineCipher(key1, key2);
                newText = cipher.processText(originalText, command);
                break;
            case 3:
                if (command.equals(Command.BREAK)) {
                    VigenerCipher vigenerCipher = new VigenerCipher("");
                    newText = vigenerCipher.breakCipher(originalText);
                } else {
                    LOG.info("Введите значение ключа");
                    String key = ctr.readText();
                    cipher = new VigenerCipher(key);
                    newText = cipher.processText(originalText, command);
                }
                break;
            case 4:
                LOG.info("Введите значение ключа");
                int key = ctr.readInt();
                cipher = new GammaCipher(key);
                newText = cipher.processText(originalText, command);
                break;
            default:
                LOG.error("Выбран некорректный метод шифрования. Операция не выполнена.");
                newText = "";
        }
        LOG.info("Производный текст: {}", newText);
        TextWriter ftw = new FileTextWriter();
        ftw.writeText(newText);
    }
}
