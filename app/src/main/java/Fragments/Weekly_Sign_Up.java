package Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import Adapters.BaseChoiceAdapter;
import Models.User;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import teamcool.mandeep.brunchify.R;

public class Weekly_Sign_Up extends BaseOnboardFragment{

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

    public Weekly_Sign_Up() {
    }

    public static Weekly_Sign_Up newInstance(String param1) {
        Weekly_Sign_Up fragment = new Weekly_Sign_Up();
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
        recyclerView = (RecyclerView)view.findViewById(R.id.ws_recycler_view);
        s1 = (Spinner)view.findViewById(R.id.ws_spinner1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),R.array.cities_array,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter1);
        s2 = (Spinner)view.findViewById(R.id.ws_spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),R.array.days_array,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(adapter2);
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

        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                selected_no_of_meetings = Integer.parseInt(item.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    @Override
    public String updateUser() {
        User.getCurrentUser().weekly_places.clear();
        if (wsAdapter.getSelectedChoices().size() < 1){
            return "Please select a location";
        }
        User.getCurrentUser().weekly_places = wsAdapter.getSelectedChoices();
        User.getCurrentUser().no_of_meetings = selected_no_of_meetings;
        return null;
    }

}