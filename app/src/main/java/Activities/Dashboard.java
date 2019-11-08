package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import Models.Meetup;
import Models.Ranker;
import Models.User;
import teamcool.mandeep.brunchify.R;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static com.google.android.gms.tasks.Tasks.await;

public class Dashboard extends AppCompatActivity {

    private static final String TAG = "Dashboard";
    Button invitebutton;
    Button Signupweekbutton;
    Button pastmeetings;
    ImageView editprofile;
    Button viewupcomingmeeting;
    User discoverUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        invitebutton=(Button)findViewById(R.id.edithree);
        Signupweekbutton=(Button)findViewById(R.id.edittwo);
        editprofile=(ImageView)findViewById(R.id.edittitle);
        viewupcomingmeeting=(Button)findViewById(R.id.editone);
        pastmeetings=(Button)findViewById(R.id.editpastmeetups);

        ((TextView)findViewById(R.id.name_tv)).setText(User.getCurrentUser().getName());
        invitebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inviteintent=new Intent(Dashboard.this,InviteActivity.class);
                startActivity(inviteintent);
            }
        });
        Signupweekbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupintent=new Intent(Dashboard.this,WeeklySignUp.class);
                startActivity(signupintent);
            }
        });
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, SelectOptions.class);
                startActivity(intent);
            }
        });
        viewupcomingmeeting.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, upcomingmeetupsactivity.class);
                startActivity(intent);
            }
        });
        pastmeetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, pastmeetingsactivity.class);
                startActivity(intent);
            }
        });


        RankAsyncTask ranker = new RankAsyncTask();
        ranker.execute();


        /*getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Brunchify");*/
    }

    private void getDiscoverUser(){
        FirebaseFirestore.getInstance().collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "Fetched all users");
                            List<DocumentSnapshot> arr = task.getResult().getDocuments();
                            Random random = new Random();
                            discoverUser = arr.get(random.nextInt(arr.size())).toObject(User.class);
                            if (discoverUser.getUid().equals(User.getCurrentUser().getUid())){
                                discoverUser = arr.get(random.nextInt(arr.size())).toObject(User.class);
                            }
                            showDiscover();
                        }
                        else{
                            Log.w(TAG, "Failure in getting all users");
                        }
                    }
                });
    }

    private void showDiscover() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Dashboard.this, WelcomeActivity.class));
    }


    class RankAsyncTask extends AsyncTask<Void, Void, ArrayList<Meetup>>{
        final String TAG = "RankAsync";

        @Override
        protected ArrayList<Meetup> doInBackground(Void... v) {

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
            ArrayList usersToRank = new ArrayList<User>();
            for (DocumentSnapshot snapshot : result.getDocuments()) {
                usersToRank.add(snapshot.toObject(User.class));
            }
            Ranker ranker = new Ranker(usersToRank);
            ArrayList<Meetup> ranks = ranker.matchalgo(User.getCurrentUser());
            if (User.getCurrentUser().getUpcomingMeetups().size() < 1) {
                Meetup topMatch = ranks.remove(0);
                User.getCurrentUser().addUpcomingMeetup(topMatch);
                try {
                    User.writeToFirestoreSync(FirebaseFirestore.getInstance());
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "Added meetup to current user " + User.getCurrentUser().getName());
                String matchUser = topMatch.match2;
                Meetup inverseMatch = new Meetup(topMatch.date, topMatch.time, topMatch.match2, topMatch.match1);
                ArrayList<Meetup> otherMeetups = new ArrayList<>();
                otherMeetups.add(inverseMatch);
                try {
                    await(FirebaseFirestore.getInstance().collection("users").document(matchUser)
                            .update("upcomingMeetups", otherMeetups));
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "Added meetup to current user " + matchUser);
                //TODO: notify both users.
            } else {
                User otherUser = new User(User.getCurrentUser().getUpcomingMeetups().get(0).match2, "");
                ranks.remove(otherUser);
            }
            return ranks;
        }

        @Override
        protected void onPostExecute(ArrayList<Meetup> meetups) {
            User.getCurrentUser();
            super.onPostExecute(meetups);
        }
    }


}
