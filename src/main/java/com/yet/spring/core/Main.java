package com.yet.spring.core;

import com.yet.spring.core.beans.FileModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private List<FileModule> modules;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        String defaultPath = "E:\\JavaRep2025\\8.2\\LAB8.2\\example.txt";

        while (true) {
            System.out.println("\n=== Файловый процессор ===");
            System.out.print("Введите путь к файлу (или 'exit' для выхода): ");
            String filePath = scanner.nextLine().trim();

            if (filePath.equalsIgnoreCase("exit")) {
                System.out.println("Завершение работы...");
                break;
            }

            // Если ввод пустой - используем путь по умолчанию
            if (filePath.isEmpty()) {
                filePath = defaultPath;
                System.out.println("Используется файл по умолчанию: " + defaultPath);
            }

            // Проверка существования файла
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println("Ошибка: Файл не существует");
                continue;
            } else if (!file.isFile() && !file.isDirectory()) {
                System.err.println("Ошибка: Это не файл или директория!");
                continue;
            }

            processFile(filePath);
        }
    }

    private void processFile(String filePath) {
        List<FileModule> supportedModules = modules.stream()
                .filter(module -> module.supports(filePath))
                .toList();

        if (supportedModules.isEmpty()) {
            System.err.println("Нет доступных модулей для обработки этого файла");
            return;
        }

        System.out.println("\nДоступные операции:");
        for (int i = 0; i < supportedModules.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, supportedModules.get(i).getDescription());
        }

        System.out.print("Выберите операцию (0 для отмены): ");
        try {
            int choice = Integer.parseInt(new Scanner(System.in).nextLine());
            if (choice == 0) return;

            if (choice < 1 || choice > supportedModules.size()) {
                System.err.println("Неверный выбор операции!");
                return;
            }

            System.out.println("\nРезультат:");
            supportedModules.get(choice - 1).process(filePath);

        } catch (NumberFormatException e) {
            System.err.println("Ошибка: Введите число!");
        }
    }
}