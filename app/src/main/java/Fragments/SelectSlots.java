package Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import Activities.Dashboard;
import Activities.PreferenceManager;
import Adapters.Slots_RV_Adapter;
import Models.Availability_Slot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.recyclerview.widget.RecyclerView;

import Models.User;
import teamcool.mandeep.brunchify.R;

public class SelectSlots extends BaseOnboardFragment implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private ArrayList<ArrayList<Availability_Slot>> arr ;

    //private BaseChoiceAdapter<Availability_Slot> adapter;
    private Slots_RV_Adapter adapter;
    private String primary_obj;

    public SelectSlots() {
    }

    public static ArrayList<ArrayList<Availability_Slot>> get_slots(){

        ArrayList<ArrayList<Availability_Slot>> ans = new ArrayList<>();
        ArrayList<String> timings = new ArrayList<String>();
        timings.add("10 AM");
        timings.add("12 PM");
        timings.add("1 PM");
        timings.add("2 PM");
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int present_day = calendar.get(Calendar.DAY_OF_WEEK);
        int diff = present_day - 4;
        calendar.add(Calendar.DAY_OF_MONTH,7-diff);
        Date next_slot = calendar.getTime();

        for(int i=0;i<3;i++){
            ArrayList<Availability_Slot> daySlots = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            String day = sdf.format(next_slot);
            sdf = new SimpleDateFormat("MMM");
            String month = sdf.format(next_slot);
            sdf = new SimpleDateFormat("yyyy");
            String year = sdf.format(next_slot);
            sdf = new SimpleDateFormat("dd");
            String date = sdf.format(next_slot);
            for (String t: timings) {
                daySlots.add(new Availability_Slot(day, month + " " + date + ", " + year, t));
            }
            ans.add(daySlots);
            calendar.setTime(next_slot);
            calendar.add(Calendar.DAY_OF_MONTH,1);
            next_slot = calendar.getTime();
        }

        return ans;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select__slots, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_slots);
        arr = get_slots();
        FlexboxLayoutManager layoutm = new FlexboxLayoutManager(getContext());
        layoutm.setFlexDirection(FlexDirection.ROW);
        layoutm.setJustifyContent(JustifyContent.FLEX_START);
        recyclerView.setLayoutManager(layoutm);
        adapter = new Slots_RV_Adapter(getContext(),arr);
        //adapter = new BaseChoiceAdapter<>(getContext(), arr, R.layout.slot_item);


        recyclerView.setAdapter(adapter);
        Spinner dropdown = (Spinner)view.findViewById(R.id.slot_spinner);
        ArrayAdapter<CharSequence> adapter_ = ArrayAdapter.createFromResource(getContext(),R.array.objectives_array,android.R.layout.simple_spinner_item);
        adapter_.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter_);
        dropdown.setOnItemSelectedListener(this);
        return view;
    }

    @Override
    public String updateUser() {
        if (adapter.getSelectedChoices().size() < 1){
            return "Please choose at least 1 time slot";
        }
        String str = "slot selection";
        User.getCurrentUser().setSlots(adapter.getSelectedChoices());
        return str;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Object item = adapterView.getItemAtPosition(i);
        primary_obj = item.toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Availability_Slot slot);
    }

}
