package com.yet.spring.core.beans.TextFile;

import com.yet.spring.core.beans.FileModule;
import org.springframework.stereotype.Component;

import javax.imageio.IIOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

@Component
public class WriteInEndFile implements FileModule {
    @Override
    public boolean supports(String filePath) {
        return filePath.endsWith(".txt");
    }

    @Override
    public String getDescription() {
        return "Дописывает в конец файла строку";
    }

    @Override
    public void process(String filePath) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Что хотите записать в конец файла?");
            String content = scanner.nextLine();

            Files.writeString(
                    Path.of(filePath),
                    content + "\n",
                    StandardOpenOption.APPEND
            );
            System.out.println("Текст успешно добавлен в файл");
        } catch (IOException e){
            System.out.println("Ошибка при записи в файл");
        }

    }
}
