package Models;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.*;

public class User {

    private String uid;
    public String name;
    public String intro;
    public ArrayList<String> objectives;
    public ArrayList<String> interests;
    public ArrayList<Meetup> pastMeetups;
    public ArrayList<Meetup> upcomingMeetups;
    public String primaryObjective;
    public ArrayList<String> neighbourhoods;
    public ArrayList<Availability_Slot> slots;
    private boolean onBoarded = false;
    public int noOfMeetings;
    public ArrayList<String> weeklyPlaces;

    private static User currentUser = null;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }

    public User() {
        this.objectives = new ArrayList<>();
        this.primaryObjective = "";
        this.interests = new ArrayList<>();
        this.pastMeetups = new ArrayList<>();
        this.upcomingMeetups = new ArrayList<>();
        this.neighbourhoods = new ArrayList<>();
        this.slots = new ArrayList<>();
        this.weeklyPlaces = new ArrayList<>();
    }

    public User(String uid, String name) {
        this.uid = uid;
        this.name = name;
        this.objectives = new ArrayList<>();
        this.primaryObjective = "";
        this.interests = new ArrayList<>();
        this.pastMeetups = new ArrayList<>();
        this.upcomingMeetups = new ArrayList<>();
        this.neighbourhoods = new ArrayList<>();
        this.weeklyPlaces = new ArrayList<>();
        this.slots = new ArrayList<>();
    }

    public void addNeighbourhoods(ArrayList<String> places) {
        neighbourhoods.addAll(places);
    }

    public void addSlot(Availability_Slot slot) {
        slots.add(slot);
    }

    public void addObjective(String obj) {
        objectives.add(obj);
    }

    public void addInterest(String interest){ this.interests.add(interest); }

    public void addInterests(ArrayList<String> interests){
        for (String i:interests) {
            User.getCurrentUser().addInterest(i);
        }
    }

    public void addUpcomingMeetup(Meetup obj) {
        upcomingMeetups.add(obj);
    }

    public void addPastMeetup(Meetup obj) {
        pastMeetups.add(obj);
    }

    public boolean isOnBoarded() {
        return onBoarded;
    }

    public void setOnBoarded(boolean onBoarded) {
        this.onBoarded = onBoarded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public ArrayList<String> getObjectives() {
        return objectives;
    }

    public void setObjectives(ArrayList<String> objectives) {
        this.objectives = objectives;
    }

    public ArrayList<String> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }

    public ArrayList<Meetup> getPastMeetups() {
        return pastMeetups;
    }

    public void setPastMeetups(ArrayList<Meetup> pastMeetups) {
        this.pastMeetups = pastMeetups;
    }

    public ArrayList<Meetup> getUpcomingMeetups() {
        return upcomingMeetups;
    }

    public void setUpcomingMeetups(ArrayList<Meetup> upcomingMeetups) {
        this.upcomingMeetups = upcomingMeetups;
    }

    public String getPrimaryObjective() {
        return primaryObjective;
    }

    public void setPrimaryObjective(String primaryObjective) {
        this.primaryObjective = primaryObjective;
    }

    public ArrayList<String> getNeighbourhoods() {
        return neighbourhoods;
    }

    public void setNeighbourhoods(ArrayList<String> neighbourhoods) {
        this.neighbourhoods = neighbourhoods;
    }

    public ArrayList<Availability_Slot> getSlots() {
        return slots;
    }

    public void setSlots(ArrayList<Availability_Slot> slots) {
        this.slots = slots;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getNoOfMeetings() {
        return noOfMeetings;
    }

    public void setNoOfMeetings(int noOfMeetings) {
        this.noOfMeetings = noOfMeetings;
    }

    public ArrayList<String> getWeeklyPlaces() {
        return weeklyPlaces;
    }

    public void setWeeklyPlaces(ArrayList<String> weeklyPlaces) {
        this.weeklyPlaces = weeklyPlaces;
    }

    public static void writeToFirestore(FirebaseFirestore firestore, OnCompleteListener<Void> listener){

        //TODO: Move function to a data class
        DocumentReference userDocRef = firestore.collection("users").document(User.getCurrentUser().uid);
        userDocRef.set(User.getCurrentUser()).addOnCompleteListener(listener);
    }
}