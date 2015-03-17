package com.example.polkadottimer.polkadottimer;

public class TimeUtil {

    public static long HOUR = 60 * 60 * 1000;
    public static long MINUTE = 60 * 1000;
    public static long SECOND = 1000;

    public static int getHours(long time) {
        return (int) (time / HOUR);
    }

    public static String getHours(long time, boolean leaderZero) {
        int h = getHours(time);
        return (leaderZero ? (h < 10 ? "0" : "") : "") + Long.toString(h);
    }

    public static int getMinutes(long time) {
        int hours = (int) (time / HOUR);
        return (int) ((time - hours * HOUR) / MINUTE);
    }

    public static String getMinutes(long time, boolean leaderZero) {
        int m = getMinutes(time);
        return (leaderZero ? (m < 10 ? "0" : "") : "") + Long.toString(m);
    }

    public static int getSeconds(long time) {
        int minutes = (int) (time / MINUTE);
        return (int) ((time - minutes * MINUTE) / SECOND);
    }

    public static String getSeconds(long time, boolean leaderZero) {
        int s = getSeconds(time);
        return (leaderZero ? (s < 10 ? "0" : "") : "") + Long.toString(s);
    }

    public static int getMilliseconds(long time) {
        int seconds = (int) (time / SECOND);
        return (int) (time - seconds * SECOND);
    }

    public static int getMillisDec(long time) {
        int milliseconds = getMilliseconds(time);
        return (int) (milliseconds / 10);
    }

    public static String getMillisDec(long time, boolean leaderZero) {
        int md = getMillisDec(time);
        return (leaderZero ? (md < 10 ? "0" : "") : "") + Long.toString(md);
    }

    public static String convertTimeDigital(long time) {
        StringBuilder result = new StringBuilder();
        if (getHours(time) > 0) {
            result.append(getHours(time, true)).append(" : ");
        }
        result.append(getMinutes(time, true)).append(" : ");
        result.append(getSeconds(time, true)).append(" : ");
        result.append(getMillisDec(time, true));

        return result.toString();
    }

    public static String convertTime(long time, boolean full) {
        int h = getHours(time);
        int m = getMinutes(time);
        int s = getSeconds(time);
        int ms = getMillisDec(time);

        StringBuilder result = new StringBuilder();
        if (h > 0) {
            result.append(h).append(full ? " hours" : "h");
        }
        if (m > 0) {
            if (h > 0) {
                result.append(" ");
            }
            result.append(m).append(full ? " min" : "m");
        }
        if (s > 0) {
            if (m > 0) {
                result.append(" ");
            }
            result.append(s);
            if (ms > 0) {
                result.append(".").append(getMillisDec(time, true));
            }
            result.append(full ? " sec" : "s");
        }

        return result.toString();
    }

}
