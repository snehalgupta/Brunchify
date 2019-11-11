package Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import Fragments.All_Set;
import Fragments.OnWizardInteractionListener;
import Fragments.WeeklySignUpStart;
import Fragments.WeeklySignupPagerFragment;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import Models.Meetup;
import Models.Ranker;
import Models.User;
import teamcool.mandeep.brunchify.R;

import static com.google.android.gms.tasks.Tasks.await;

public class WeeklySignUp extends FragmentActivity implements OnWizardInteractionListener {

    private static final String TAG = WeeklySignUp.class.getSimpleName();
    //private ViewPager viewPager;
//    private BaseOnboardFragment[] fragments;
    //private MyViewPagerAdapter myViewPagerAdapter;
    private WeeklySignUpStart weeklySignUpStart;
    private WeeklySignupPagerFragment viewPagerFragment;
    private All_Set allSetFragment;
    private SendMail mailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Hide action bar
        //getSupportActionBar().hide();
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_weeklysignup);
        changeStatusBarColor();

        initFragments();
    }

    private void completeWeeklySignup(){
        //Assumption is that this will complete before all the other writes in async task start
        User.writeToFirestore(FirebaseFirestore.getInstance(), null);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        allSetFragment = new All_Set("Finding Your Match ...", true);

        RankAsyncTask matchTask = new RankAsyncTask(allSetFragment);
        matchTask.execute();
        //User.writeToFirestore(FirebaseFirestore.getInstance(), allSetFragment);
        transaction.replace(R.id.fragmentContainer, allSetFragment);
        transaction.commit();
        //startActivity(new Intent(WeeklySignUp.this, Dashboard.class));
        //finish();
    }

    public void initFragments(){

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        weeklySignUpStart = new WeeklySignUpStart();
        viewPagerFragment = new WeeklySignupPagerFragment();
        //transaction.add(R.id.fragmentContainer, weeklySignUpStart);
        transaction.add(R.id.fragmentContainer, viewPagerFragment);
        transaction.commit();

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void submit() {
        String msg = viewPagerFragment.updateUser();
        if (msg!=null){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }
        else {
            completeWeeklySignup();
        }
    }

    @Override
    public void begin() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.fragmentContainer, viewPagerFragment);
        transaction.commit();

        /*viewPager = (ViewPager) findViewById(R.id.view_pager_);
        myViewPagerAdapter = new WeeklySignUp.MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setOffscreenPageLimit(fragments.length+1);*/
    }

    @Override
    public void cancel() {
        completeWeeklySignup();
    }



    class RankAsyncTask extends AsyncTask<Void, Void, ArrayList<Meetup>> {
        final String TAG = "RankAsync";
        private final OnCompleteListener<Void> completeListener;

        public RankAsyncTask(All_Set allSetFragment) {
            this.completeListener = allSetFragment;
        }

        @Override
        protected ArrayList<Meetup> doInBackground(Void... v) {
            if (User.userDb.size() == 0) {
                Task<QuerySnapshot> task = FirebaseFirestore.getInstance().collection("users")
                        .get();
                try {
                    await(task);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                QuerySnapshot result = task.getResult();
                Log.d(TAG, "get result");
                ArrayList<User> usersToRank = new ArrayList<User>();
                for (DocumentSnapshot snapshot : result.getDocuments()) {
                    usersToRank.add(snapshot.toObject(User.class));
                }
                for (User u : usersToRank) {
                    User.userDb.put(u.getUid(), u);
                }
            }
            //User.userDb = userDb;
            //Ranker ranker = new Ranker(usersToRank);
            Ranker ranker = new Ranker(new ArrayList<User>(User.userDb.values()));
            ArrayList<Meetup> ranks = ranker.matchalgo(User.getCurrentUser());
            Log.d(TAG,"Raker returned " + ranks.size()+"matches");
            if (User.getCurrentUser().getUpcomingMeetups().size() < 1) {

                final Meetup topMatch = ranks.remove(0);
                User.getCurrentUser().addUpcomingMeetup(topMatch);
                final User u1 = User.getCurrentUser();
                try {
                    User.writeToFirestoreSync(FirebaseFirestore.getInstance());
                    Log.i(TAG, "Added meetup to current user " + User.getCurrentUser().getName());
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                String matchUser;
                /**
                 if (true || User.getCurrentUser().getEmail().contains("snehal")){
                 matchUser = "9R2Yy1YDFdakO6sbYuCIhTzKRaE3";
                 }
                 else{
                 matchUser = topMatch.match2;
                 }
                 **/
                matchUser = topMatch.match2;
                final Meetup inverseMatch = new Meetup(topMatch.date, topMatch.time, topMatch.match2, topMatch.match1);
                ArrayList<Meetup> otherMeetups = new ArrayList<>();
                otherMeetups.add(inverseMatch);
                Map<String, Object> updateMap = new HashMap<>();
                updateMap.put("upcomingMeetups", otherMeetups);
                try {
                    Log.d(TAG, "Updating other user");
                    DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(matchUser);
                    Task<DocumentSnapshot> updateTask = docRef.get();
                    await(updateTask);
                    Log.d(TAG, "Got other user from Firestore");
                    User match = updateTask.getResult().toObject(User.class);
                    final User u2 = match;
                    match.addUpcomingMeetup(inverseMatch);
                    Log.d(TAG, "Sending other user update");
                    Task<Void> updateTask2 = docRef.set(match);
                    await(updateTask2);
                    if (updateTask2.isSuccessful()) {
                        Log.d(TAG, "Successfully updated the other user");
                    }
                    else{
                        Log.d(TAG, "Error updating other user " + updateTask2.getException());
                    }
                    //await(FirebaseFirestore.getInstance().collection("users").document(matchUser)
                    //.update("upcomingMeetups", otherMeetups));
                    //        .update(updateMap));
                    //.update("upcomingMeetups",  FieldValue.arrayUnion(inverseMatch)));
                    //Log.i(TAG, "Added meetup to matched user " + matchUser);
                    ranks.remove(matchUser);

                    //TODO: notify both users.
                    SendMail sm = new SendMail(WeeklySignUp.this,
                            new ArrayList<String>(){
                                {
                                    add(u1.getEmail());
                                    add(u2.getEmail());
                                }
                            },
                            "Your match for this week!",
                            "Hi " + u1.getName() + " and " + u2.getName() + ",\n" +
                                    "You both have been matched with each other.\n" +
                                    "Use this email as a medium to break the ice and coordinate the exact meeting time and location.\n" +
                                    "Please try to commit and meet your connection.\n" +
                                    "Mutual respect and reliability is what makes our community special.");

//                    sm.execute();
                    mailer = sm;
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                User otherUser = new User(User.getCurrentUser().getUpcomingMeetups().get(0).match2, "", "");
                ranks.remove(otherUser);
            }
            User.getCurrentUser().possibleMeetups = new ArrayList<String>();
            for (Meetup m: ranks){
                User.getCurrentUser().possibleMeetups.add(m.match2);
            }
            return ranks;
        }

        @Override
        protected void onPostExecute(ArrayList<Meetup> meetups) {
            User.getCurrentUser();
            mailer.execute();
            completeListener.onComplete(null);
            //TODO: Launch Dashboard when done
            super.onPostExecute(meetups);
        }
    }
}
