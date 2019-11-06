package Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import Models.User;
import teamcool.mandeep.brunchify.R;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = "SplashScreen";
    private static int SPLASH_TIME_OUT=2000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Hide action bar
        getSupportActionBar().hide();
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splashscreen);
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            loginUser();
        }
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent homeintent = new Intent(SplashScreenActivity.this, WelcomeActivity.class);
                    startActivity(homeintent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }

    private void loginUser(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DocumentReference userDocRef = FirebaseFirestore.getInstance().collection("users")
                .document(user.getUid());

        Log.d(TAG,"Fetching user info from Firestore");
        userDocRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Redundant as already checked newUser
                            User.setCurrentUser(snapshot.toObject(User.class));
                            Class cls = WelcomeActivity.class;
                            if (User.getCurrentUser().isOnBoarded()) {
                                cls = Dashboard.class;
                                Log.i(TAG, "Opening home page");
                            }
                            Intent homeintent = new Intent(SplashScreenActivity.this, cls);
                        } else {
                            // TODO: Move this registration to End of Onboarding
                            // TODO: Check for invite condition
                            User.setCurrentUser(new User(user.getUid(), user.getDisplayName()));
                            userDocRef.set(User.getCurrentUser())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Log.d(TAG, "firebaseUserSet: user added on firebase");
                                            }
                                            else{
                                                Log.e(TAG, "Error Registering user to firebase, will try again after onboarding", task.getException());

                                            }
                                            Intent homeintent = new Intent(SplashScreenActivity.this, WelcomeActivity.class);
                                            startActivity(homeintent);
                                            finish();
                                        }
                                    })
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "fetchFirebaseUser: Failure. Will try after onboarding", e);
                        Intent homeintent = new Intent(SplashScreenActivity.this, WelcomeActivity.class);
                        startActivity(homeintent);
                        finish();
                    }
                });
    }

}
