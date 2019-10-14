package Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import Fragments.All_Set;
import Fragments.BaseOnboardFragment;
import Fragments.OnWizardInteractionListener;
import Fragments.SelectInterests;
import Fragments.SelectNeighbourhood;
import Fragments.SelectObjectives;
import Fragments.SelectSlots;
import Fragments.Weekly_Sign_Up;
import Fragments.Write_Intro;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import teamcool.mandeep.brunchify.R;

public class WeeklySignUp extends FragmentActivity implements OnWizardInteractionListener {

    private static final String TAG = WeeklySignUp.class.getSimpleName();
    private ViewPager viewPager;
    private BaseOnboardFragment[] fragments;
    private MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Hide action bar
        //getSupportActionBar().hide();
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_weeklysignup);
        changeStatusBarColor();

        initFragments();

        viewPager = (ViewPager) findViewById(R.id.view_pager_);
        myViewPagerAdapter = new WeeklySignUp.MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setOffscreenPageLimit(fragments.length+1);
    }

    private void launchDashboard(){
        startActivity(new Intent(WeeklySignUp.this, Dashboard.class));
        finish();
    }

    public void initFragments(){
        fragments = new BaseOnboardFragment[]{
                new All_Set(),
                new SelectSlots(),
                new Weekly_Sign_Up()
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

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void submit() {
        for (int i=0; i<fragments.length;i++){
            String msg = fragments[i].updateUser();
            if (msg!=null){
                Log.i(TAG,fragments[i].getClass().getSimpleName() + " Returned message " + msg);
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(i, true);
            }
        }
    }
}
