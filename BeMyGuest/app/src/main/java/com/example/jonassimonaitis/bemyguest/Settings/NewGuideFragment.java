package com.example.jonassimonaitis.bemyguest.Settings;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jonassimonaitis.bemyguest.R;
import com.example.jonassimonaitis.bemyguest.TermAndCondActivity;


public class NewGuideFragment extends Fragment {


    private Button nextPageBtn;
    private TextView termAndCond;

    public NewGuideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_new_guide, container, false);


        termAndCond = rootView.findViewById(R.id.textView8);
        nextPageBtn = rootView.findViewById(R.id.nextpageguideBtn);
        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment nextPage = new NewGuideFragment2();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.settings_frame_layout, nextPage).addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


        termAndCond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent termAndCond = new Intent(getContext(), TermAndCondActivity.class);
                startActivity(termAndCond);

            }
        });


        return rootView;
    }

}
