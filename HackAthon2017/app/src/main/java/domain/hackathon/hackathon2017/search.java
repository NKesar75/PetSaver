package domain.hackathon.hackathon2017;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class search extends AppCompatActivity {

    private static final String TAG = "search";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String USerid = user.getUid();

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mbreed = mRootRef.child(USerid).child("Search").child("breedcb");
    DatabaseReference mbreedtext = mRootRef.child(USerid).child("Search").child("breedtext");
    DatabaseReference manimaltype = mRootRef.child(USerid).child("Search").child("animaltypecb");
    DatabaseReference manimaltypetext = mRootRef.child(USerid).child("Search").child("animaltypetext");
    DatabaseReference mgender = mRootRef.child(USerid).child("Search").child("gendercb");
    DatabaseReference mgendertxt = mRootRef.child(USerid).child("Search").child("gendertxt");
    DatabaseReference mage = mRootRef.child(USerid).child("Search").child("agecb");
    DatabaseReference magetxt = mRootRef.child(USerid).child("Search").child("agetxt");
    DatabaseReference msize = mRootRef.child(USerid).child("Search").child("sizecb");
    DatabaseReference msizetxt = mRootRef.child(USerid).child("Search").child("sizetxt");
    DatabaseReference mlocation = mRootRef.child(USerid).child("Search").child("locationcb");
    DatabaseReference mlocationrandiobutton = mRootRef.child(USerid).child("Search").child("locationrb");
    DatabaseReference mlocationtxt = mRootRef.child(USerid).child("Search").child("locationtxt");

    private CheckBox breed;
    private CheckBox animaltype;
    private CheckBox gender;
    private CheckBox age;
    private CheckBox size;
    private CheckBox local;
    private RadioButton citystate;
    private RadioButton Zipcode;
    private EditText breedtxt;
    private EditText localtxt;
    private Spinner Agespinner;
    private Spinner Sizespinner;
    private Spinner Genderspinner;
    private Spinner animaltypespinner;
    private Button Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        breed = (CheckBox) findViewById(R.id.Breed_CB);
        animaltype = (CheckBox) findViewById(R.id.Animal_Type_CB);
        gender = (CheckBox) findViewById(R.id.Gender_CB);
        age = (CheckBox) findViewById(R.id.Age_CB);
        size = (CheckBox) findViewById(R.id.Size_CB);
        local = (CheckBox) findViewById(R.id.Location_CB);

        citystate = (RadioButton) findViewById(R.id.CAS_RB);
        Zipcode = (RadioButton) findViewById(R.id.ZIPCODE_RB);

        breedtxt = (EditText) findViewById(R.id.Breed_Search);
        localtxt = (EditText) findViewById(R.id.Location_Txt);

        Search = (Button)findViewById(R.id.Search_BTN);

        Agespinner = (Spinner) findViewById(R.id.Age_SP);
        ArrayAdapter<String> Sort1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ageforsearch));
        Sort1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Agespinner.setAdapter(Sort1);

        Genderspinner = (Spinner) findViewById(R.id.Gender_SP);
        ArrayAdapter<String> Sort2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Genderforsearch));
        Sort2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Genderspinner.setAdapter(Sort2);

        Sizespinner = (Spinner) findViewById(R.id.SIZE_SP);
        ArrayAdapter<String> Sort3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Sizeforsearch));
        Sort3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Sizespinner.setAdapter(Sort3);

        animaltypespinner = (Spinner) findViewById(R.id.Animal_Type_SP);
        ArrayAdapter<String> Sort4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.animaltypearray));
        Sort4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        animaltypespinner.setAdapter(Sort4);


        breed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (breed.isChecked() == true){
                    breedtxt.setVisibility(View.VISIBLE);
                }
                else{
                    breedtxt.setVisibility(View.GONE);
                }

            }
        });
        animaltype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animaltype.isChecked() == true){
                    animaltypespinner.setVisibility(View.VISIBLE);
                }
                else{
                    animaltypespinner.setVisibility(View.GONE);
                }

            }
        });
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gender.isChecked() == true){
                    Genderspinner.setVisibility(View.VISIBLE);
                }
                else{
                    Genderspinner.setVisibility(View.GONE);
                }
            }
        });
        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (age.isChecked() == true){
                    Agespinner.setVisibility(View.VISIBLE);
                }
                else{
                    Agespinner.setVisibility(View.GONE);
                }
            }
        });
        size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (size.isChecked() == true){
                    Sizespinner.setVisibility(View.VISIBLE);
                }
                else{
                    Sizespinner.setVisibility(View.GONE);
                }
            }
        });
        local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (local.isChecked() == true){
                    localtxt.setVisibility(View.VISIBLE);
                }
                else{
                    localtxt.setVisibility(View.GONE);
                }
            }
        });

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
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
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                shoData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean breedfb = breed.isChecked();
                boolean animaltypefb = animaltype.isChecked();
                boolean genderfb = gender.isChecked();
                boolean agefb = age.isChecked();
                boolean sizefb = size.isChecked();
                boolean localfb = local.isChecked();
                boolean citystatefb =  citystate.isChecked();

                String breedtxtfb =  breedtxt.getText().toString();
                String localtxtfb = localtxt.getText().toString();

                String agetxtfb =  Agespinner.getSelectedItem().toString();
                String sizetxtfb = Sizespinner.getSelectedItem().toString();
                String gendertxtfb = Genderspinner.getSelectedItem().toString();
                String animaltypetxtfb = animaltypespinner.getSelectedItem().toString();

                switch(sizetxtfb){
                    case "Small":
                        sizetxtfb = "S";
                        break;
                    case "Medium":
                        sizetxtfb = "M";
                        break;
                    case "Large":
                        sizetxtfb = "L";
                        break;
                    case "Extra Large":
                        sizetxtfb = "XL";
                        break;
                    default:
                        break;
                }

                switch(animaltypetxtfb){
                    case "Barnyard":
                        animaltypetxtfb = "barnyard";
                        break;
                    case "Bird":
                        animaltypetxtfb = "bird";
                        break;
                    case "Cat":
                        animaltypetxtfb = "cat";
                        break;
                    case "Dog":
                        animaltypetxtfb = "dog";
                        break;
                    case "Horse":
                        animaltypetxtfb = "horse";
                        break;
                    case "Reptile":
                        animaltypetxtfb = "reptile";
                        break;
                    case "Small Furry":
                        animaltypetxtfb = "smallfurry";
                        break;
                    default:
                        break;
                }

                switch(gendertxtfb){
                    case "Male":
                        gendertxtfb = "M";
                        break;
                    case "Female":
                        gendertxtfb = "F";
                        break;
                    default:
                        break;
                }

                mRootRef.child(USerid).child("Search").child("breedcb").setValue(breedfb);
                mRootRef.child(USerid).child("Search").child("breedtext").setValue(breedtxtfb);
                mRootRef.child(USerid).child("Search").child("animaltypecb").setValue(animaltypefb);
                mRootRef.child(USerid).child("Search").child("animaltypetext").setValue(animaltypetxtfb);
                mRootRef.child(USerid).child("Search").child("gendercb").setValue(genderfb);
                mRootRef.child(USerid).child("Search").child("gendertxt").setValue(gendertxtfb);
                mRootRef.child(USerid).child("Search").child("agecb").setValue(agefb);
                mRootRef.child(USerid).child("Search").child("agetxt").setValue(agetxtfb);
                mRootRef.child(USerid).child("Search").child("sizecb").setValue(sizefb);
                mRootRef.child(USerid).child("Search").child("sizetxt").setValue(sizetxtfb);
                mRootRef.child(USerid).child("Search").child("locationcb").setValue(localfb);
                mRootRef.child(USerid).child("Search").child("locationrb").setValue(citystatefb);
                mRootRef.child(USerid).child("Search").child("locationtxt").setValue(localtxtfb);

                startActivity(new Intent(this,Home.class));
            }
        });

    }

    private void shoData(DataSnapshot dataSnapshot) {

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
