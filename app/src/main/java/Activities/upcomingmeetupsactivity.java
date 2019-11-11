package Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Models.Meetup;
import Models.User;
import teamcool.mandeep.brunchify.R;

public class upcomingmeetupsactivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private List<Movie> movieList = new ArrayList<>();
    private View emptyView;
    private View mainView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upcomingmeetupsactivity);

        emptyView = findViewById(R.id.empty_view);
        mainView = findViewById(R.id.main_view);

        ((Button) findViewById(R.id.back_to_profile_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(upcomingmeetupsactivity.this, Dashboard.class));
                finish();
            }
        });

        recyclerView=(RecyclerView) findViewById(R.id.recyclerupcomingmeetings);
        mAdapter = new MoviesAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        /*Movie movie = new Movie("ABC","CEO XYZ","2019");
        movieList.add(movie);
        movie = new Movie("ABC","CEO XYZ","2019");
        movieList.add(movie);
        movie = new Movie("ABC","CEO XYZ","2019");
        movieList.add(movie);
        movie = new Movie("ABC","CEO XYZ","2019");
        movieList.add(movie);
        movie = new Movie("ABC","CEO XYZ","2019");
        movieList.add(movie);
        movie = new Movie("ABC","CEO XYZ","2019");
        movieList.add(movie);
        movie = new Movie("ABC","CEO XYZ","2019");
        movieList.add(movie);*/
        if (User.getCurrentUser().getUpcomingMeetups().size() > 0) {
            mainView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            for (Meetup meetup : User.getCurrentUser().getUpcomingMeetups()) {
                movieList.add(new Movie(User.userDb.get(meetup.match2).getName(), meetup.date, meetup.time));
            }
            mAdapter.notifyDataSetChanged();
        }
        else{
            mainView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }

    }
}
