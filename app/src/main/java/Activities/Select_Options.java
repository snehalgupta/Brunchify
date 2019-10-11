package Activities;

//import android.support.v7.app.AppCompatActivity;
import Fragments.Select_Neighbourhood;
import Fragments.Select_Objectives;
import Fragments.Select_Slots;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import teamcool.mandeep.brunchify.R;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;

public class Select_Options extends FragmentActivity implements Select_Objectives.OnFragmentInteractionListener,Select_Neighbourhood.OnFragmentInteractionListener {
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private int no_of_fragments = 3;
    private Button btn_skip;
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Hide action bar
        //getSupportActionBar().hide();
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        changeStatusBarColor();
        btn_skip = (Button)findViewById(R.id.Btn_Skip);
        btn_next = (Button)findViewById(R.id.Btn_Next);
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myViewPagerAdapter);
        btn_skip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                launchDashboard();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int current = getItem(+1);
                if (current < no_of_fragments) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchDashboard();
                }
            }
        });

    }

    private void launchDashboard(){
        startActivity(new Intent(Select_Options.this, Dashboard.class));
        finish();
    }

    private int getItem(int i){
        return viewPager.getCurrentItem()+i;
    }

    @Override
    public void onFragmentInteraction(String item) {

    }

    public class MyViewPagerAdapter extends FragmentPagerAdapter{

        public MyViewPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int pos){
            switch(pos){
                case 0: return Select_Objectives.newInstance("Select objectives");
                case 1: return Select_Neighbourhood.newInstance("Select neighbourhood");
                case 2: return Select_Slots.newInstance("Select slots");
                default: return Select_Objectives.newInstance("Default");
            }
        }

        @Override
        public int getCount(){
            return no_of_fragments;
        }

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
