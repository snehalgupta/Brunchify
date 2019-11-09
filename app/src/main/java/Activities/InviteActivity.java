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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import teamcool.mandeep.brunchify.R;

public class InviteActivity extends AppCompatActivity {
    private static final String TAG = "InviteActivity";
    private Button inviteBtn;
    private EditText emailText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inviteactivity);

        inviteBtn = (Button) findViewById(R.id.invitebutton);
        emailText = (EditText) findViewById(R.id.invite_email_text);
        inviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                final String email = emailText.getText().toString().trim();

                SendMail sm = new SendMail(InviteActivity.this, email, "You have been invited to start your career with brunchify", "Brunchify is an invite-only service that connects mutually relevant, opportunistic people on 1:1 brunch. Every week, people explore new possibilities, talk about emerging fields and make a real life connection.");
                //Executing sendmail to send email
                sm.execute();
                final DocumentReference docRef = FirebaseFirestore.getInstance()
                        .collection("invites").document(email);

                docRef.get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()){
                                    Log.i(TAG, "Fetched Invite Successfuly");
                                    if (task.getResult().exists()){
                                        Log.d(TAG, "Already Exists");
                                        showToast("This User is already invited");
                                        Intent dashIntent=new Intent(InviteActivity.this,Dashboard.class);
                                        startActivity(dashIntent);
                                    }
                                    else{
                                        Map<String, String> data = new HashMap<>();
                                        data.put("email", email);
                                        docRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Log.d(TAG, "Add user to invites succesfullly");
                                                    showToast("Invitation Sent");
                                                }
                                                else{
                                                    Log.w(TAG, "Failed to add user to invites", task.getException());
                                                    showToast("Failed to send Invite");
                                                }
                                                Intent dashIntent=new Intent(InviteActivity.this,Dashboard.class);
                                                startActivity(dashIntent);
                                            }
                                        });
                                    }
                                }
                            }
                        });
            }
        });
    }

    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
