package domain.hackathon.hackathon2017;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private Button loginbuttonanimation;
    private Button registerbuttonanimation;
    private TextView FGBtext;
    private TextView Emailfun;
    private TextView Passwordfin;
    private Button loginbutton;
    private TextView registeremail;
    private TextView passregular;
    private TextView passwordcheck;
    private TextView regd;
    private CheckBox CAPA;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static String Email, Pass, Repass;

    private static final String TAG = "Login";
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginbuttonanimation = (Button) findViewById(R.id.Login_Button);
        registerbuttonanimation = (Button) findViewById(R.id.Register_Button);
        loginbutton = (Button) findViewById(R.id.loginsubmit_Button);

        FGBtext = (TextView) findViewById(R.id.FGB_Text);
        Emailfun = (TextView) findViewById(R.id.email_email);
        Passwordfin = (TextView) findViewById(R.id.password_password);
        registeremail = (TextView) findViewById(R.id.registeremail_email);
        passregular = (TextView) findViewById(R.id.registerpassword_password);
        passwordcheck = (TextView) findViewById(R.id.password_pass);
        regd = (TextView) findViewById(R.id.reg_button);

        CAPA = (CheckBox) findViewById(R.id.CAPA_Check);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    startActivity(new Intent(Login.this,Home.class));
                    Toast.makeText(Login.this, "Successfully logged in",
                            Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"Logged in");
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Mail = Emailfun.getText().toString();
                String Pass = Passwordfin.getText().toString();

                if (!Mail.equals("") && !Pass.equals("")) {
                    if (!Mail.contains(" ")) {
                        mAuth.signInWithEmailAndPassword(Mail, Pass).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                FirebaseUser user = mAuth.getCurrentUser();
                                String userID = user.getUid();
                                myRef.child(userID).child("Search").child("Citytxt").setValue("");
                                myRef.child(userID).child("Search").child("Statetxt").setValue("");
                                myRef.child(userID).child("Search").child("Zipcodetxt").setValue("");
                                
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                                    Emailfun.setError("Email or Password is invalid");
                                    Emailfun.requestFocus();

                                }
                            }


                        });
                    }
                    else {
                        Emailfun.setError("Email can not contain spaces");
                        Emailfun.requestFocus();
                    }
                }
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        loginbuttonanimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginbuttonanimation.setVisibility(View.GONE);

                registerbuttonanimation.setVisibility(View.GONE);
                FGBtext.setVisibility(View.VISIBLE);
                Emailfun.setVisibility((View.VISIBLE));
                Passwordfin.setVisibility((View.VISIBLE));
                loginbutton.setVisibility((View.VISIBLE));
            }
        });
        regd.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Email = registeremail.getText().toString();
                Pass = passregular.getText().toString();
                Repass = passwordcheck.getText().toString();

                if (Pass.length() >= 8) {
                    if (!Email.equals("") && !Pass.equals("") && !Repass.equals("")) {
                        if (Pass.equals(Repass)) {
                            if (!Email.equals(" ")) {
                                mAuth.createUserWithEmailAndPassword(Email, Pass)
                                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {

                                                    FirebaseUser user = mAuth.getCurrentUser();
                                                    String userID = user.getUid();
                                                    myRef.child(userID).child("Search").child("breedcb").setValue("false");
                                                    myRef.child(userID).child("Search").child("breedtext").setValue("");
                                                    myRef.child(userID).child("Search").child("animaltypecb").setValue("false");
                                                    myRef.child(userID).child("Search").child("animaltypetext").setValue("barnyard");
                                                    myRef.child(userID).child("Search").child("gendercb").setValue("false");
                                                    myRef.child(userID).child("Search").child("gendertxt").setValue("F");
                                                    myRef.child(userID).child("Search").child("agecb").setValue("false");
                                                    myRef.child(userID).child("Search").child("agetxt").setValue("Baby");
                                                    myRef.child(userID).child("Search").child("sizecb").setValue("false");
                                                    myRef.child(userID).child("Search").child("sizetxt").setValue("S");
                                                    myRef.child(userID).child("Search").child("locationcb").setValue("false");
                                                    myRef.child(userID).child("Search").child("locationrb").setValue("false");
                                                    myRef.child(userID).child("Search").child("Citytxt").setValue("");
                                                    myRef.child(userID).child("Search").child("Statetxt").setValue("");
                                                    myRef.child(userID).child("Search").child("Zipcodetxt").setValue("");

                                                    Intent changepage = new Intent(Login.this, Home.class);
                                                    startActivity(changepage);
                                                } else {
                                                    registeremail.setError("Account already exists");
                                                    registeremail.requestFocus();
                                                }

                                                // ...
                                            }

                                        });
                            } else {
                                registeremail.setError("Email cannot contain a space");
                                registeremail.requestFocus();
                            }
                        } else {
                            passregular.setError(("Password's do not match"));
                            passregular.requestFocus();
                        }
                    } else {
                        registeremail.setError("Missing some info");
                        registeremail.requestFocus();
                    }
                } else {
                    passregular.setError("Password must be atleast 8 characters");
                    passregular.requestFocus();
                }
            }
        }));

        registerbuttonanimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginbuttonanimation.setVisibility(View.GONE);
                registerbuttonanimation.setVisibility(View.GONE);
                registeremail.setVisibility(((View.VISIBLE)));
                passregular.setVisibility((View.VISIBLE));
                passwordcheck.setVisibility((View.VISIBLE));
                regd.setVisibility((View.VISIBLE));
                CAPA.setVisibility((View.VISIBLE));

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}

