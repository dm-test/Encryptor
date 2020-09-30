package com.github.dmtest.utils.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTextWriter implements TextWriter {
    private static final Logger LOG = LoggerFactory.getLogger(FileTextWriter.class);
    private static final Path PATH = Paths.get("data", "out.txt");

    @Override
    public void writeText(String text) {
        byte[] bytes = text.getBytes();
        try {
            Files.write(PATH, bytes);
        } catch (IOException e) {
            LOG.error("Не удалось записать данные в файл");
            throw new IOError(e);
        }
    }
}
