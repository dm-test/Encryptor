package com.github.dmtest.utils.io;

import com.github.dmtest.utils.cripto.Command;
import org.apache.commons.io.input.CloseShieldInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleTextReader implements TextReader {
    private static final Logger LOG = LoggerFactory.getLogger(ConsoleTextReader.class);

    @Override
    public String readText() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new CloseShieldInputStream(System.in)))) {
            return br.readLine();
        } catch (IOException e) {
            LOG.info("Не удалось считать введенную строку!");
            throw new IOError(e);
        }
    }

    public int readInt() {
        while (true) {
            try {
                String num = readText();
                return Integer.parseInt(num);
            } catch (NumberFormatException e) {
                LOG.error("Не удалось преобразовать в число. Повторите ввод");
            }
        }

    }

    public Command readCommand() {
        int commandNum = readInt();
        while (true) {
            try {
                return Command.values()[commandNum - 1];
            } catch (IndexOutOfBoundsException e) {
                LOG.error("Не удалось получить комманду по введенному числу. Повторите ввод");
            }
        }
    }
}
