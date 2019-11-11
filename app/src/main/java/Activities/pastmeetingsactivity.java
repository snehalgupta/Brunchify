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
import teamcool.mandeep.brunchify.R;

public class pastmeetingsactivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private List<Movie> movieList = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pastmeetingactivity);

        ((Button) findViewById(R.id.back_to_profile_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(pastmeetingsactivity.this, Dashboard.class));
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclermeetings);
        mAdapter = new MoviesAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        Movie movie = new Movie("ABC", "CEO XYZ", "2019");
        movieList.add(movie);
        movie = new Movie("ABC", "CEO XYZ", "2019");
        movieList.add(movie);
        movie = new Movie("ABC", "CEO XYZ", "2019");
        movieList.add(movie);
        movie = new Movie("ABC", "CEO XYZ", "2019");
        movieList.add(movie);
        movie = new Movie("ABC", "CEO XYZ", "2019");
        movieList.add(movie);
        movie = new Movie("ABC", "CEO XYZ", "2019");
        movieList.add(movie);
        movie = new Movie("ABC", "CEO XYZ", "2019");
        movieList.add(movie);
        recyclerView.setVisibility(View.GONE);
        mAdapter.notifyDataSetChanged();
    }
}
