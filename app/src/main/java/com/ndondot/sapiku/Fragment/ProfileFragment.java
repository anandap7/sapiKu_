package com.ndondot.sapiku.Fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ndondot.sapiku.R;
import com.ndondot.sapiku.SignUpActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    EditText username, password;
    Button signup, signin;
    TextView forgot;

    private FirebaseAuth mAuth;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        signup = (Button) view.findViewById(R.id.sign_up);
        signin = (Button) view.findViewById(R.id.sign_in);
        forgot = (TextView) view.findViewById(R.id.forgot_pw);

        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SignUpActivity.class));
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}
