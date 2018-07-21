package com.ndondot.sapiku;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    EditText full_name, email, phone, password, password_confirm;
    String sFull_name, sPhone, sEmail, sPassword, sPassword_confirm;
    Button signup;
    RelativeLayout progress;
    Drawable validated;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        full_name = findViewById(R.id.full_name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        password_confirm = findViewById(R.id.password_confirm);
        signup = findViewById(R.id.sign_up);
        progress = findViewById(R.id.relative_layout_progress);

        validated = getResources().getDrawable(R.drawable.validated);
        validated.setBounds(0, 0, validated.getIntrinsicWidth(), validated.getIntrinsicHeight());

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()){
                    password.setError("Kolom harus diisi");
                    password_confirm.setEnabled(false);
                }
                else {
                    password.setCompoundDrawables(null, null, validated, null);
                    password_confirm.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sPassword = password.getText().toString();
                if (charSequence.toString().equals(sPassword)) {
                    password_confirm.setCompoundDrawables(null, null, validated, null);
                    signup.setEnabled(true);
                }
                else{
                    password_confirm.setError("Password tidak sesuai");
                    signup.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        full_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!full_name.hasFocus()){
                    sFull_name = full_name.getText().toString();
                    if (sFull_name.isEmpty())
                        full_name.setError("Kolom harus diisi");
                    else
                        full_name.setCompoundDrawables(null, null, validated, null);
                }
            }
        });

        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!phone.hasFocus()){
                    sPhone = phone.getText().toString();
                    if (sPhone.isEmpty())
                        phone.setError("Kolom harus diisi");
                    else
                        phone.setCompoundDrawables(null, null, validated, null);
                }
            }
        });

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!email.hasFocus()){
                    sEmail = email.getText().toString();
                    if (!sEmail.contains("@"))
                        email.setError("Bukan email");
                    else
                        email.setCompoundDrawables(null, null, validated, null);
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    showProgress(true);
                    register();
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    private void register() {
        sEmail = email.getText().toString().trim();
        sPassword = password.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(sEmail, sPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            showProgress(false);
                            Toast.makeText(SignUpActivity.this, sEmail + " , " + sPassword, Toast.LENGTH_SHORT).show();
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignUpActivity.this);
                                alertDialogBuilder.setTitle("Signup");
                                alertDialogBuilder.setMessage("Your account has been registered. Please sign in use your username and password.");
                                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        finish();
                                    }
                                });
                                alertDialogBuilder.show();
                            } else {
                                // If sign in fails, display a message to the user.
                                final Snackbar snackbar = Snackbar.make(findViewById(R.id.frame_main2), "registered has been failed! Please try again.", Snackbar.LENGTH_INDEFINITE);
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
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void showProgress(boolean show){
        if (show) {
            progress.setVisibility(View.VISIBLE);
        }
        else {
            progress.setVisibility(View.GONE);
        }
        full_name.setEnabled(!show);
        email.setEnabled(!show);
        phone.setEnabled(!show);
        password.setEnabled(!show);
        password_confirm.setEnabled(!show);
    }

    private boolean validate() {
        sFull_name = full_name.getText().toString();
        sPhone = phone.getText().toString();
        sEmail = email.getText().toString();
        sPassword = password.getText().toString();
        sPassword_confirm = password_confirm.getText().toString();
        if (sFull_name.isEmpty())
            full_name.setError("Kolom harus diisi");
        if (sPhone.isEmpty())
            phone.setError("Kolom harus diisi");
        if (sEmail.isEmpty())
            email.setError("Kolom harus diisi");
        if (sPassword.isEmpty())
            password.setError("Kolom harus diisi");
        if (sPassword_confirm.isEmpty())
            password_confirm.setError("Kolom harus diisi");
        return !sFull_name.isEmpty() && !sPhone.isEmpty() && !sEmail.isEmpty() && !sPassword.isEmpty() && !sPassword_confirm.isEmpty();
    }
}
