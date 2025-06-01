package com.yet.spring.core.beans.ImageFile;

import com.yet.spring.core.beans.FileModule;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class ImageSizeModule implements FileModule {
    @Override
    public boolean supports(String filePath) {
        return filePath.matches("(?i).*\\.(jpg|jpeg|png|gif|bmp)$");
    }

    @Override
    public String getDescription() {
        return "Вывод размера изображения (ширина x высота)";
    }

    @Override
    public void process(String filePath) {
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            if (image == null) {
                System.err.println("Файл не является поддерживаемым изображением");
                return;
            }
            System.out.printf("Размер изображения: %d x %d пикселей\n",
                    image.getWidth(), image.getHeight());
        } catch (IOException e) {
            System.err.println("Ошибка чтения изображения: " + e.getMessage());
        }
    }
}