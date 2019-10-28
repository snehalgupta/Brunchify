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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import Models.User;
import teamcool.mandeep.brunchify.R;

import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

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
        User.writeToFirestore(FirebaseFirestore.getInstance(), allSetFragment);
        transaction.replace(R.id.fragmentContainer, allSetFragment);
        transaction.commit();
    }

    @Override
    public void submit() {
        String msg = viewPager.updateUser();
        if (msg!=null){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
        else {
            completeOnboarding();
        }
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
}
