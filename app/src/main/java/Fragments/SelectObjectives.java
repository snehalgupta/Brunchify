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

import androidx.recyclerview.widget.RecyclerView;

import Models.User;
import teamcool.mandeep.brunchify.R;

public class SelectObjectives extends BaseOnboardFragment{

    private RecyclerView recyclerView;
    private ArrayList<String> objectives_arr;
    private BaseChoiceAdapter<String> adapter;

    public SelectObjectives() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select__objectives, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_obj);
        FlexboxLayoutManager layoutm = new FlexboxLayoutManager(getContext());
        layoutm.setFlexDirection(FlexDirection.ROW);
        layoutm.setJustifyContent(JustifyContent.CENTER);
        recyclerView.setLayoutManager(layoutm);
        objectives_arr = new ArrayList<String>();
        objectives_arr.add("Brainstorming");
        objectives_arr.add("Invest Money");
        objectives_arr.add("Explore New Projects");
        objectives_arr.add("Mentor Others");
        objectives_arr.add("Organize Events");
        objectives_arr.add("Start a company");
        objectives_arr.add("Find Cofounder or Partner");
        objectives_arr.add("Raise Funding");
        objectives_arr.add("Business Development");
        objectives_arr.add("Grow your team");
        objectives_arr.add("Explore other companies");
        //Objectives_RV_Adapter adapter = new Objectives_RV_Adapter(getContext(),objectives_arr,mListener);
        adapter = new BaseChoiceAdapter<>(getContext(),objectives_arr,R.layout.objectives_button);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public String updateUser() {
        if (adapter.getSelectedChoices().size() < 1){
            return "Please select an objective";
        }
        User.getCurrentUser().setObjectives(adapter.getSelectedChoices());
        return null;
    }

}
