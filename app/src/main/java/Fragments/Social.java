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


public class Social extends BaseOnboardFragment {

    private static final String ARG_PARAM1 = "param1";
    public int mParam1;
    private RecyclerView recyclerView;
    private ArrayList<String> social;
    private BaseChoiceAdapter<String> adapter;

    public Social() {
    }

    public static Social newInstance(int param1) {
        Social fragment = new Social();
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
        View view = inflater.inflate(R.layout.fragment_social, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_social);
        StaggeredGridLayoutManager layoutm = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        //FlexboxLayoutManager layoutm = new FlexboxLayoutManager(getContext());
        //layoutm.setFlexDirection(FlexDirection.ROW);
        //layoutm.setJustifyContent(JustifyContent.CENTER);
        recyclerView.setLayoutManager(layoutm);
        social = new ArrayList<String>();
        social.add("Diversity and Inclusion");
        social.add("Photography");
        social.add("Travel");
        social.add("Hiking");
        social.add("Fishing");
        social.add("Music");
        social.add("Social Impact");
        //Social_RV_Adapter adapter = new Social_RV_Adapter(getContext(),social,mListener);
        adapter = new BaseChoiceAdapter<>(getContext(),social,R.layout.oval_select_button);

        ArrayList<String> selected = new ArrayList<String>();
        for (String interest: social){
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
