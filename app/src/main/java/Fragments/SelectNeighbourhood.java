package Fragments;

import android.os.Bundle;

import Adapters.BaseChoiceAdapter;

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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import Models.User;
import teamcool.mandeep.brunchify.R;

public class SelectNeighbourhood extends BaseOnboardFragment implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private ArrayList<String> delhi;
    private ArrayList<String> bangalore;
    String selected_city;
    private BaseChoiceAdapter<String> neighbourhoodAdapter;

    public SelectNeighbourhood() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select__neighbourhood, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_neighbourhood);
        //StaggeredGridLayoutManager layoutm = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
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
        Spinner dropdown = (Spinner)view.findViewById(R.id.neighbourhood_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.cities_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
        return view;
    }

    @Override
    public String updateUser() {
        User.getCurrentUser().neighbourhoods.clear();
        if (neighbourhoodAdapter.getSelectedChoices().size() < 1){
            return "Please select a neighbourhood";
        }
        User.getCurrentUser().addNeighbourhoods(neighbourhoodAdapter.getSelectedChoices());
        return null;
    }

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
        //Neighbourhood_RV_Adapter adapter = new Neighbourhood_RV_Adapter(getContext(),arr,mListener);
        neighbourhoodAdapter = new BaseChoiceAdapter<>(getContext(),arr,R.layout.oval_select_button);
        recyclerView.setAdapter(neighbourhoodAdapter);
        //Toast.makeText(getContext(),selected_city,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Toast.makeText(getContext(),"Select city",Toast.LENGTH_LONG).show();
    }

}
