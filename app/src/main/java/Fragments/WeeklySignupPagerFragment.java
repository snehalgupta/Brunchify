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

import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

import Activities.Dashboard;
import Activities.WeeklySignUp;
import Models.User;
import teamcool.mandeep.brunchify.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeeklySignupPagerFragment extends BaseOnboardFragment {


    private static final String TAG = WeeklySignupPagerFragment.class.getSimpleName();
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private BaseOnboardFragment[] fragments;
    private Button mDoneBtn;
    private TabLayout tabLayout;


    public WeeklySignupPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weekly_signup_pager, container, false);

        initFragments();
        initDoneBtn(view);

        viewPager = (ViewPager) view.findViewById(R.id.view_pager_);
        myViewPagerAdapter = new MyViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        viewPager.setOffscreenPageLimit(fragments.length+1);

        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);
        return view;
    }

    @Override
    public String updateUser() {
        User.getCurrentUser().setLastSignupWeek(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));
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

    /*************** Code for last fragment of Onboarding Wizard ****************/

    private void initDoneBtn(View view) {
        mDoneBtn = (Button) view.findViewById(R.id.done_btn);
        mDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.submit();
            }
        });
    }
    private OnWizardInteractionListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnWizardInteractionListener) {
            mListener = (OnWizardInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /*************** #################################### ****************/

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //addBottomDots(position);
            toggleDoneBtn(position);
        }

        private void toggleDoneBtn(int position) {
            if (position == fragments.length-1){
                mDoneBtn.setVisibility(View.VISIBLE);
            }
            else{
                mDoneBtn.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
