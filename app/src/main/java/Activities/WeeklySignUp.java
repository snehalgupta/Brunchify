package Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import Fragments.OnWizardInteractionListener;
import Fragments.WeeklySignUpStart;
import Fragments.WeeklySignupPagerFragment;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.FirebaseFirestore;

import Models.User;
import teamcool.mandeep.brunchify.R;

public class WeeklySignUp extends FragmentActivity implements OnWizardInteractionListener {

    private static final String TAG = WeeklySignUp.class.getSimpleName();
    //private ViewPager viewPager;
//    private BaseOnboardFragment[] fragments;
    //private MyViewPagerAdapter myViewPagerAdapter;
    private WeeklySignUpStart weeklySignUpStart;
    private WeeklySignupPagerFragment viewPagerFragment;

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
    }

    private void completeWeeklySignup(){
        User.writeToFirestore(FirebaseFirestore.getInstance(), null);
        startActivity(new Intent(WeeklySignUp.this, Dashboard.class));
        finish();
    }

    public void initFragments(){

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        weeklySignUpStart = new WeeklySignUpStart();
        transaction.add(R.id.fragmentContainer, weeklySignUpStart);
        transaction.commit();

        viewPagerFragment = new WeeklySignupPagerFragment();
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
        String msg = viewPagerFragment.updateUser();
        if (msg!=null){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }
        else {
            completeWeeklySignup();
        }
    }

    @Override
    public void begin() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.fragmentContainer, viewPagerFragment);
        transaction.commit();

        /*viewPager = (ViewPager) findViewById(R.id.view_pager_);
        myViewPagerAdapter = new WeeklySignUp.MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setOffscreenPageLimit(fragments.length+1);*/
    }

    @Override
    public void cancel() {
        completeWeeklySignup();
    }
}
