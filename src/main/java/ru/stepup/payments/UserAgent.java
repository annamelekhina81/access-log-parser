package ru.stepup.payments;


public class UserAgent {
    private String os, browser;
    boolean bot;

    UserAgent(String str) {
        setBrowser(str);
        setOs(str);
        isBot(str);
      }

    public void setBrowser(String str) {
        String[] browser = str.split(" ");
        for (int i = 0; i <= browser.length - 1; i++) {
            if (browser[i].contains("Chrome")) {
                this.browser = "Chrome";
            } else if (browser[i].contains("Opera")) {
                this.browser = "Opera";
            } else if (browser[i].contains("Firefox")) {
                this.browser = "Firefox";
            } else if (browser[i].contains("Edge")) {
                this.browser = "Edge";
            }
        }
    }

    public boolean isBot(String str) {
        String[] bot = str.split(" ");
        for (int i = 0; i <= bot.length - 1; i++) {
            if (bot[i].contains("Bot")) {
                return this.bot = true;
            }
        }
        return this.bot=false;
    }

    public void setOs(String str) {
        String[] os = str.split(" ");
        for (int i = 0; i <= os.length - 1; i++) {
            if (os[i].contains("Windows")) {
                this.os = "Windows";
            } else if (os[i].contains("Macintosh")) {
                this.os = "Macintosh";
            } else if (os[i].contains("Linux")) {
                this.os = "Linux";
            } else if (os[i].contains("iOS")) {
                this.os = "iOS";
            }
        }
    }


    public String getOs() {
        return os;
    }

    public String getBrowser() {
        return browser;
    }

    public boolean getBot() {
        return bot;
    }

    @Override
    public String toString() {
        return "UserAgent{" +
                " os='" + getOs() + '\'' +
                ", browser='" + getBrowser() + '\'' +
                ", bot='" + getBot() + '\'' +
                '}';
    }
}

