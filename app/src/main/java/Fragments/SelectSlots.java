package Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import Activities.Dashboard;
import Activities.PreferenceManager;
import Adapters.BaseChoiceAdapter;
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
    private ArrayList<Availability_Slot> arr ;
    private OnWizardInteractionListener mListener;
    private Button doneBtn;

    private PreferenceManager prefManager;
    private BaseChoiceAdapter<Availability_Slot> adapter;
    private String primary_obj;


    public SelectSlots() {
    }

    public static ArrayList<Availability_Slot> get_slots(){

        ArrayList<Availability_Slot> ans = new ArrayList<Availability_Slot>();
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
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            String day = sdf.format(next_slot);
            sdf = new SimpleDateFormat("MMM");
            String month = sdf.format(next_slot);
            sdf = new SimpleDateFormat("yyyy");
            String year = sdf.format(next_slot);
            sdf = new SimpleDateFormat("dd");
            String date = sdf.format(next_slot);
            Availability_Slot tempslot = new Availability_Slot(day,month+" "+date+", "+year,timings);
            ans.add(tempslot);
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
        //Slots_RV_Adapter adapter = new Slots_RV_Adapter(getContext(),arr,mListener);

        adapter = new BaseChoiceAdapter<>(getContext(), arr, R.layout.slot_item);

        recyclerView.setAdapter(adapter);
        Spinner dropdown = (Spinner)view.findViewById(R.id.slot_spinner);
        ArrayAdapter<CharSequence> adapter_ = ArrayAdapter.createFromResource(getContext(),R.array.objectives_array,android.R.layout.simple_spinner_item);
        adapter_.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter_);
        dropdown.setOnItemSelectedListener(this);
        prefManager = new PreferenceManager(getContext());

        doneBtn = (Button) view.findViewById(R.id.done_onboarding_btn);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeOnboarding();
            }
        });
        return view;

    }

    @Override
    public String updateUser() {
        if (adapter.getSelectedChoices().size() < 1){
            return "Please choose at least 1 time slot";
        }
        User.getCurrentUser().setSlots(adapter.getSelectedChoices());
        return null;
    }

    private void completeOnboarding() {
        prefManager.setFirstTimeLaunch(false);

        // Write all collected info for user, to object and to firestore

        // TODO: Launch All set page
        startActivity(new Intent(getContext(), Dashboard.class));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnWizardInteractionListener) {
            mListener = (OnWizardInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
