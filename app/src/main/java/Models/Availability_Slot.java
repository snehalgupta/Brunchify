package Models;

import java.util.ArrayList;

public class Availability_Slot {
    String day;
    String date;
    ArrayList<String> timings;

    public Availability_Slot(String s1, String s2, ArrayList<String> arr){
        day = s1;
        date = s2;
        timings = arr;
    }
}
