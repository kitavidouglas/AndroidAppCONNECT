package com.example.connect;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Register extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    EditText editRegisterText, editTextTextPassword, editTextRegisterEmailAddress, editRegisterText4, editRegsterPhoneno;
    Button Registerbutton2;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editRegisterText = findViewById(R.id.editRegisterText);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        editTextRegisterEmailAddress = findViewById(R.id.editTextRegisterEmailAddress);
        editRegisterText4 = findViewById(R.id.editRegisterText4);
        editRegsterPhoneno = findViewById(R.id.editRegsterPhoneno);
        Registerbutton2 = findViewById(R.id.Registerbutton2);
        progressBar = findViewById(R.id.progressBar);

        Registerbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editRegisterText.getText().toString().trim();
                String email = editTextRegisterEmailAddress.getText().toString().trim();
                String password = editTextTextPassword.getText().toString().trim();
                String location = editRegisterText4.getText().toString().trim();
                String phoneNumber = editRegsterPhoneno.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    editRegisterText.setError("Username is required");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    editTextRegisterEmailAddress.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    editTextTextPassword.setError("Password is required");
                    return;
                }

                if (TextUtils.isEmpty(location)) {
                    editRegisterText4.setError("Location is required");
                    return;
                }

                if (TextUtils.isEmpty(phoneNumber)) {
                    editRegsterPhoneno.setError("Phone number is required");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Sign up with email and password
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign up successful
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    if (user != null) {
                                        String userId = user.getUid();

                                        // Save additional user details to Realtime Database
                                        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                                        User newUser = new User(username, email, location, phoneNumber);
                                        usersRef.setValue(newUser)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        // Data successfully added
                                                        Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                                                        // Proceed to login screen
                                                        Intent intent = new Intent(Register.this, LogInActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // Failed to add data
                                                        Toast.makeText(getApplicationContext(), "Failed to register: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                } else {
                                    // Sign up failed
                                    handleRegistrationError(task);
                                }
                            }
                        });
            }
        });
    }

    private void handleRegistrationError(Task<AuthResult> task) {
        try {
            throw task.getException();
        } catch (FirebaseAuthWeakPasswordException e) {
            editTextTextPassword.setError("Your password is too weak. Kindly use a mix of alphabets, numbers, and special characters");
            editTextTextPassword.requestFocus();
        } catch (FirebaseAuthInvalidCredentialsException e) {
            editTextRegisterEmailAddress.setError("Your email is invalid or already in use");
            editTextRegisterEmailAddress.requestFocus();
        } catch (FirebaseAuthUserCollisionException e) {
            editTextRegisterEmailAddress.setError("User is already registered");
            editTextRegisterEmailAddress.requestFocus();
        } catch (Exception e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Assuming you have a User class defined elsewhere in your project
    public static class User {
        public String username;
        public String email;
        public String location;
        public String phoneNumber;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String username, String email, String location, String phoneNumber) {
            this.username = username;
            this.email = email;
            this.location = location;
            this.phoneNumber = phoneNumber;
        }
    }

    // Password validation method (optional)
    public static boolean isValid(String password) {
        int f1 = 0, f2 = 0, f3 = 0;
        if (password.length() < 8) {
            return false;
        } else {
            for (int p = 0; p < password.length(); p++) {
                if (Character.isLetter(password.charAt(p))) {
                    f1 = 1;
                }
            }
            for (int r = 0; r < password.length(); r++) {
                if (Character.isDigit(password.charAt(r))) {
                    f2 = 1;
                }
            }
            for (int s = 0; s < password.length(); s++) {
                char c = password.charAt(s);
                if ((c >= 33 && c <= 46) || c == 64) {
                    f3 = 1;
                }
            }
            return f1 == 1 && f2 == 1 && f3 == 1;
        }
    }
}
