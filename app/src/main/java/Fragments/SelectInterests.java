package Fragments;

import android.os.Bundle;

import Adapters.Pager_Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import Models.User;
import teamcool.mandeep.brunchify.R;

public class SelectInterests extends BaseOnboardFragment{

    private Pager_Adapter adapter_vp;

    public SelectInterests() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select__interests, container, false);
        ViewPager vpager = (ViewPager)view.findViewById(R.id.vpPager);
        vpager.setOffscreenPageLimit(3);
        adapter_vp = new Pager_Adapter(getFragmentManager());
        vpager.setAdapter(adapter_vp);
        return view;
    }

    @Override
    public String updateUser() {
        User.getCurrentUser().interests.clear();
        for (BaseOnboardFragment f: adapter_vp.fragments){
            f.updateUser();
        }
        if (User.getCurrentUser().interests.size()<3){
            return "Please select at least 3 interests";
        }
        return null;
    }
}
