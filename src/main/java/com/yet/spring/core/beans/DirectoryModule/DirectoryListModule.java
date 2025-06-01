package com.yet.spring.core.beans.DirectoryModule;

import com.yet.spring.core.beans.FileModule;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Component
public class DirectoryListModule implements FileModule {
    @Override
    public boolean supports(String filePath) {
        return Files.isDirectory(Path.of(filePath));
    }

    @Override
    public String getDescription() {
        return "Вывод списка файлов в каталоге";
    }

    @Override
    public void process(String filePath) {
        try (Stream<Path> stream = Files.walk(Path.of(filePath))) {
            stream.forEach(path -> System.out.println(path.toAbsolutePath()));
        } catch (IOException e) {
            System.err.println("Ошибка при чтении каталога: " + e.getMessage());
        }
    }
}
