package com.github.dmtest;

import com.github.dmtest.utils.cripto.CesarCipher;
import com.github.dmtest.utils.cripto.Cipher;
import com.github.dmtest.utils.cripto.Command;
import com.github.dmtest.utils.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        TextReader ftr = new FileTextReader();
        String originalText = ftr.readText();
        LOG.info("Исходный текст из файла: {}", originalText);
        LOG.info("Выберите метод шифрования: \n 1 - Шифр Цезаря");
        ConsoleTextReader ctr = new ConsoleTextReader();
        int selectedCipher = ctr.readInt();
        LOG.info("Введите тип операции: \n 1 - Шифрование \n 2 - Дешифровка");
        Command command = ctr.readCommand();
        Cipher cipher;
        String newText;
        switch (selectedCipher) {
            case 1:
                LOG.info("Введите значение сдвига");
                int offset = ctr.readInt();
                cipher = new CesarCipher(offset);
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
