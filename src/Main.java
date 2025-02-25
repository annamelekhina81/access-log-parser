import java.io.File;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int count=0;

        while (true){
            String path = new Scanner(System.in).nextLine();
            File file = new File (path);
            if (file.exists() && !file.isDirectory()){
                count++;
                System.out.println("Путь указан верно. Это файл № "+ count );
                continue;
            }
            else if ((!file.exists() && !file.isDirectory())) {
                System.out.println("Указанного файла (директории) не существует или путь указан не верно");
                continue;

            }
            else if (file.exists()&& file.isDirectory()){
                System.out.println("Вы указали путь к директории. Введите путь к файлу");
                continue;
            }

        }

    }

}


