package ru.stepup.payments;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class LogEntry {
    String str;
    private final String ip, method, urlRequest, referer;
    private final LocalDateTime data;
    private final int response, amount;
    UserAgent userAgent;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy':'HH:mm:ss", Locale.ENGLISH);

    public LogEntry(String str) {
        this.str = str;
        String[] parts = str.split(" ");
        String[] parts2 = str.split("\"");
        this.ip = parts[0];
        this.data = LocalDateTime.parse(parts[1], formatter);
        this.method = parts[2].replaceAll("\"", "");
        this.urlRequest = parts[3];
        this.response = Integer.parseInt(parts[5]);
        this.amount = Integer.parseInt(parts[6]);
        this.referer = parts[7].replaceAll("\"", "");
        UserAgent ua = new UserAgent(parts2[5]);
        this.userAgent = ua;
    }


    public String getIp() {
        return ip;
    }

    public String getMethod() {
        return method;
    }

    public String getUrlRequest() {
        return urlRequest;
    }

    public String getReferer() {
        return referer;
    }

    public LocalDateTime getData() {
        return data;
    }

    public int getResponse() {
        return response;
    }

    public int getAmount() {
        return amount;
    }


    @Override
    public String toString() {
        return "LogEntry{" +
                "log=" + getIp() + " "
                + getData() + " " + getMethod() + " "
                + getUrlRequest() + " " + getResponse() + " "
                + getAmount() + " " + getReferer() +
                " " + userAgent +
                '}';
    }
}
