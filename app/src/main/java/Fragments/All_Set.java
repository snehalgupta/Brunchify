package Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import teamcool.mandeep.brunchify.R;

public class All_Set extends BaseOnboardFragment{

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;

    public All_Set() {
    }

    public static All_Set newInstance(String param1) {
        All_Set fragment = new All_Set();
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
        View view = inflater.inflate(R.layout.fragment_all__set, container, false);
        return view;
    }

    @Override
    public String updateUser() {
        String str= "All_set";
        return str;
    }

}


