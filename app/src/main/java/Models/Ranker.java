package Models;

import android.icu.lang.UScript;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Ranker {

    private static final String TAG = "Ranker";
    ArrayList<User> UserDB;
    HashMap<String, ArrayList<String>> hm;

    public Ranker(ArrayList<User> userDb){
        UserDB = userDb;
        hm = new HashMap<String, ArrayList<String>>();
        String s1 = "Brainstorming";
        String s2 = "Invest Money";
        String s3 = "Explore New Projects";
        String s4 = "Mentor Others";
        String s5 = "Organize Events";
        String s6 = "Start a company";
        String s7 = "Find Cofounder or Partner";
        String s8 = "Raise Funding";
        String s9 = "Business Development";
        String s10 = "Grow your team";
        String s11 = "Explore other companies";
        hm.put(s1, new ArrayList<String>());
        hm.get(s1).add(s4);
        hm.get(s1).add(s6);
        hm.get(s1).add(s7);
        hm.get(s1).add(s10);
        hm.put(s2, new ArrayList<String>());
        hm.get(s2).add(s8);
        hm.get(s2).add(s9);
        hm.put(s3, new ArrayList<String>());
        hm.get(s3).add(s10);
        hm.get(s3).add(s11);
        hm.put(s4, new ArrayList<String>());
        hm.get(s4).add(s1);
        hm.get(s4).add(s5);
        hm.put(s5, new ArrayList<String>());
        hm.get(s5).add(s4);
        hm.put(s6, new ArrayList<String>());
        hm.get(s6).add(s1);
        hm.get(s6).add(s7);
        hm.get(s6).add(s11);
        hm.put(s7, new ArrayList<String>());
        hm.get(s7).add(s6);
        hm.get(s7).add(s1);
        hm.get(s7).add(s11);
        hm.put(s8, new ArrayList<String>());
        hm.get(s8).add(s2);
        hm.get(s8).add(s9);
        hm.put(s9, new ArrayList<String>());
        hm.get(s9).add(s2);
        hm.get(s9).add(s8);
        hm.put(s10, new ArrayList<String>());
        hm.get(s10).add(s1);
        hm.get(s10).add(s3);
        hm.put(s11, new ArrayList<String>());
        hm.get(s11).add(s3);
        hm.get(s11).add(s7);
    }

    public int getObjectivesScore(User u1, User u2){
        int score = 0;
        for(String o:u1.getObjectives()){
            for(String o1:hm.get(o)){
                if(u2.getObjectives().contains(o1)){
                    score += 1;
                    Log.v(TAG,"Match " + o + " with " + o1);
                }
            }
        }
        Log.d(TAG, u1.getName() + " and " + u2.getName() + " Objectives score = " + score);
        return score;
    }

    public int getInterestsScore(User u1, User u2){
        int score = 0;
        for(String s:u1.getInterests()){
            if(u2.getInterests().contains(s)){
                score += 1;
                Log.v(TAG,"Match " + s);
            }
        }
        Log.d(TAG, u1.getName() + " and " + u2.getName() + " Interests score = " + score);
        return score;
    }

    public ArrayList<Meetup> matchalgo(User user){
        ArrayList<Meetup> meetups = new ArrayList<Meetup>();
        String match1 = user.getUid();
        for(int i=0; i<UserDB.size();i++){
            if (UserDB.get(i).getUid().equals(User.getCurrentUser().getUid())){
                continue;
            }
            Log.d("current user",i+"");
            String date = null;
            String time = null;
            String match2 = null;
            int score = 0;

            Log.d("livelife",user.email);
                int flag=0;
                if(user.location.equals(UserDB.get(i).location)) {
                    for (Availability_Slot slot : UserDB.get(i).slots) {
                        Log.i(TAG,"Total number of slots " + user.slots.size()+"");
                        for (int j = 0; j < user.slots.size(); j++) {
                            Log.i(TAG,"Checking slot " + slot + " with current user, " + j);
                            Log.i(TAG, user.slots.get(j).date + " " + slot.date  + " " + user.slots.get(j).timing + " " + slot.timing);
                            if (user.slots.get(j).date.equals(slot.date) && user.slots.get(j).timing.equals(slot.timing)) {
                                date = slot.getDate();
                                time = slot.getTiming();
                                if (flag == 0)
                                    flag = 1;
                                break;
                            }

                        }
                        Log.d(TAG, "checked slot for " + i);
                        if (flag == 1) {
                            // Check if .contains is working:
                            System.out.println("manual check shows slot is there, .contains - " + user.slots.contains(slot));
                            break;
                        }

                    }
                    Log.d(TAG, "checked all slots");
                    if (date == null && time == null) {
                        continue;
                    }
                    match2 = UserDB.get(i).getUid();

                    score += getObjectivesScore(user, UserDB.get(i));
                    score += getInterestsScore(user, UserDB.get(i));
                }
                else{
                    Log.i(TAG,"User " + i + " location doesn't match");
                }
                if (UserDB.get(i).getUpcomingMeetups().size() == 0){
                    score+=50;
                }


            if (match2 != null){
            Meetup newmeet = new Meetup(date,time,match1,match2);
            newmeet.score = score;
            meetups.add(newmeet);
            }
        }
        Collections.sort(meetups, new Comparator<Meetup>(){
            public int compare( Meetup o1, Meetup o2 )
            {
                Integer one=o1.getScore();
                Integer two =o2.getScore();

                return two.compareTo(one);
            }
        });
        return meetups;
        /*ArrayList<Meetup> finalans = new ArrayList<Meetup>();
        for(int m=0; m<user.noOfMeetings;m++){
            finalans.add(meetups.get(m));
        }
        return finalans;*/
    }

    public User discoveralgo(User user){
        User suggestion = user;

        return suggestion;
    }
}
