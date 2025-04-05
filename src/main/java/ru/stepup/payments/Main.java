package ru.stepup.payments;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        int count = 0;
        ArrayList<LogEntry> logEntriesArrays = new ArrayList<>();
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
                        line = line.replaceAll(" -", "");
                        line = line.replaceAll("\\[", "");
                        line = line.replaceAll("0300] ", "");
                        line = line.replaceAll("[+]", "");
                        LogEntry logEntry = new LogEntry(line);
                        logEntriesArrays.add(logEntry);
                        //System.out.println(logEntry.toString());
                    }
                    Statistics st = new Statistics();
                    //System.out.println(logEntriesArrays);
                    st.addEntry(logEntriesArrays);
                    System.out.println("Полный объем трафика за весь период = " + st.getTotalTraffic());
                    System.out.println("Средний объем трафика за час = " + st.getTotalTrafficHour());
                    //System.out.println("За выбранный период ");
                    //st.getTrafficRate(logEntriesArrays, LocalDate.of(2022, Month.SEPTEMBER, 25).atTime(6, 25, 5),
                    //       LocalDate.of(2022, Month.SEPTEMBER, 25).atTime(6, 25, 7));
                    //System.out.println("Общий объем трафика за выбранный период: " + st.getMinTime() + " до " + st.getMaxTime() + " = " + st.getTotalTrafficPeriod());
                    //System.out.println("Список страниц с кодом 200 = "+st.getPageOk().toString());
                    System.out.println("---------------------------------------");
                    System.out.println("Частота встречаемости ОС: " + st.getOs());
                    System.out.println("Доля ОС в общем объеме: " + st.getOsStatictic());
                    System.out.println("Список страниц с ошибкой 404: " + st.getPageError().toString());
                    System.out.println("Частота встречаемости браузеров: " + st.getBrowser());
                    System.out.println("Количество ботов, посещаемых сайт: " + st.getBot());
                    System.out.println("Доля браузеров в общем объеме: " + st.getBrowserStat());
                    System.out.println("---------------------------------------");
                    System.out.println("Среднее количество посещений за час: " + st.numberOfVisitsPerHour());
                    System.out.println("Cреднее количество ошибочных запросов в час: " + st.numbersOfErrorsRequest());
                    System.out.println("Средняя посещаемость одним пользователем: " + st.averageAttendanceUser());
                    System.out.println("---------------------------------------");
                    System.out.println("Пиковая посещаемость сайта (в секунду): " + st.peakAttendanceSecond());
                    System.out.println("Список сайтов: " + st.listOfSites());
                    System.out.println("Максимальная посещаемость уникальным пользователем: " + st.maximumAttendanceUser());
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
//C:\Users\AMelekhina\Documents\AT\access.log