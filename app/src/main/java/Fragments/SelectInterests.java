package Fragments;

import android.os.Bundle;

import Adapters.Pager_Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import Models.User;
import teamcool.mandeep.brunchify.R;

public class SelectInterests extends BaseOnboardFragment{
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    private Pager_Adapter adapter_vp;

    public SelectInterests() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select__interests, container, false);
        ViewPager vpager = (ViewPager)view.findViewById(R.id.vpPager);
        vpager.setOffscreenPageLimit(3);
        imageView1=(ImageView)view.findViewById(R.id.interests_img1);
        imageView2=(ImageView)view.findViewById(R.id.interests_img2);
        imageView3=(ImageView)view.findViewById(R.id.interests_img3);
        adapter_vp = new Pager_Adapter(getFragmentManager());
        vpager.setAdapter(adapter_vp);
        vpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int i, final float v, final int i2) {
            }
            @Override
            public void onPageSelected(final int i) {
                if (i ==0) {
                    imageView1.setVisibility(View.VISIBLE);
                    imageView2.setVisibility(View.GONE);
                    imageView3.setVisibility(View.GONE);
                }
                if(i==1){
                    imageView2.setVisibility(View.VISIBLE);
                    imageView1.setVisibility(View.GONE);
                    imageView3.setVisibility(View.GONE);


                }
                if(i==2){
                    imageView1.setVisibility(View.GONE);
                    imageView2.setVisibility(View.GONE);
                    imageView3.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onPageScrollStateChanged(final int i) {
            }
        });
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
