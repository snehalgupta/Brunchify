package Activities;

import android.os.Bundle;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upcomingmeetupsactivity);
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
        Meetup meetup = User.getCurrentUser().getUpcomingMeetups().get(0);
        movieList.add(new Movie(User.userDb.get(meetup.match2).getName(), meetup.date, meetup.time));

        mAdapter.notifyDataSetChanged();
    }
}
