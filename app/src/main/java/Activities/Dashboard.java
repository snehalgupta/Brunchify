package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import Models.Meetup;
import Models.Ranker;
import Models.User;
import teamcool.mandeep.brunchify.R;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static com.google.android.gms.tasks.Tasks.await;

public class Dashboard extends AppCompatActivity {
    Button bmatchfinder;
    Button buttyes;
    Button buttno;
    Button buttmaybe;
    private static final String TAG = "Dashboard";
    Button invitebutton;
    Button Signupweekbutton;
    Button pastmeetings;
    ImageView editprofile;
    Button viewupcomingmeeting;
    User discoverUser;
    private Button logoutBtn;
    Map<String, User> userDb = new HashMap<String, User>();
    ArrayList<Meetup> possibleMeetups;
    User match;
    int currentDiscover = 0;
    private TextView discoverInfoTv;
    private TextView discoverNameTv;
    private View discoverResponse;
    SendMail mailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        buttmaybe=(Button)findViewById(R.id.editmaybe);
        buttno=(Button)findViewById(R.id.editno);
        buttyes=(Button)findViewById(R.id.edityes);
        bmatchfinder=(Button)findViewById(R.id.matchfinderbutton);
        invitebutton=(Button)findViewById(R.id.edithree);
        Signupweekbutton=(Button)findViewById(R.id.edittwo);
        editprofile=(ImageView)findViewById(R.id.edittitle);
        viewupcomingmeeting=(Button)findViewById(R.id.editone);
        pastmeetings=(Button)findViewById(R.id.editpastmeetups);
        logoutBtn = (Button)findViewById(R.id.logout_btn);
        discoverNameTv = (TextView)findViewById(R.id.discover_name_tv);
        discoverInfoTv = (TextView)findViewById(R.id.discover_info_tv);
        //discoverResponse = findViewById(R.id.discover_response);


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

//        ((TextView)findViewById(R.id.name_tv)).setText(User.getCurrentUser().getName());
//        final String desig = User.getCurrentUser().designation + " at " + User.getCurrentUser().organisation;
//        ((TextView)findViewById(R.id.user_info_tv)).setText(desig);


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


//        discoverResponse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        buttyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttyes.setBackgroundResource(R.drawable.capsule);
                buttyes.setTextColor(Color.WHITE);
                discoverResponse();
            }
        });
        buttno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttno.setBackgroundResource(R.drawable.capsule);
                buttno.setTextColor(Color.WHITE);
                discoverResponse();
            }
        });
        buttmaybe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttmaybe.setBackgroundResource(R.drawable.capsule);
                buttmaybe.setTextColor(Color.WHITE);
                discoverResponse();
            }
        });

            bmatchfinder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Dashboard.this, WeeklySignUp.class));
                }
            });



        /*getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Brunchify");*/
    }

    private void discoverResponse(){
        Thread timer = new Thread() {
            public void run(){
                try {
                    sleep(1000);
                    showDiscover();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        timer.start();
        //showDiscover();
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
        buttmaybe.setTextColor(Color.BLACK);
        buttmaybe.setBackgroundResource(R.drawable.capsulewhite);
        buttno.setTextColor(Color.BLACK);
        buttno.setBackgroundResource(R.drawable.capsulewhite);
        buttyes.setTextColor(Color.BLACK);
        buttyes.setBackgroundResource(R.drawable.capsulewhite);
//        User mUsr = userDb.get(possibleMeetups.get(currentDiscover++ % possibleMeetups.size()).match2);
//        discoverNameTv.setText(mUsr.getName());
//        discoverInfoTv.setText(mUsr.getDesignation() + " at " + mUsr.getOrganisation());
    }

    /*@Override
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
    }*/

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Dashboard.this, WelcomeActivity.class));
    }





}
