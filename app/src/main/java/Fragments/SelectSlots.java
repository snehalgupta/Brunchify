package Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import Adapters.Slots_RV_Adapter;
import Models.Availability_Slot;
import Models.Meetup;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.recyclerview.widget.RecyclerView;
import teamcool.mandeep.brunchify.R;

public class SelectSlots extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private RecyclerView recyclerView;
    private ArrayList<Availability_Slot> arr ;
    private OnFragmentInteractionListener mListener;

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
        sdf = new SimpleDateFormat("MMMMM");
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

    public static SelectSlots newInstance(String param1) {
        SelectSlots fragment = new SelectSlots();
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
        View view = inflater.inflate(R.layout.fragment_select__slots, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_slots);
        arr = get_slots();
        FlexboxLayoutManager layoutm = new FlexboxLayoutManager(getContext());
        layoutm.setFlexDirection(FlexDirection.ROW);
        layoutm.setJustifyContent(JustifyContent.FLEX_START);
        recyclerView.setLayoutManager(layoutm);
        Slots_RV_Adapter adapter = new Slots_RV_Adapter(getContext(),arr,mListener);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Availability_Slot slot);
    }

}
