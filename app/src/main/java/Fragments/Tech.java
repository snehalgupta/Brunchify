package Fragments;

import android.content.Context;
import android.os.Bundle;

import Adapters.Tech_RV_Adapter;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import teamcool.mandeep.brunchify.R;


public class Tech extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private RecyclerView recyclerView;
    private ArrayList<String> tech;
    private OnFragmentInteractionListener mListener;

    public Tech() {
    }

    public static Tech newInstance(String param1) {
        Tech fragment = new Tech();
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
        View view = inflater.inflate(R.layout.fragment_tech, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_tech);
        FlexboxLayoutManager layoutm = new FlexboxLayoutManager(getContext());
        layoutm.setFlexDirection(FlexDirection.ROW);
        layoutm.setJustifyContent(JustifyContent.CENTER);
        recyclerView.setLayoutManager(layoutm);
        tech = new ArrayList<String>();
        tech.add("AI");
        tech.add("Programming Languages");
        tech.add("Data Science");
        tech.add("Product Design");
        tech.add("Crypto");
        tech.add("VR/AR");
        Tech_RV_Adapter adapter = new Tech_RV_Adapter(getContext(),tech,mListener);
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
        void onFragmentInteraction(String item);
    }

}
