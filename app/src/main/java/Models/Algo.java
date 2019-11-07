package Models;

import java.util.ArrayList;
import java.util.HashMap;

public class Algo {

    ArrayList<User> UserDB;
    HashMap<String, ArrayList<String>> hm;

    public Algo(){
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
            for(String o_:hm.get(o)){
                if(u2.getObjectives().contains(o)){
                    score += 1;
                }
            }
        }
        return score;
    }

    public int getInterestsScore(User u1, User u2){
        int score = 0;
        return score;
    }

    public ArrayList<Meetup> matchalgo(User user){
        HashMap<String, Integer> hm = new HashMap<String, Integer>();
        ArrayList<Meetup> meetups = new ArrayList<Meetup>();
        String match1 = user.getName();
        for(int i=0; i<UserDB.size();i++){
            String date = null;
            String time = null;
            String match2 = UserDB.get(i).getName();
            int score = 0;
            if(user.location.equals(UserDB.get(i).location)){
                for(Availability_Slot slot: UserDB.get(i).slots){
                    if(user.slots.contains(slot)){
                        date = slot.getDate();
                        time = slot.getTiming();
                    }
                }
                if(date == null && time == null){
                    continue;
                }
                score += getObjectivesScore(user, UserDB.get(i));
                score += getInterestsScore(user, UserDB.get(i));
            }
            hm.put(user.name,score);
        }
        return meetups;
    }

    public User discoveralgo(User user){
        User suggestion = user;
        return suggestion;
    }
}
