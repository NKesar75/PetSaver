package domain.hackathon.hackathon2017;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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

import android.Manifest;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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

    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    Double lon;
    Double lat;
    String city;
    String state;
    String Zipcode;
    Geocoder geocoder;
    List<Address> addressList;

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
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    // User is signed in
                    startActivity(new Intent(Login.this,Home.class));

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
                                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                                getLocation();

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
                                                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                                                    getLocation();
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
                                                    myRef.child(userID).child("Search").child("Zipcodetxt").setValue(Zipcode.toString());
                                                    myRef.child(userID).child("Search").child("Citytxt").setValue(city.toString());
                                                    myRef.child(userID).child("Search").child("Statetxt").setValue(state.toString());

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

    void getLocation()
    {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        }
        else
        {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location != null)
            {
                lat = location.getLatitude();
                lon = location.getLongitude();

                geocoder = new Geocoder(this, Locale.getDefault());
                try
                {
                    addressList = geocoder.getFromLocation(lat,lon,1);
                    city = addressList.get(0).getLocality();
                    Zipcode = addressList.get(0).getPostalCode();
                    String x = addressList.get(0).getAdminArea();
                    switch(x)
                    {
                        case"Alabama":
                            x="AL";
                            break;

                        case"Alaska":
                            x="AK";
                            break;

                        case"Arizona":
                            x="AZ";
                            break;

                        case"Arkansas":
                            x="AR";
                            break;

                        case"California":
                            x="CA";
                            break;

                        case"Colorado":
                            x="CO";
                            break;

                        case"Connecticut":
                            x="CT";
                            break;

                        case"Delaware":
                            x="DE";
                            break;

                        case"District of Columbia":
                            x="DC";
                            break;

                        case"Florida":
                            x="FL";
                            break;

                        case"Georgia":
                            x="GA";
                            break;

                        case"Hawaii":
                            x="HI";
                            break;

                        case"Idaho":
                            x="ID";
                            break;

                        case"Illinois":
                            x="IL";
                            break;

                        case"Indiana":
                            x="IN";
                            break;

                        case"Iowa":
                            x="IA";
                            break;

                        case"Kansas":
                            x="KS";
                            break;

                        case"Kentucky":
                            x="KY";
                            break;

                        case"Louisiana":
                            x="LA";
                            break;

                        case"Maine":
                            x="ME";
                            break;

                        case"Maryland":
                            x="MD";
                            break;

                        case"Massachusetts":
                            x="MA";
                            break;

                        case"Michigan":
                            x="MI";
                            break;

                        case"Minnesota":
                            x="MN";
                            break;

                        case"Mississippi":
                            x="MS";
                            break;

                        case"Missouri":
                            x="MO";
                            break;

                        case"Montana":
                            x="MT";
                            break;

                        case"Nebraska":
                            x="NE";
                            break;

                        case"Nevada":
                            x="NV";
                            break;

                        case"New Hampshire":
                            x="NH";
                            break;

                        case"New Jersey":
                            x="NJ";
                            break;

                        case"New Mexico":
                            x="NM";
                            break;

                        case"New York":
                            x="NY";
                            break;

                        case"North Carolina":
                            x="NC";
                            break;

                        case"North Dakota":
                            x="ND";
                            break;

                        case"Ohio":
                            x="OH";
                            break;

                        case"Oklahoma":
                            x="OK";
                            break;

                        case"Oregon":
                            x="OR";
                            break;

                        case"Pennsylvania":
                            x="PA";
                            break;


                        case"Rhode Island":
                            x="RI";
                            break;

                        case"South Carolina":
                            x="SC";
                            break;

                        case"South Dakota":
                            x="SD";
                            break;

                        case"Tennessee":
                            x="TN";
                            break;

                        case"Texas":
                            x="TX";
                            break;

                        case"Utah":
                            x="UT";
                            break;

                        case"Vermont":
                            x="VT";
                            break;

                        case"Virginia":
                            x="VA";
                            break;

                        case"Washington":
                            x="WA";
                            break;

                        case"West Virginia":
                            x="WV";
                            break;

                        case"Wisconsin":
                            x="WI";
                            break;

                        case"Wyoming":
                            x="WY";
                            break;

                        default:
                            break;
                    }
                    state = x;

                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }

}
