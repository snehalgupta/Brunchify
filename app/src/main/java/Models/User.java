package Models;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.*;
import java.util.concurrent.ExecutionException;

import static com.google.android.gms.tasks.Tasks.await;

public class User {

    private String uid;
    public String name;
    public String email;
    public String intro;
    public String designation;
    public String organisation;

    public String primaryObjective;
    public ArrayList<String> objectives;
    public ArrayList<String> interests;

    public ArrayList<Availability_Slot> slots;
    public ArrayList<String> weeklyPlaces;
    public String location;
    public ArrayList<String> neighbourhoods;
    public int lastSignupWeek;
    public int noOfMeetings = 1;

    public ArrayList<Meetup> pastMeetups;
    public ArrayList<Meetup> upcomingMeetups;

    private boolean onBoarded = false;

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
        this.slots = new ArrayList<>();
        this.weeklyPlaces = new ArrayList<>();
    }

    public User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.objectives = new ArrayList<>();
        this.primaryObjective = "";
        this.interests = new ArrayList<>();
        this.pastMeetups = new ArrayList<>();
        this.upcomingMeetups = new ArrayList<>();
        this.weeklyPlaces = new ArrayList<>();
        this.slots = new ArrayList<>();
    }


    public void addNeighbourhoods(ArrayList<String> places) {
        neighbourhoods.addAll(places);
     String primaryObjective;
     ArrayList<String> neighbourhoods; }


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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
        this.noOfMeetings = 1;//noOfMeetings;
    }

    public ArrayList<String> getWeeklyPlaces() {
        return weeklyPlaces;
    }

    public void setWeeklyPlaces(ArrayList<String> weeklyPlaces) {
        this.weeklyPlaces = weeklyPlaces;
    }

    public int getLastSignupWeek() {
        return lastSignupWeek;
    }

    public void setLastSignupWeek(int lastSignupWeek) {
        this.lastSignupWeek = lastSignupWeek;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public ArrayList<String> getNeighbourhoods() {
        return neighbourhoods;
    }

    public void setNeighbourhoods(ArrayList<String> neighbourhoods) {
        this.neighbourhoods = neighbourhoods;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return uid.equals(user.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }

    public static void writeToFirestore(FirebaseFirestore firestore, OnCompleteListener<Void> listener){

        //TODO: Move function to a data class
        DocumentReference userDocRef = firestore.collection("users").document(User.getCurrentUser().uid);
        userDocRef.set(User.getCurrentUser()).addOnCompleteListener(listener);
    }

    public static void writeToFirestoreSync(FirebaseFirestore firestore) throws ExecutionException, InterruptedException {
        DocumentReference userDocRef = firestore.collection("users").document(User.getCurrentUser().uid);
        await(userDocRef.set(User.getCurrentUser()));
    }
}