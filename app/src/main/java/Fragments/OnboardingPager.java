package Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import Activities.SelectOptions;
import teamcool.mandeep.brunchify.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnboardingPager extends BaseOnboardFragment {


    private static final String TAG = OnboardingPager.class.getSimpleName();
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private BaseOnboardFragment[] fragments;


    public OnboardingPager() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_onboarding_pager, container, false);


        initFragments();

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
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


    private int getItem(int i){
        return viewPager.getCurrentItem()+i;
    }

    public void initFragments(){
        fragments = new BaseOnboardFragment[]{
                new SelectObjectives(),
                new SelectInterests(),
                new SelectNeighbourhood(),
                new Write_Intro()
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
