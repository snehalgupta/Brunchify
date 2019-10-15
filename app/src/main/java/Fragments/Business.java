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


public class Business extends BaseOnboardFragment {

    private static final String ARG_PARAM1 = "param1";
    public int mParam1;
    private RecyclerView recyclerView;
    private ArrayList<String> business;
    private BaseChoiceAdapter<String> mAdapter;

    public Business() {
    }

    public static Business newInstance(int param1) {
        Business fragment = new Business();
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
        View view = inflater.inflate(R.layout.fragment_business, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_business);
        StaggeredGridLayoutManager layoutm = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        //FlexboxLayoutManager layoutm = new FlexboxLayoutManager(getContext());
        //layoutm.setFlexDirection(FlexDirection.ROW);
        //layoutm.setJustifyContent(JustifyContent.CENTER);
        recyclerView.setLayoutManager(layoutm);
        business = new ArrayList<String>();
        business.add("Healthcare");
        business.add("Entrepreneurship");
        business.add("Marketing");
        business.add("Education");
        business.add("Venture Capital");
        business.add("Consulting");
        business.add("Investment Banking");
        business.add("E-commerce");
        business.add("Retail");
        //Toast.makeText(getContext(),type,Toast.LENGTH_LONG).show();
        //mAdapter = new Business_RV_Adapter(getContext(),business,mListener);
        mAdapter = new BaseChoiceAdapter<>(getContext(), business, R.layout.oval_select_button);
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public String updateUser() {
        User.getCurrentUser().addInterests(mAdapter.getSelectedChoices());
        return null;
    }
}
