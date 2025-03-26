package ru.stepup.payments;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Statistics {
    LogEntry logEntry;
    private long totalTraffic, totalTrafficPeriod, totalTrafficHour;
    private LocalDateTime minTime, maxTime;

    public Statistics() {
    }

    public void addEntry(ArrayList<LogEntry> logEntryArr) {
        long sumAmount = 0;
        this.minTime = logEntryArr.get(0).getData();
        this.maxTime = logEntryArr.get(logEntryArr.size() - 1).getData();
        long h = Duration.between(this.minTime, this.maxTime).toHours();
        for (int i = 0; i < logEntryArr.size() - 1; i++) {
            sumAmount += logEntryArr.get(i).getAmount();
        }
        this.totalTraffic = sumAmount;
        this.totalTrafficHour = totalTraffic / h;
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

    @Override
    public String toString() {
        return "Statistics{" +
                "logEntry=" + logEntry +
                ", totalTraffic=" + getTotalTraffic() +
                ", minTime=" + getMinTime() +
                ", maxTime=" + getMaxTime() +
                '}';
    }
}

