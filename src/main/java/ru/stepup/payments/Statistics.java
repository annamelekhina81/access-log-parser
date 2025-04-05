package ru.stepup.payments;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Statistics {
    LogEntry logEntry;
    private long totalTraffic, totalTrafficPeriod, totalTrafficHour, hours;
    private LocalDateTime minTime, maxTime;
    private final HashSet<String> pageOk = new HashSet<>();
    private final HashSet<String> pageError = new HashSet<>();
    private final HashMap<String, Integer> os = new HashMap<>();
    private final HashMap<String, Integer> browser = new HashMap<>();
    private final HashMap<String, Double> osStatictic = new HashMap<>();
    private final HashMap<String, Double> browserStat = new HashMap<>();
    ArrayList<LogEntry> logEntriesArrays = new ArrayList<>();
    long bot;

    public Statistics() {
    }

    public void addEntry(ArrayList<LogEntry> logEntryArr) {
        this.logEntriesArrays = logEntryArr;
        long sumAmount = 0;
        long sumBot=0;
        this.minTime = logEntryArr.get(0).getData();
        this.maxTime = logEntryArr.get(logEntryArr.size() - 1).getData();
        this.hours = Duration.between(this.minTime, this.maxTime).toHours();
        for (int i = 0; i < logEntryArr.size() - 1; i++) {
            sumAmount += logEntryArr.get(i).getAmount();
            if (logEntryArr.get(i).getResponse() == 200) {
                pageOk.add(logEntryArr.get(i).getUrlRequest());
            }
            if (logEntryArr.get(i).getResponse() == 404 || logEntryArr.get(i).getResponse() == 500) {
                pageError.add(logEntryArr.get(i).getUrlRequest());
            }
            if (os.containsKey(logEntryArr.get(i).userAgent.getOs())) {
                os.put(logEntryArr.get(i).userAgent.getOs(), os.get(logEntryArr.get(i).userAgent.getOs()) + 1);
            } else {
                os.put(logEntryArr.get(i).userAgent.getOs(), 1);
            }
            if (browser.containsKey(logEntryArr.get(i).userAgent.getBrowser())) {
                browser.put(logEntryArr.get(i).userAgent.getBrowser(), browser.get(logEntryArr.get(i).userAgent.getBrowser()) + 1);
            } else {
                browser.put(logEntryArr.get(i).userAgent.getBrowser(), 1);
            }
            if (logEntryArr.get(i).userAgent.getBot()){
                sumBot=sumBot+1;
            }
        }
        this.totalTraffic = sumAmount;
        this.totalTrafficHour = totalTraffic / hours;
        this.bot=sumBot;
    }


    public void getTrafficRate(ArrayList<LogEntry> logEntryArr, LocalDateTime minTime, LocalDateTime maxTime) {
        ArrayList<LogEntry> logEntryArr2 = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < logEntryArr.size() - 1; i++) {
            if ((logEntryArr.get(i).getData().isEqual(minTime) || logEntryArr.get(i).getData().isAfter(minTime))
                    && ((logEntryArr.get(i).getData().isEqual(maxTime) || logEntryArr.get(i).getData().isBefore(maxTime)))) {
                logEntryArr2.add(logEntryArr.get(i));
            }
        }
        for (int i = 0; i < logEntryArr2.size() - 1; i++) {
            System.out.println(logEntryArr2.get(i));
            sum += logEntryArr2.get(i).getAmount();
        }
        this.totalTrafficPeriod = sum;
    }

    public long numberOfVisitsPerHour() {
        int s = 0;
        for (Map.Entry<String, Integer> entry : browser.entrySet()) {
            if (!browser.containsKey("null")) {
                s += entry.getValue();
            }
        }
        return s / hours;
    }

    public long numbersOfErrorsRequest() {
        return pageError.size() / hours;
    }

    public double averageAttendanceUser() {
        List<LogEntry> list = this.logEntriesArrays
                .stream().filter(logEntry1 -> !Objects.isNull(logEntry1.userAgent.getBrowser()))
                .filter(logEntry1 -> Objects.equals(logEntry1.userAgent.getBot(),false)).toList();
        HashMap<String, String> mapList = (HashMap<String, String>) list.stream()
                .collect(Collectors.toMap(logEntry1 -> logEntry1.getIp(), logEntry1 -> logEntry1.userAgent.getBrowser(),
                        (oldValue, newValue) -> newValue));

        return (double) mapList.size() / list.size();
    }

    public Map.Entry<LocalDateTime, Long> peakAttendanceSecond() {
        List<LogEntry> list = this.logEntriesArrays
                .stream().filter(logEntry1 -> !Objects.isNull(logEntry1.userAgent.getBrowser()))
                .filter(logEntry1 -> Objects.equals(logEntry1.userAgent.getBot(), false)).toList();
        Map<LocalDateTime, Long> result =
                list.stream().collect(Collectors.groupingBy(logEntry1 -> logEntry1.getData(), Collectors.counting()));
        return Collections.max(result.entrySet(), Map.Entry.comparingByValue());
    }

    public HashSet<String> listOfSites() {
        HashSet<String> listOfSitesReferer = new HashSet<>();
        for (int i = 0; i < this.logEntriesArrays.size(); i++) {
            if (!this.logEntriesArrays.get(i).getReferer().isEmpty() && !this.logEntriesArrays.get(i).getReferer().contains("-")) {
                String s = logEntriesArrays.get(i).getReferer().
                        replaceAll("https://", "").
                        replaceAll("http://", "").
                        replaceAll("www.", "");
                String[] str = s.split("[/]");
                listOfSitesReferer.add(str[0]);
            }
        }
        return listOfSitesReferer;
    }

    public Map.Entry<String, Long> maximumAttendanceUser() {
        List<LogEntry> listUser = this.logEntriesArrays
                .stream().filter(logEntry1 -> !Objects.isNull(logEntry1.userAgent.getBrowser()))
                .filter(logEntry1 -> !Objects.equals(logEntry1.userAgent.getBot(), true)).toList();
        Map<String, Long> result =
                listUser.stream().collect(Collectors.groupingBy(logEntry1 -> logEntry1.getIp(), Collectors.counting()));
        return Collections.max(result.entrySet(), Map.Entry.comparingByValue());
    }

    public HashSet<String> getPageOk() {
        return pageOk;
    }

    public HashMap<String, Integer> getOs() {
        return os;
    }

    public HashMap<String, Double> getOsStatictic() {
        int sumOs = os.values().stream().mapToInt(Integer::intValue).sum();
        for (Map.Entry<String, Integer> entry : os.entrySet()) {
            osStatictic.put(entry.getKey(), (double) entry.getValue() / sumOs);
        }
        return osStatictic;
    }

    public HashSet<String> getPageError() {
        return pageError;
    }

    public HashMap<String, Integer> getBrowser() {
        return browser;
    }

    public HashMap<String, Double> getBrowserStat() {
        double sumBr = browser.values().stream().mapToInt(Integer::intValue).sum();
        for (Map.Entry<String, Integer> entry : browser.entrySet()) {
            browserStat.put(entry.getKey(), entry.getValue() / sumBr);
        }
        return browserStat;
    }

    public long getTotalTraffic() {
        return totalTraffic;
    }

    public long getTotalTrafficPeriod() {
        return totalTrafficPeriod;
    }

    public long getTotalTrafficHour() {
        return totalTrafficHour;
    }

    public LocalDateTime getMinTime() {
        return minTime;
    }

    public LocalDateTime getMaxTime() {
        return maxTime;
    }

    public long getBot() {
        return bot;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "logEntry=" + logEntry +
                ", minTime=" + getMinTime() +
                ", maxTime=" + getMaxTime() +
                ", totalTraffic= " + getTotalTraffic() +
                ", TotalTrafficHour= "+ getTotalTrafficHour()+
                ", st.getOs= " +getOs()+
                ", getOsStatictic= "+ getOsStatictic()+
                ", getBrowser= "+ getBrowser()+
                ", getBrowserStat= "+getBrowserStat()+
                ", numberOfVisitsPerHour= "+ numberOfVisitsPerHour()+
                ", numbersOfErrorsRequest= "+ numbersOfErrorsRequest()+
                '}';
    }
}
