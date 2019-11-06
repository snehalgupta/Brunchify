package Models;

import java.util.ArrayList;

public class Availability_Slot {
    public String day;
    public String date;
    public String timing;

    public Availability_Slot() {
    }

    public Availability_Slot(String s1, String s2, String time){
        day = s1;
        date = s2;
        this.timing = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }
}
