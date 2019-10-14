package Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import Activities.Dashboard;
import Activities.PreferenceManager;
import Activities.WeeklySignUp;
import Models.User;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import teamcool.mandeep.brunchify.R;

public class Write_Intro extends BaseOnboardFragment {

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private TextView subject;
    private EditText intro;
    private Button doneBtn;
    private PreferenceManager prefManager;

    public Write_Intro() {
    }

    public static Write_Intro newInstance(String param1) {
        Write_Intro fragment = new Write_Intro();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write__intro, container, false);
        subject = (TextView)view.findViewById(R.id.msg_text);
        intro = (EditText)view.findViewById(R.id.bio_textbox);
        String temp = subject.getText().toString();
        String added = "To: ";
        added += "You";
        added += ", Your Match";
        subject.setText(temp+added);
        prefManager = new PreferenceManager(getContext());
        initDoneBtn(view);
        return view;
    }

    @Override
    public String updateUser() {
        User.getCurrentUser().intro = intro.getText().toString();
        return null;
    }

    /*************** Code for last fragment of Onboarding Wizard ****************/

    private void initDoneBtn(View view) {
        doneBtn = (Button) view.findViewById(R.id.done_onboarding_btn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeOnboarding();
            }
        });
    }

    private void completeOnboarding() {
        prefManager.setFirstTimeLaunch(false);

        // Write all collected info for user, to object and to firestore

        // TODO: Launch All set page
        startActivity(new Intent(getContext(), WeeklySignUp.class));
    }

    private OnWizardInteractionListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnWizardInteractionListener) {
            mListener = (OnWizardInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /*************** #################################### ****************/

}
