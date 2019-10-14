package Models;

import java.util.ArrayList;

public class Availability_Slot {
    public String day;
    public String date;
    public String timing;

    public Availability_Slot(String s1, String s2, String time){
        day = s1;
        date = s2;
        this.timing = time;
    }
}
