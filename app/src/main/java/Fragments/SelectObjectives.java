package Fragments;

import android.content.Context;
import android.os.Bundle;

import Adapters.Objectives_RV_Adapter;
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

public class SelectObjectives extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private RecyclerView recyclerView;
    private ArrayList<String> objectives_arr;
    private OnFragmentInteractionListener mListener;

    public SelectObjectives() {

    }

    public static SelectObjectives newInstance(String param1) {
        SelectObjectives fragment = new SelectObjectives();
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

        View view = inflater.inflate(R.layout.fragment_select__objectives, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_obj);
        FlexboxLayoutManager layoutm = new FlexboxLayoutManager(getContext());
        layoutm.setFlexDirection(FlexDirection.ROW);
        layoutm.setJustifyContent(JustifyContent.FLEX_START);
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
        Objectives_RV_Adapter adapter = new Objectives_RV_Adapter(getContext(),objectives_arr,mListener);
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
