package ru.stepup.payments;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        int count = 0;
        int ya = 0;
        int g = 0;
        int i = 0;
        while (true) {
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            if (file.exists() && !file.isDirectory()) {
                count++;
                System.out.println("Путь указан верно. Это файл № " + count);
                try {
                    FileReader fileReader = new FileReader(path);
                    BufferedReader reader = new BufferedReader(fileReader);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        int length = line.length();
                        if (length >= 1024) throw new ExceptionError("В файле длина строки более 1024 символов");
                        String[] parts = line.split(" ");
                        if (parts.length >= 2) {
                            for (int j = 0; j <= parts.length - 1; j++) {
                                parts[j].replaceAll(" ", "");
                                if (parts[j].contains("YandexBot")) {
                                    ya = ya + 1;
                                }
                                if (parts[j].contains("Googlebot")) {
                                    g = g + 1;
                                }
                                // System.out.print(parts[j]); вывод фрагментов в консоль для самопроверки
                            }
                        }
                        // System.out.println();вывод фрагментов в консоль для самопроверки с разделением
                        i = i + 1;
                    }
                    System.out.println("Количество строк в файле: " + i);
                    System.out.println("YandexBot " + ya + " Доля запросов (%): " + ((double) ya / i) * 100);
                    System.out.println("Googlebot " + g + " Доля запросов (%): " + ((double) g / i) * 100);
                    System.out.println();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                continue;
            } else if ((!file.exists() && !file.isDirectory())) {
                System.out.println("Указанного файла (директории) не существует или путь указан не верно");
                continue;
            } else if (file.exists() && file.isDirectory()) {
                System.out.println("Вы указали путь к директории. Введите путь к файлу");
                continue;
            }
        }
    }
}
