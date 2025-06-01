package com.yet.spring.core.beans.FileGz;

import java.io.*;
import java.util.zip.GZIPInputStream;

import com.yet.spring.core.beans.FileModule;
import org.springframework.stereotype.Component;

@Component
public class DecompressionModule implements FileModule {
    @Override
    public boolean supports(String filePath) {
        return filePath.endsWith(".gz");
    }

    @Override
    public String getDescription() {
        return "Разархивация архива .gz (GZIP)";
    }

    @Override
    public void process(String filePath) {
        try {
            String compressedPath = filePath.replace(".gz", "");
            deCompressFile(filePath, compressedPath);
            System.out.println("Файл распакован: " + compressedPath);
        } catch (IOException e) {
            System.err.println("Ошибка распаковки: " + e.getMessage());
        }
    }

    public static void deCompressFile(String compressedFile, String outputFile) throws IOException {
        try (GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(compressedFile));
             FileOutputStream fos = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        }
    }
}
