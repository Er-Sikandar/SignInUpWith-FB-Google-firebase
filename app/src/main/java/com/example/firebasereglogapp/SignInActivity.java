package com.example.firebasereglogapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebasereglogapp.model.ModelCustom;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.Collection;

import static com.example.firebasereglogapp.SignUpActivity.checkFields;

public class SignInActivity extends AppCompatActivity {
    EditText email, password;
    private static boolean valids = true;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore =FirebaseFirestore.getInstance();
    private ProgressDialog dialog;

    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
        if (currentUser != null) {
          // startActivity(new Intent(SignInActivity.this,MainActivity.class));
          // finish();
        } else {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        dialog = new ProgressDialog(this);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFields(email);
                checkFields(password);
                if (valids) {
                    if (password.getText().toString().trim().length() < 8) {
                        password.setError("Min Password Length !");
                        password.requestFocus();
                    } else {
                        dialog.setMessage("Loging User&Admin..");
                        dialog.show();
                        firebaseAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                                .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = firebaseAuth.getCurrentUser();
                                            updateUI(user);
                                            DocumentReference df=firestore.collection("AllDatas").document(user.getUid());
                                            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    dialog.dismiss();
                                                    Log.e(" data ","profile "+documentSnapshot.getData());
                                                    if (documentSnapshot.getString("userType").equalsIgnoreCase("1")){
                                                        Log.e("user data ","user ");
                                                        startActivity(new Intent(SignInActivity.this, UserProfileActivity.class));
                                                        finish();
                                                    }else if (documentSnapshot.getString("userType").equalsIgnoreCase("3")){
                                                        Log.e("admin data ","Admin ");
                                                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                                        finish();
                                                    }else {
                                                        Toast.makeText(SignInActivity.this, "Please SignUp !"+documentSnapshot.getString("userType"), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignInActivity.this, "Failed AUTH !", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
        findViewById(R.id.btn_createAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        // google login...........
        //   GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        //        .requestIdToken(getString(R.string.default_web_client_id))
        //          .requestEmail()
        //         .build();
        //  mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.img_google).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task.isSuccessful()) {
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                    handleSignInResult(task);
                } catch (ApiException e) {
                    Log.w(" failed", "Google sign in failed", e);
                }
            } else {
                Log.d("TAG", "firebaseAuthWithGoogle: failed !");
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }

                    }
                });
    }

    private void updateUI(FirebaseUser user) {

    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount acc = task.getResult(ApiException.class);
            Toast.makeText(getApplicationContext(), "Signing Success", Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {
            Toast.makeText(getApplicationContext(), "Signing FAiled", Toast.LENGTH_SHORT).show();
        }
    }
private void chechUserOrAdmin(String uid){
    DocumentReference df=firestore.collection("AllDatas").document(uid);
    df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
        @Override
        public void onSuccess(DocumentSnapshot documentSnapshot) {
Log.e("user data ",": "+documentSnapshot.getData());
if (documentSnapshot.getString("userType")=="1"){
    Log.e("user data ","user ");

}else if (documentSnapshot.getString("userType")=="3"){
    Log.e("user data ","Admin ");

}else {
    Log.e("user data ","esle ");

}
        }
    });
}

}