package com.yet.spring.core.beans.FileGz;

import java.io.*;
import java.util.zip.GZIPOutputStream;
import com.yet.spring.core.beans.FileModule;
import org.springframework.stereotype.Component;

@Component
public class CompressionModule implements FileModule {
    @Override
    public boolean supports(String filePath) {
        return true; // Поддерживаем все файлы
    }

    @Override
    public String getDescription() {
        return "Сжатие файла (GZIP)";
    }

    @Override
    public void process(String filePath) {
        try {
            String compressedPath = filePath.replaceAll("\\.[^.]+$", ".gz");
            compressFile(filePath, compressedPath);
            System.out.printf("Файл сжат. Результат: %s (%.2f%% от оригинала)\n", compressedPath, (new File(compressedPath).length() * 100.0 / new File(filePath).length()));
        } catch (IOException e) {
            System.err.println("Ошибка сжатия: " + e.getMessage());
        }
    }

    public static void compressFile(String sourceFile, String compressedFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(sourceFile);
             GZIPOutputStream gzos = new GZIPOutputStream(new FileOutputStream(compressedFile))) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                gzos.write(buffer, 0, len);
            }
        }
    }
}
