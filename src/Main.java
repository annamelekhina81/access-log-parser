import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ExceptionError {
        int count = 0;
        int max = 0;
        int min = 0;
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

                        if (length > max) {
                            max = length;
                        }
                        if (length < max) {
                            min = length;
                        }
                        i = i + 1;
                    }
                    System.out.println("Количество строк в файле: " + i);
                    System.out.println("Размер самой длиной строки " + max);
                    System.out.println("Размер самой короткой строки " + min);
                } catch (IOException ex) {
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


