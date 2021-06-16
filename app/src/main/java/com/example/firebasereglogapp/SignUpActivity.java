package com.example.firebasereglogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {
    EditText name,email,password,phone;
private static boolean valids=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
          name=findViewById(R.id.et_name);
        email=findViewById(R.id.et_email);
        password=findViewById(R.id.et_password);
        phone=findViewById(R.id.et_number);
        findViewById(R.id.btn_createAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFields(name);
                checkFields(email);
                checkFields(password);
                checkFields(phone);
                if (valids){





                }
            }
        });
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));

            }
        });
    }
    public static boolean checkFields(EditText editText){
        if (editText.getText().toString().isEmpty()){
            editText.setError("Error !");
            valids=false;
        }else {
            valids=true;
        }
        return valids;
    }
}