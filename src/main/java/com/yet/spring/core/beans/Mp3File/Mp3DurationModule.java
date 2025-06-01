package com.yet.spring.core.beans.Mp3File;

import com.yet.spring.core.beans.FileModule;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Mp3DurationModule implements FileModule {
    @Override
    public boolean supports(String filePath) {
        return filePath.toLowerCase().endsWith(".mp3");
    }

    @Override
    public String getDescription() {
        return "Вывод длительности MP3-файла (в секундах)";
    }

    @Override
    public void process(String filePath) {
        try {
            Process process = new ProcessBuilder(
                    "ffmpeg",
                    "-i",
                    filePath
            ).redirectErrorStream(true).start();

            String output = new String(process.getInputStream().readAllBytes());
            double duration = extractDuration(output);

            if (duration > 0) {
                System.out.printf("Длительность: %.2f сек.\n", duration);
            } else {
                System.err.println("Не удалось определить длительность");
            }
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    private double extractDuration(String ffmpegOutput) {
        Pattern pattern = Pattern.compile("Duration: (\\d+):(\\d+):(\\d+)\\.\\d+");
        Matcher matcher = pattern.matcher(ffmpegOutput);

        if (matcher.find()) {
            int hours = Integer.parseInt(matcher.group(1));
            int minutes = Integer.parseInt(matcher.group(2));
            int seconds = Integer.parseInt(matcher.group(3));
            return hours * 3600 + minutes * 60 + seconds;
        }
        return 0;
    }
}