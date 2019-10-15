package Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import Activities.Dashboard;
import Activities.WeeklySignUp;
import teamcool.mandeep.brunchify.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeeklySignupPagerFragment extends BaseOnboardFragment {


    private static final String TAG = WeeklySignupPagerFragment.class.getSimpleName();
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private BaseOnboardFragment[] fragments;


    public WeeklySignupPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weekly_signup_pager, container, false);

        initFragments();

        viewPager = (ViewPager) view.findViewById(R.id.view_pager_);
        myViewPagerAdapter = new MyViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setOffscreenPageLimit(fragments.length+1);
        return view;
    }

    @Override
    public String updateUser() {
        for (int i=0; i<fragments.length;i++){
            String msg = fragments[i].updateUser();
            if (msg!=null){
                Log.i(TAG,fragments[i].getClass().getSimpleName() + " Returned message " + msg);
                viewPager.setCurrentItem(i, true);
                return msg;
            }
        }

        return null;
    }

    void initFragments(){
        fragments = new BaseOnboardFragment[]{
                new SelectSlots(),
                new WeeksMeetingScheduleFragment()
        };
    }
    public class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int pos){
            return fragments[pos];
        }

        @Override
        public int getCount(){
            //return no_of_fragments;
            return fragments.length;
        }

    }

}
