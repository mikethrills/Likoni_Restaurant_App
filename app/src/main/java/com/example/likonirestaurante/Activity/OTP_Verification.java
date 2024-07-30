package com.example.likonirestaurante.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.likonirestaurante.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTP_Verification extends AppCompatActivity {

    private EditText input_code1, input_code2, input_code3, input_code4, input_code5, input_code6;
    private TextView phone_no;
    private Button verify;
    private ProgressBar accessBar;

    private String verificationId, code;
    private FirebaseAuth mAuth;
    private String phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        accessBar = findViewById(R.id.access_bar);
        verify = findViewById(R.id.Verify);
        phone_no = findViewById(R.id.phone_no);

        mAuth = FirebaseAuth.getInstance();

        getIntentBundle();
        phone_no.setText(phoneNo);

        sendVerificationCode(phoneNo);

        setupOTPinputs();

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input_code1.getText().toString().trim().isEmpty() ||
                        input_code2.getText().toString().trim().isEmpty() ||
                        input_code3.getText().toString().trim().isEmpty() ||
                        input_code4.getText().toString().trim().isEmpty() ||
                        input_code5.getText().toString().trim().isEmpty() ||
                        input_code6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(OTP_Verification.this, "Enter valid OTP", Toast.LENGTH_SHORT).show();
                    return;
                }

                code = input_code1.getText().toString() +
                        input_code2.getText().toString() +
                        input_code3.getText().toString() +
                        input_code4.getText().toString() +
                        input_code5.getText().toString() +
                        input_code6.getText().toString();

                accessBar.setVisibility(View.VISIBLE);
                verify.setVisibility(View.GONE);

                verifyCode(code);
            }
        });
    }

    private void getIntentBundle() {
        phoneNo = getIntent().getStringExtra("Mobile");
    }

    private void sendVerificationCode(String number) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallBacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String verificationId,
                                       @NonNull PhoneAuthProvider.ForceResendingToken token) {
                    super.onCodeSent(verificationId, token);
                    OTP_Verification.this.verificationId = verificationId;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    // Auto-retrieval is disabled
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    accessBar.setVisibility(View.GONE);
                    verify.setVisibility(View.VISIBLE);

                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(OTP_Verification.this, "Invalid phone number format", Toast.LENGTH_SHORT).show();
                    } else if (e instanceof FirebaseAuthException) {
                        Toast.makeText(OTP_Verification.this, "Authentication error. Please try again later.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(OTP_Verification.this, "Verification failed. Check your internet connection.", Toast.LENGTH_SHORT).show();
                    }
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            accessBar.setVisibility(View.GONE);
                            verify.setVisibility(View.VISIBLE);
                            Intent intent = new Intent(getApplicationContext(), Complete_SigningUp.class);
                            intent.putExtra("PhoneNo", phoneNo);
                            startActivity(intent);
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(OTP_Verification.this, "Invalid OTP entered", Toast.LENGTH_SHORT).show();
                            }
                            accessBar.setVisibility(View.GONE);
                            verify.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void setupOTPinputs() {
        input_code1 = findViewById(R.id.input_code1);
        input_code2 = findViewById(R.id.input_code2);
        input_code3 = findViewById(R.id.input_code3);
        input_code4 = findViewById(R.id.input_code4);
        input_code5 = findViewById(R.id.input_code5);
        input_code6 = findViewById(R.id.input_code6);

        input_code1.addTextChangedListener(new OTPTextWatcher(input_code1, input_code2, null));
        input_code2.addTextChangedListener(new OTPTextWatcher(input_code2, input_code3, input_code1));
        input_code3.addTextChangedListener(new OTPTextWatcher(input_code3, input_code4, input_code2));
        input_code4.addTextChangedListener(new OTPTextWatcher(input_code4, input_code5, input_code3));
        input_code5.addTextChangedListener(new OTPTextWatcher(input_code5, input_code6, input_code4));
        input_code6.addTextChangedListener(new OTPTextWatcher(input_code6, null, input_code5));
    }

    private class OTPTextWatcher implements TextWatcher {

        private final EditText currentEditText;
        private final EditText nextEditText;
        private final EditText previousEditText;

        OTPTextWatcher(EditText currentEditText, EditText nextEditText, EditText previousEditText) {
            this.currentEditText = currentEditText;
            this.nextEditText = nextEditText;
            this.previousEditText = previousEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            // Handle deletion of characters
            if (count > after) {
                // If the current EditText is empty and a character is being deleted, move focus to the previous EditText
                if (currentEditText.getText().length() == 0 && previousEditText != null) {
                    previousEditText.requestFocus();
                }
            }
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            // Handle input of characters
            if (charSequence.length() == 1 && nextEditText != null) {
                nextEditText.requestFocus();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // No additional actions needed after text is changed
        }
    }
}
