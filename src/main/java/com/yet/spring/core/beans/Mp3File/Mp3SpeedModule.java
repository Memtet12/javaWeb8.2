package com.yet.spring.core.beans.Mp3File;

import com.yet.spring.core.beans.FileModule;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

@Component
public class Mp3SpeedModule implements FileModule {
    @Override
    public boolean supports(String filePath) {
        return filePath.toLowerCase().endsWith(".mp3");
    }

    @Override
    public String getDescription() {
        return "Ускорение или замедление MP3-файла на указанный процент";
    }

    @Override
    public void process(String filePath) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Насколько процентов ускорить песню?");
            double percentage = scanner.nextDouble();
            Path inputPath = Path.of(filePath);
            Path outputPath = inputPath.resolveSibling("modified_" + inputPath.getFileName());

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "ffmpeg",
                    "-i",
                    filePath,
                    "-af",
                    String.format("atempo=%s", 1 + percentage / 100),
                    outputPath.toString()
            );

            Process process = processBuilder.redirectErrorStream(true).start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Файл успешно обработан и сохранен как " + outputPath.getFileName());
            } else {
                System.err.println("Ошибка при обработке файла");
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}

