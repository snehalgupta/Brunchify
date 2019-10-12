package Models;

import java.util.*;

public class User {

    public String name;
    public String intro;
    public ArrayList<String> objectives;
    public ArrayList<String> interests;
    public ArrayList<Meetup> past_meetups;
    public ArrayList<Meetup> upcoming_meetups;
    public String primary_objective;
    public ArrayList<String> neighbourhoods;
    public ArrayList<Availability_Slot> slots;

    public User(String username) {
        name = username;
    }
    public void add_neighbourhood(String place) {
        neighbourhoods.add(place);
    }

    public void add_slot(Availability_Slot slot) {
        slots.add(slot);
    }

    public void add_bio(String text) {
        intro = text;
    }

    public void add_objective(String obj) {
        objectives.add(obj);
    }

    public void change_objectives(ArrayList<String> obj) {
        objectives = obj;
    }

    public void change_interests(ArrayList<String> obj) {
        interests = obj;
    }

    public void add_upcoming_meetup(Meetup obj) {
        upcoming_meetups.add(obj);
    }

    public void add_past_meetup(Meetup obj) {
        past_meetups.add(obj);
    }

    public void set_primary_objective(String obj) {
        primary_objective = obj;
    }

}