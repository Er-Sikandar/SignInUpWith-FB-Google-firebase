package com.example.firebasereglogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.firebasereglogapp.model.ModelCustom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    EditText name,age,email,password,phone;
private static boolean valids=true;
RadioGroup radioGroup;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private ProgressDialog dialog;
    String userType="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        dialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        firestore =FirebaseFirestore.getInstance();
          name=findViewById(R.id.et_name);
        age=findViewById(R.id.et_age);
        email=findViewById(R.id.et_email);
        password=findViewById(R.id.et_password);
        phone=findViewById(R.id.et_number);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
      //  radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
               switch (checkedId){
                   case R.id.rb_user:
                     userType="1";
                       break;
                   case R.id.rb_admin:
                       userType="3";
                       break;
               }
            }
        });
        findViewById(R.id.btn_createAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFields(name);
                checkFields(age);
                checkFields(email);
                checkFields(password);
                checkFields(phone);
                String uname=name.getText().toString().trim();
                String uage=age.getText().toString().trim();
               String uphone=phone.getText().toString().trim();
               String uemail=email.getText().toString().trim();
               String upass=password.getText().toString().trim();

                if (valids){
                    if (!Patterns.EMAIL_ADDRESS.matcher(uemail).matches()){
                        email.setError("Please Provide Valid email !");
                        email.requestFocus();
                    }else if (upass.length()<8)
                    {
                        password.setError("Min Password Length !");
                        password.requestFocus();
                    }else if (userType!="") {
                        dialog.setMessage("Registring Data...");
                        dialog.show();
                        firebaseAuth.createUserWithEmailAndPassword(uemail, upass)
                                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
/*
//realtime db
                                            ModelCustom modelCustom = new ModelCustom(uname, uage, uphone, uemail, upass, userType);
                                            FirebaseDatabase.getInstance().getReference("MyUsers")
                                                    .child(firebaseAuth.getCurrentUser().getUid())
                                                    .setValue(modelCustom).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        dialog.dismiss();
                                                        Log.e("Success"," Successfully Registering !"+task);
                                                        onBackPressed();
                                                    } else {
                                     Log.e("Failed Registering"," Failed Registering !");
                                                    }
                                                }
                                            });
                                            */
//SECOND METHIOD firebasestorage DB
                                            FirebaseUser user = firebaseAuth.getCurrentUser();
                                            DocumentReference df = firestore.collection("AllDatas").document(user.getUid());
                                            Map<String, Object> userInfo = new HashMap<>();
                                            userInfo.put("name", name.getText().toString().trim());
                                            userInfo.put("age", age.getText().toString().trim());
                                            userInfo.put("mobile", phone.getText().toString().trim());
                                            userInfo.put("email", email.getText().toString().trim());
                                            userInfo.put("password", password.getText().toString().trim());
                                            userInfo.put("userType",userType);
                                            df.set(userInfo);
                                            dialog.dismiss();
                                            Log.e("Success"," Successfully Registering !"+task);
                                            onBackPressed();

                                        } else {
                                            Log.e("Failed", " Failed Auth !");
                                        }

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("Failed", " Failed AUTH !");
                            }
                        });
                    }
                }
            }
        });

    }
    public static boolean checkFields(EditText editText){
        if (editText.getText().toString().isEmpty()){
            editText.setError("Empty !");
            valids=false;
        }else {
            valids=true;
        }
        return valids;
    }


}