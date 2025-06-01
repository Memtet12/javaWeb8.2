package com.yet.spring.core.beans.ImageFile;

import com.yet.spring.core.beans.FileModule;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class NegativeModule implements FileModule {
    @Override
    public boolean supports(String filePath) {
        return filePath.matches("(?i).*\\.(jpg|jpeg|png|gif|bmp)$");
    }

    @Override
    public String getDescription() {
        return "Делает картинку негативной";
    }

    @Override
    public void process(String filePath) {
            try {
                BufferedImage img = ImageIO.read(new File(filePath));
                int width = img.getWidth();
                int height = img.getHeight();
                // Преобразование в негативное
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int p = img.getRGB(x, y);
                        int a = (p >> 24) & 0xff;
                        int r = (p >> 16) & 0xff;
                        int g = (p >> 8) & 0xff;
                        int b = p & 0xff;
                        r = 255 - r;
                        g = 255 - g;
                        b = 255 - b;
                        p = (a << 24) | (r << 16) | (g << 8) | b;
                        img.setRGB(x, y, p);
                    }
                }
                try {
                    File file = new File(filePath);
                    ImageIO.write(img, "jpg", file);
                    System.out.println("Изображение негативное!");
                } catch (IOException e) {
                    System.out.println("Ошибка записи изображения:" + e);
                }
            } catch (IOException e)
            {
                System.out.println("Ошибка:" + e);
            }
        }

}