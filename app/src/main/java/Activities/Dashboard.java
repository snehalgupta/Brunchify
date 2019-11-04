package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import teamcool.mandeep.brunchify.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    Button invitebutton;
    Button Signupweekbutton;
    Button pastmeetings;
    Button editprofile;
    Button viewupcomingmeeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        invitebutton=(Button)findViewById(R.id.edithree);
        Signupweekbutton=(Button)findViewById(R.id.edittwo);
        editprofile=(Button)findViewById(R.id.edittitle);
        viewupcomingmeeting=(Button)findViewById(R.id.editone);
        pastmeetings=(Button)findViewById(R.id.editpastmeetups);
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
        viewupcomingmeeting.setOnClickListener(new View.OnClickListener() {
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


        /*getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Brunchify");*/
    }
}
