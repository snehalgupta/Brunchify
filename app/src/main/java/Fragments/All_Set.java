package Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import Activities.Dashboard;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import Activities.WeeklySignUp;
import teamcool.mandeep.brunchify.R;

public class All_Set extends Fragment implements OnCompleteListener<Void> {

    private static String TAG = "";
    private String progressMessage;
    private Button getStartedBtn;
    private Context context;
    private boolean firebaseWriteComplete = false;
    private LinearLayout progressBar;
    private LinearLayout mainLayout;
    private boolean autoProceed = false;

    public All_Set() {
        this.autoProceed = false;
        this.progressMessage = "Setting You Up :)";
    }

    public All_Set(boolean autoProceed) {
        this.autoProceed = autoProceed;
        this.progressMessage = "Setting You Up :)";
    }

    public All_Set(String progressMessage, boolean autoProceed) {
        this.autoProceed = autoProceed;
        this.progressMessage = progressMessage;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all__set, container, false);
        ((TextView)view.findViewById(R.id.progress_msg_tv)).setText(progressMessage);
        getStartedBtn = (Button)view.findViewById(R.id.get_started_btn);
        if (!firebaseWriteComplete){getStartedBtn.setVisibility(View.INVISIBLE);}
        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceed();
            }
        });
        mainLayout = (LinearLayout)view.findViewById(R.id.all_set_content);
        progressBar = (LinearLayout)view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void proceed(){
        startActivity(new Intent(context, Dashboard.class));
        //finish();
    }

    @Override
    public void onComplete(Task<Void> task) {
        if (task != null) {
            String tag = "writeUserToFirebase";
            if (task.isSuccessful()) {
                Log.i(tag, "User written to Firestore");
                firebaseWriteComplete = true;
                //Toast.makeText(getContext(), "Error registering to server", Toast.LENGTH_LONG).show();
            } else {
                Log.e(tag, "Error Registering user to firebase", task.getException());
                Toast.makeText(getContext(), "Error registering to server", Toast.LENGTH_LONG).show();
            }
        }
        if (autoProceed){
            proceed();
        }
        else {
            progressBar.setVisibility(View.INVISIBLE);
            mainLayout.setVisibility(View.VISIBLE);
            getStartedBtn.setVisibility(View.VISIBLE);
        }

    }
}


