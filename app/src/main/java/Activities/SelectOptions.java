package Activities;

//import android.support.v7.app.AppCompatActivity;
import Fragments.All_Set;
import Fragments.BaseOnboardFragment;
import Fragments.Business;
import Fragments.OnWizardInteractionListener;
import Fragments.SelectInterests;
import Fragments.SelectNeighbourhood;
import Fragments.SelectObjectives;
import Fragments.SelectSlots;
import Fragments.Social;
import Fragments.Tech;
import Fragments.Write_Intro;
import Models.Availability_Slot;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import teamcool.mandeep.brunchify.R;

import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;
import android.widget.Toast;

public class SelectOptions extends FragmentActivity implements
        OnWizardInteractionListener {
    private static final String TAG = SelectOptions.class.getSimpleName();
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private BaseOnboardFragment[] fragments;

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

        initFragments();

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setOffscreenPageLimit(fragments.length+1);
    }

    private void launchDashboard(){
        startActivity(new Intent(SelectOptions.this, WeeklySignUp.class));
        finish();
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

    public class MyViewPagerAdapter extends FragmentPagerAdapter{

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
