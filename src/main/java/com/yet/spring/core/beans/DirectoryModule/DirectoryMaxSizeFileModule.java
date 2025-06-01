package com.yet.spring.core.beans.DirectoryModule;

import com.yet.spring.core.beans.FileModule;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class DirectoryMaxSizeFileModule implements FileModule {
    @Override
    public boolean supports(String filePath) {
        return Files.isDirectory(Path.of(filePath));
    }

    @Override
    public String getDescription() {
        return "Поиск файла с максимальным размером в каталоге";
    }

    @Override
    public void process(String filePath) {
        try (Stream<Path> stream = Files.walk(Path.of(filePath))) {
            Optional<Path> maxSizeFile = stream.max((p1, p2) -> {
                try {
                    return Long.compare(Files.size(p1), Files.size(p2));
                } catch (IOException e) {
                    System.err.println("Ошибка при получении размера файла: " + e.getMessage());
                    return 0;
                }
            });

            if (maxSizeFile.isPresent()) {
                System.out.printf("Файл с максимальным размером: %s, размер: %d байт%n",
                        maxSizeFile.get().toAbsolutePath(), Files.size(maxSizeFile.get()));
            } else {
                System.out.println("Каталог пуст или ошибка при чтении");
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении каталога: " + e.getMessage());
        }
    }
}
