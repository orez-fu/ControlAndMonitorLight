package com.example.controlandmonitorlight.view.view.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.controlandmonitorlight.MainActivity;
import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.model.User;
import com.example.controlandmonitorlight.utils.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;


    private EditText edtName;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnSignUp;
    private Button btnLogin;
    private LoadingDialog dialog;

    // Data variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        // mapping
        edtName = findViewById(R.id.edt_username);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnSignUp = findViewById(R.id.btn_sign_up);
        btnLogin = findViewById(R.id.btn_go_login);

        dialog = new LoadingDialog(SignupActivity.this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpUser();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        updateUI(user);
    }

    private void signUpUser() {
        final String username = edtName.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        final String password = edtPassword.getText().toString().trim();

        if(username.isEmpty()) {
            edtName.setError("Email required");
            edtName.requestFocus();
            return;
        }

        if(email.isEmpty()) {
            edtEmail.setError("Email required");
            edtEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Enter a valid email");
            edtEmail.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            edtPassword.setError("Password required");
            edtPassword.requestFocus();
            return;
        }

        dialog.startLoadingDialog();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String key = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        User user = new User(key, username, email);

                        FirebaseDatabase.getInstance().getReference("users")
                                .child(key)
                                .setValue(user)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            Toasty.success(getApplicationContext(), "Sign up successful!!", Toast.LENGTH_SHORT, true).show();
                                        } else {
                                            Toasty.error(getApplicationContext(), "Sign up failed!!", Toast.LENGTH_SHORT, true).show();
                                        }

                                        if(dialog.isShowing()) {
                                            dialog.dismissDialog();
                                        }

                                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                        UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(username)
                                                .setPhotoUri(Uri.parse(String.valueOf("https://firebasestorage.googleapis.com/v0/b/luxremote-zm.appspot.com/o/profilepics%2Fdownload.png?alt=media&token=930c1b12-c30e-498f-9b06-47cc7c1ca42c")))
                                                .build();
                                        firebaseUser.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                finish();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                finish();
                                            }
                                        });
                                    }
                                });
                    }
                });
    }
}
