package Activities;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import teamcool.mandeep.brunchify.R;

public class pastmeetingsactivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private List<Movie> movieList = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pastmeetingactivity);
                recyclerView=(RecyclerView) findViewById(R.id.recyclermeetings);
        mAdapter = new MoviesAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        Movie movie = new Movie("ABC","CEO XYZ","2019");
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
        movieList.add(movie);
        recyclerView.setVisibility(View.GONE);
        mAdapter.notifyDataSetChanged();
    }
}
