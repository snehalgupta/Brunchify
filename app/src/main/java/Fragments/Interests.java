package Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import Adapters.Interest_RV_Adapter;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import teamcool.mandeep.brunchify.R;


public class Interests extends Fragment {

    private static String type;

    private String mParam1;
    private RecyclerView recyclerView;
    private ArrayList<String> interests;
    private ArrayList<String> business;
    private ArrayList<String> tech;
    private ArrayList<String> social;
    private OnFragmentInteractionListener mListener;

    public Interests() {
    }

    public static Interests newInstance(String param1) {
        Interests fragment = new Interests();
        type = param1;
        Bundle args = new Bundle();
        args.putString(type, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(type);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interest, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_interests);
        FlexboxLayoutManager layoutm = new FlexboxLayoutManager(getContext());
        layoutm.setFlexDirection(FlexDirection.ROW);
        layoutm.setJustifyContent(JustifyContent.FLEX_START);
        recyclerView.setLayoutManager(layoutm);
        business = new ArrayList<String>();
        tech = new ArrayList<String>();
        social = new ArrayList<String>();
        business.add("Healthcare");
        business.add("Entrepreneurship");
        business.add("Marketing");
        business.add("Education");
        business.add("Venture Capital");
        business.add("Consulting");
        business.add("Investment Banking");
        business.add("E-commerce");
        business.add("Retail");
        social.add("Diversity and Inclusion");
        social.add("Photography");
        social.add("Travel");
        social.add("Hiking");
        social.add("Fishing");
        social.add("Music");
        social.add("Social Impact");
        tech.add("AI");
        tech.add("Programming Languages");
        tech.add("Data Science");
        tech.add("Product Design");
        tech.add("Crypto");
        tech.add("VR/AR");
        mParam1 = type;
        Toast.makeText(getContext(),type,Toast.LENGTH_LONG).show();
        if(mParam1.equals("0")){
            interests = business;
        }
        else if(mParam1.equals("1")){
            interests = social;
        }
        else{
            interests = tech;
        }
        Interest_RV_Adapter adapter = new Interest_RV_Adapter(getContext(),interests,mListener);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
