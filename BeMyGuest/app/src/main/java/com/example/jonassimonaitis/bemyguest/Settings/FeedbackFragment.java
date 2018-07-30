package com.example.jonassimonaitis.bemyguest.Settings;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jonassimonaitis.bemyguest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends Fragment {

    private EditText subject, message;
    private Button submitFeedback;


    public FeedbackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_feedback, container, false);


        subject = view.findViewById(R.id.editTextSubjectInput);
        message = view.findViewById(R.id.editTextMessageInput);
        submitFeedback = view.findViewById(R.id.buttonSubmitFeedback);
        submitFeedback.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String emailedTo = "dzonas.sim@gmail.com";
                String subjectInfo = subject.getText().toString().trim();
                String messageInfo = message.getText().toString().trim();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[] {emailedTo});
                email.putExtra(Intent.EXTRA_SUBJECT, subjectInfo);
                email.putExtra(Intent.EXTRA_TEXT, messageInfo);

                email.setType("message/rfc822"); // get this in description
                startActivity(Intent.createChooser(email, "Choose app to send mail"));

            }
        } );



        return view;
    }

}
