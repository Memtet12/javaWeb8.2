package com.yet.spring.core.beans.DirectoryModule;

import com.yet.spring.core.beans.FileModule;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Component
public class DirectorySizeModule implements FileModule {
    @Override
    public boolean supports(String filePath) {
        return Files.isDirectory(Path.of(filePath));
    }

    @Override
    public String getDescription() {
        return "Подсчёт размера всех файлов в каталоге";
    }

    @Override
    public void process(String filePath) {
        try (Stream<Path> stream = Files.walk(Path.of(filePath))) {
            long totalSize = stream.mapToLong(path -> {
                try {
                    return Files.size(path);
                } catch (IOException e) {
                    System.err.println("Ошибка при получении размера файла: " + e.getMessage());
                    return 0;
                }
            }).sum();

            System.out.printf("Общий размер файлов в каталоге: %d байт%n", totalSize);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении каталога: " + e.getMessage());
        }
    }
}
