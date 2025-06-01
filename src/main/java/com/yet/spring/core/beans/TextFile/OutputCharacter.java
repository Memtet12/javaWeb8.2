package com.yet.spring.core.beans.TextFile;

import com.yet.spring.core.beans.FileModule;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

@Component
public class OutputCharacter implements FileModule {

    @Override
    public boolean supports(String filePath) {
        return filePath.endsWith(".txt");
    }

    @Override
    public String getDescription() {
        return "Вывод частоты вхождения каждого символа";
    }

    @Override
    public void process(String filePath) {
        Scanner scanner = new Scanner(System.in);
        try {
            String content = Files.readString(Path.of(filePath));
            System.out.println("Введите символ, который хотите подсчитать");
            char character = scanner.nextLine().charAt(0);
            long count = content.chars().filter(c -> c ==character).count();
            System.out.println(String.format("Количество символа %s: %d",character,count));
        } catch (IOException e)
        {
            System.out.println("Error" + e.getMessage());
        }
    }
}
