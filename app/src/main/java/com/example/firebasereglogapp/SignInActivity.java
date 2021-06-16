package com.example.firebasereglogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.firebasereglogapp.SignUpActivity.checkFields;

public class SignInActivity extends AppCompatActivity {
    EditText email, password;
    RecyclerView recyclerView;
    private static boolean valids = true;

    DatabaseReference database;
    ArrayList<ModelCustom> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFields(email);
                checkFields(password);
                String name = "", lastname = "", age = "";
                if (valids) {

                }
                /*
                    for (int i = 0; i <= list.size(); i++) {
                        ModelCustom modelCustom = new ModelCustom();
                        name = modelCustom.getName();
                        lastname = modelCustom.getLastname();
                        age = modelCustom.getAge();
                        Log.e("Tag", name + " : " + lastname + " : " + age);
                    }
                 */

                }
        });
        findViewById(R.id.btn_createAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, UserActivity.class));
            }
        });
        //recycler view data...
        recyclerView = findViewById(R.id.re_view);
//firebase................................................
        database = FirebaseDatabase.getInstance().getReference("category");
        recyclerView.setHasFixedSize(true);
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));
      //  MyReAdapter myReAdapter=new MyReAdapter(list,this);
     //   recyclerView.setAdapter(myReAdapter);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelCustom modelCustom = dataSnapshot.getValue(ModelCustom.class);
                    list.add(modelCustom);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}