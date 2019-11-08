package Fragments;

import android.os.Bundle;

import Adapters.BaseChoiceAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import Models.User;
import teamcool.mandeep.brunchify.R;


public class Tech extends BaseOnboardFragment {

    private static final String ARG_PARAM1 = "param1";
    public int mParam1;
    private RecyclerView recyclerView;
    private ArrayList<String> tech;
    private BaseChoiceAdapter<String> adapter;

    public Tech() {
    }

    public static Tech newInstance(int param1) {
        Tech fragment = new Tech();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tech, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_tech);
        StaggeredGridLayoutManager layoutm = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        //FlexboxLayoutManager layoutm = new FlexboxLayoutManager(getContext());
        //layoutm.setFlexDirection(FlexDirection.ROW);
        //layoutm.setJustifyContent(JustifyContent.CENTER);
        recyclerView.setLayoutManager(layoutm);
        tech = new ArrayList<String>();
        tech.add("AI");
        tech.add("Programming Languages");
        tech.add("Data Science");
        tech.add("Product Design");
        tech.add("Crypto");
        tech.add("VR/AR");
//        Tech_RV_Adapter adapter = new Tech_RV_Adapter(getContext(),tech,mListener);
        adapter = new BaseChoiceAdapter<>(getContext(),tech,R.layout.oval_select_button);

        ArrayList<String> selected = new ArrayList<String>();
        for (String interest: tech){
            if (User.getCurrentUser().interests.contains(interest)){
                selected.add(interest);
            }
        }
        adapter.setSelectedChoices(selected);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public String updateUser() {
        User.getCurrentUser().addInterests(adapter.getSelectedChoices());
        return null;
    }

}
