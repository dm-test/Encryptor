package com.github.dmtest.utils.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTextReader implements TextReader {
    private static final Logger LOG = LoggerFactory.getLogger(FileTextReader.class);
    private static final Path PATH = Paths.get("data", "in.txt");

    @Override
    public String readText() {
        try {
            return String.join("", Files.readAllLines(PATH));
        } catch (IOException e) {
            LOG.error("Не удалось прочитать файл");
            throw new IOError(e);
        }
    }
}
