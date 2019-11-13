package Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import Activities.SendMail;
import Adapters.BaseChoiceAdapter;
import Models.Meetup;
import Models.Ranker;
import Models.User;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import androidx.recyclerview.widget.RecyclerView;
import teamcool.mandeep.brunchify.R;

import static com.google.android.gms.tasks.Tasks.await;

public class WeeksMeetingScheduleFragment extends BaseOnboardFragment{
    SendMail mailer;
    ArrayList<Meetup> possibleMeetups;
    User match;
    int currentDiscover = 0;
    Map<String, User> userDb = new HashMap<String, User>();
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private RecyclerView recyclerView;
    private Spinner s1;
    private Spinner s2;
    private ArrayList<String> delhi;
    private ArrayList<String> bangalore;
    String selected_city;
    int selected_no_of_meetings;
    private BaseChoiceAdapter<String> wsAdapter;
    private Button mDoneBtn;


    public WeeksMeetingScheduleFragment() {
    }

    public static WeeksMeetingScheduleFragment newInstance(String param1) {
        WeeksMeetingScheduleFragment fragment = new WeeksMeetingScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weekly__sign__up, container, false);

        //initDoneBtn(view);
        selected_city = User.getCurrentUser().location;
        recyclerView = (RecyclerView)view.findViewById(R.id.ws_recycler_view);
        s1 = (Spinner)view.findViewById(R.id.ws_spinner1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),R.array.cities_array,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter1);
        //s2 = (Spinner)view.findViewById(R.id.ws_spinner2);
        //ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),R.array.days_array,android.R.layout.simple_spinner_item);
        //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //s2.setAdapter(adapter2);
        FlexboxLayoutManager layoutm = new FlexboxLayoutManager(getContext());
        layoutm.setFlexDirection(FlexDirection.ROW);
        layoutm.setJustifyContent(JustifyContent.CENTER);
        recyclerView.setLayoutManager(layoutm);
        delhi = new ArrayList<String>();
        delhi.add("CP");
        delhi.add("CyberHub");
        delhi.add("SocialCops");
        bangalore = new ArrayList<String>();
        bangalore.add("Kormangala");
        bangalore.add("EGL");
        bangalore.add("Belandur");

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                selected_city = item.toString();
                ArrayList<String> arr;
                if(selected_city.equals("Delhi")){
                    arr = delhi;
                }
                else{
                    arr = bangalore;
                }
                wsAdapter = new BaseChoiceAdapter<>(getContext(),arr,R.layout.oval_select_button);
                recyclerView.setAdapter(wsAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//       s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//           @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//               Object item = adapterView.getItemAtPosition(i);
//               selected_no_of_meetings = Integer.parseInt(item.toString());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
        return view;
    }

    @Override
    public String updateUser() {
        User.getCurrentUser().weeklyPlaces.clear();
        if (wsAdapter.getSelectedChoices().size() < 1){
            Toast toast = Toast.makeText(getContext(),"Select a location",Toast.LENGTH_LONG);
            toast.show();
            return "Please select a location";
        }
        User.getCurrentUser().weeklyPlaces = wsAdapter.getSelectedChoices();
        User.getCurrentUser().noOfMeetings = selected_no_of_meetings;
        User.getCurrentUser().location = selected_city;
        return null;
    }

}