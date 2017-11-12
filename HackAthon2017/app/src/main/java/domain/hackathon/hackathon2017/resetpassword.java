package domain.hackathon.hackathon2017;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class resetpassword extends AppCompatActivity {

    private static final String TAG = "resetpassword";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private Button SendResetemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        final EditText Emailaccounttoreset = (EditText)findViewById(R.id.EmailACCountReset);
        SendResetemail = (Button)findViewById(R.id.SendREset);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG,"Logged in");
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

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


        Emailaccounttoreset.setFocusable(true);
        Emailaccounttoreset.setFocusableInTouchMode(true);///add this line
        Emailaccounttoreset.requestFocus();


        SendResetemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String REmail = Emailaccounttoreset.getText().toString();


                if (!REmail.equals("")){
                    mAuth.sendPasswordResetEmail(REmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "Email sent.");

                                        Intent changepage2 = new Intent(resetpassword.this, Login.class);
                                        startActivity(changepage2);
                                        Toast.makeText(resetpassword.this, "Email Sent", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }else{
                    Emailaccounttoreset.setError("Please Provide Email");
                    Emailaccounttoreset.requestFocus();
                }

            }

        });


    }
}
