package Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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
    private TextView[] dots;
    private LinearLayout dotsLayout;

    public OnboardingPager() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_onboarding_pager, container, false);


        initFragments();

        dotsLayout = (LinearLayout) view.findViewById(R.id.layoutDots);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        myViewPagerAdapter = new MyViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        viewPager.setOffscreenPageLimit(fragments.length+1);
        addBottomDots(0);

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

        //TODO: Remove neighbourhood screen
        fragments = new BaseOnboardFragment[]{
                new SelectObjectives(),
                new SelectInterests(),
                //new SelectNeighbourhood(),
                new Write_Intro()
        };
    }

    public int getNumFragments() {
        return fragments.length;
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
            return fragments.length;
        }

    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void addBottomDots(int currentPage) {
        dots = new TextView[fragments.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getContext());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

}
