package com.modzo.iptv.scanner.exporter;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileWriter {

    public void toFile(String exportedInvalidChannels, String fileName) {
        try {
            Path path = Paths.get(fileName);
            System.out.println(path.getFileName().toString());
            try {
                Files.createFile(path);
            } catch (Exception ignored) {
            }
            Files.write(path, exportedInvalidChannels.getBytes());
            System.out.println(Files.readAllLines(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
