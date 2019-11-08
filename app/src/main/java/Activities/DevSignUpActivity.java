package Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import Models.User;
import teamcool.mandeep.brunchify.R;

public class DevSignUpActivity extends AppCompatActivity {

    private static final String TAG = "DevSignUp";
    private EditText emailEtv;
    private EditText nameEtv;
    private Button signupBtn;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_signup);

        emailEtv = (EditText) findViewById(R.id.email_edit_text);
        nameEtv = (EditText) findViewById(R.id.name_edit_text);

        signupBtn = (Button) findViewById(R.id.signup_btn_1);
        signupBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(emailEtv.getText().toString(), "devpass")
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(nameEtv.getText().toString())
                                            //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                                            .build();

                                    user.updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "User profile updated.");
                                                        startSignup(user);
                                                    }
                                                }
                                            });
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(DevSignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

        loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Backdoor Login clicked");
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailEtv.getText().toString(), "devpass")
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithCredential:success");
                                    startSignup(FirebaseAuth.getInstance().getCurrentUser());
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "devSignIn:failure", task.getException());
                                }
                            }
                        });
            }
        });

    }

    private void startSignup(final FirebaseUser user) {
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
                            if (User.getCurrentUser().isOnBoarded()) {
                                launchHomeScreen();
                            } else {
                                launchOnBoarding();
                            }
                        } else {
                            // TODO: Move this registration to End of Onboarding
                            // TODO: Check for invite condition
                            User.setCurrentUser(new User(user.getUid(), user.getDisplayName()));
                            userDocRef.set(User.getCurrentUser())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "firebaseUserSet: user added on firebase");
                                            launchOnBoarding();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e(TAG, "Error Registering user to firebase", e);
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "fetchFirebaseUser: Failure", e);
                    }
                });
    }

    private void launchOnBoarding(){
        Intent intent = new Intent(DevSignUpActivity.this, SelectOptions.class);
        startActivity(intent);

    }

    private void launchHomeScreen() {
        //prefManager.setFirstTimeLaunch(false);
        Log.i(TAG,"Not first time user, launching homes screen");
        startActivity(new Intent(DevSignUpActivity.this, Dashboard.class));
        finish();
    }
}
