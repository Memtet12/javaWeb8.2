package com.yet.spring.core.beans.Mp3File;

import com.yet.spring.core.beans.FileModule;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Mp3TitleModule implements FileModule {
    @Override
    public boolean supports(String filePath) {
        return filePath.toLowerCase().endsWith(".mp3");
    }

    @Override
    public String getDescription() {
        return "Вывод названия трека из MP3-тегов";
    }

    @Override
    public void process(String filePath) {
        try {
            Process process = new ProcessBuilder(
                    "ffmpeg",
                    "-i",
                    filePath,
                    "-f",
                    "ffmetadata",
                    "-"
            ).redirectErrorStream(true).start();

            String output = new String(process.getInputStream().readAllBytes());
            String title = extractMetadata(output, "title");

            if (title != null) {
                System.out.println("Название трека: " + title);
            } else {
                System.err.println("Тег 'title' не найден в файле");
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения тегов: " + e.getMessage());
        }
    }

    private String extractMetadata(String ffmpegOutput, String tagName) {
        Pattern pattern = Pattern.compile(tagName + "=(.+)");
        Matcher matcher = pattern.matcher(ffmpegOutput);
        return matcher.find() ? matcher.group(1) : null;
    }
}