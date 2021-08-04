package com.sp.igeeti.utils;

public class TimeFormatter {
    private TimeFormatter(){

    }
    public static String formatMillis(int millis){
        int seconds = millis / 1000;
        int min = seconds / 60;
        int hour = min / 60;
        seconds -= (min * 60);
        min -= (hour * 60);
        return hour == 0 ? min + ":" + (seconds < 10?  "0" + seconds : seconds)
                : (min < 10? "0"+min : min) + ":" + (seconds < 10?  "0" + seconds : seconds);
    }
}
