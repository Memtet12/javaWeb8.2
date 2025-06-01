package com.yet.spring.core.beans.TextFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.yet.spring.core.beans.FileModule;
import org.springframework.stereotype.Component;
@Component
public class TextLineCounterModule implements FileModule {

    @Override
    public boolean supports(String filePath)
    {
        return filePath.endsWith(".txt");
    }

    @Override
    public String getDescription() {
        return "Подсчёт количества строк в файле";
    }

    @Override
    public void process(String filePath) {
        try {
            long lineCount = Files.lines(Path.of(filePath)).count();
            System.out.println("Количество строк : " + lineCount);
        } catch (IOException e)
        {
            System.out.println("Error" + e.getMessage());
        }
    }
}
