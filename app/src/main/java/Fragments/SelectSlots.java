package Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import Models.Availability_Slot;
import Models.Meetup;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
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
        arr = new ArrayList<Availability_Slot>();
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
        void onFragmentInteraction(Uri uri);
    }

}
