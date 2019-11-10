package Models;

import java.util.ArrayList;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        System.out.println("Checking equals of Slot");
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Availability_Slot that = (Availability_Slot) o;
        return Objects.equals(day, that.day) &&
                Objects.equals(date, that.date) &&
                Objects.equals(timing, that.timing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, date, timing);
    }
}
