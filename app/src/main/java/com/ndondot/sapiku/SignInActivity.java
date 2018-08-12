package com.ndondot.sapiku;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    EditText mEmailText,mPasswordText;
    Button mSignInBtn,mSignUpBtn;
    TextView mForgotPwd;
    FirebaseAuth mAuth;

    private String TAG = "SignIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        mEmailText = findViewById(R.id.email);
        mPasswordText = findViewById(R.id.password);
        mSignInBtn = findViewById(R.id.sign_in);
        mSignUpBtn = findViewById(R.id.sign_up);
        mForgotPwd = findViewById(R.id.forgot_pw);

        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
            }
        });

    }

    public void login(){
        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(TAG, "signInWithEmail:success");
                    startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                    finish();
                }else{
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    final Snackbar snackbar = Snackbar.make(findViewById(R.id.view_signIn), "sign in has been failed! Please try again.", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                        }
                    });
                    snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
                    snackbar.show();
                }
            }
        });
    }
}
