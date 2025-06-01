package com.yet.spring.core.beans.ImageFile;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.yet.spring.core.beans.FileModule;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class ExifDataModule implements FileModule {
    @Override
    public boolean supports(String filePath) {
        return filePath.matches("(?i).*\\.(jpg|jpeg|tiff)$");
    }

    @Override
    public String getDescription() {
        return "Вывод EXIF-данных изображения";
    }

    @Override
    public void process(String filePath) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(new File(filePath));

            System.out.println("=== EXIF-данные изображения ===");
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    System.out.printf("[%s] %s = %s\n",
                            directory.getName(),
                            tag.getTagName(),
                            tag.getDescription());
                }
            }
        } catch (ImageProcessingException | IOException e) {
            System.err.println("Ошибка чтения метаданных: " + e.getMessage());
        }
    }
}