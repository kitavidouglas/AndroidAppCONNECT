package com.example.connect;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UploadActivity extends AppCompatActivity {

    private Spinner testTypeSpinner;
    private EditText resultsInput, diagnosisInput, commentsInput;
    private Button submitButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        testTypeSpinner = findViewById(R.id.test_type_spinner);
        resultsInput = findViewById(R.id.results_input);
        diagnosisInput = findViewById(R.id.diagnosis_input);
        commentsInput = findViewById(R.id.comments_input);
        submitButton = findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitTestResults();
            }
        });
    }

    private void submitTestResults() {
        String testType = testTypeSpinner.getSelectedItem().toString();
        String results = resultsInput.getText().toString().trim();
        String diagnosis = diagnosisInput.getText().toString().trim();
        String comments = commentsInput.getText().toString().trim();

        if (results.isEmpty() || diagnosis.isEmpty()) {
            Toast.makeText(this, "Please fill all the required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userTestsRef = mDatabase.child("Users").child(userId).child("test_results");

            String testId = userTestsRef.push().getKey();
            TestResult testResult = new TestResult(testType, results, diagnosis, comments);

            if (testId != null) {
                userTestsRef.child(testId).setValue(testResult)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UploadActivity.this, "Test results submitted successfully", Toast.LENGTH_SHORT).show();
                                    clearInputs();
                                } else {
                                    Toast.makeText(UploadActivity.this, "Failed to submit test results", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearInputs() {
        testTypeSpinner.setSelection(0);
        resultsInput.setText("");
        diagnosisInput.setText("");
        commentsInput.setText("");
    }

    // TestResult class to structure the test results data
    public static class TestResult {
        public String testType;
        public String results;
        public String diagnosis;
        public String comments;

        public TestResult() {
            // Default constructor required for calls to DataSnapshot.getValue(TestResult.class)
        }

        public TestResult(String testType, String results, String diagnosis, String comments) {
            this.testType = testType;
            this.results = results;
            this.diagnosis = diagnosis;
            this.comments = comments;
        }
    }
}
