package Activities;

//import android.support.v7.app.AppCompatActivity;
import Fragments.All_Set;
import Fragments.BaseOnboardFragment;
import Fragments.OnWizardInteractionListener;
import Fragments.OnboardingPager;
import Fragments.SelectInterests;
import Fragments.SelectNeighbourhood;
import Fragments.SelectObjectives;
import Fragments.Write_Intro;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import teamcool.mandeep.brunchify.R;

import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;
import android.widget.Toast;

public class SelectOptions extends FragmentActivity implements
        OnWizardInteractionListener {
    private static final String TAG = SelectOptions.class.getSimpleName();
    private All_Set allSetFragment;
    private OnboardingPager viewPager;

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

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        viewPager = new OnboardingPager();
        transaction.add(R.id.fragmentContainer, viewPager);
        transaction.commit();
    }

    private void completeOnboarding(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        allSetFragment = new All_Set();
        transaction.replace(R.id.fragmentContainer, allSetFragment);
        transaction.commit();
    }

    @Override
    public void submit() {
        completeOnboarding();
    }

    @Override
    public void begin() {
    }

    @Override
    public void cancel() {
        onBackPressed();
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
