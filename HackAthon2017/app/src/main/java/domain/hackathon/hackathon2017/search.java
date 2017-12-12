package domain.hackathon.hackathon2017;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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

public class search extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "search";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String USerid = user.getUid();
    public static boolean searcherror;

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
    DatabaseReference mZipcodetxt = mRootRef.child(USerid).child("Search").child("Zipcodetxt");
    DatabaseReference mCitytxt = mRootRef.child(USerid).child("Search").child("Citytxt");
    DatabaseReference mSatatetxt = mRootRef.child(USerid).child("Search").child("Statetxt");

    private CheckBox breed;
    private CheckBox animaltype;
    private CheckBox gender;
    private CheckBox age;
    private CheckBox size;
    private CheckBox local;
    private RadioButton citystate;
    private RadioButton Zipcode;
    private EditText breedtxt;
    private EditText ZipcodetxtED;
    private EditText Citytxt;
    private EditText Statetxt;
    private Spinner Agespinner;
    private Spinner Sizespinner;
    private Spinner Genderspinner;
    private Spinner animaltypespinner;
    private Button Search;

    private DrawerLayout draw;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searcherror = true;
        breed = (CheckBox) findViewById(R.id.Breed_CB);
        animaltype = (CheckBox) findViewById(R.id.Animal_Type_CB);
        gender = (CheckBox) findViewById(R.id.Gender_CB);
        age = (CheckBox) findViewById(R.id.Age_CB);
        size = (CheckBox) findViewById(R.id.Size_CB);
        local = (CheckBox) findViewById(R.id.Location_CB);

        citystate = (RadioButton) findViewById(R.id.CAS_RB);
        Zipcode = (RadioButton) findViewById(R.id.ZIPCODE_RB);

        breedtxt = (EditText) findViewById(R.id.Breed_Search);
        Citytxt = (EditText) findViewById(R.id.City_Txt);
        Statetxt = (EditText) findViewById(R.id.State_Txt);
        ZipcodetxtED = (EditText) findViewById(R.id.Zipcode_Txt);

        Search = (Button) findViewById(R.id.Search_BTN);

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

                if (breed.isChecked() == true) {
                    breedtxt.setVisibility(View.VISIBLE);
                } else {
                    breedtxt.setVisibility(View.GONE);
                }

            }
        });
        animaltype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animaltype.isChecked() == true) {
                    animaltypespinner.setVisibility(View.VISIBLE);
                } else {
                    animaltypespinner.setVisibility(View.GONE);
                }

            }
        });
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gender.isChecked() == true) {
                    Genderspinner.setVisibility(View.VISIBLE);
                } else {
                    Genderspinner.setVisibility(View.GONE);
                }
            }
        });
        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (age.isChecked() == true) {
                    Agespinner.setVisibility(View.VISIBLE);
                } else {
                    Agespinner.setVisibility(View.GONE);
                }
            }
        });
        size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (size.isChecked() == true) {
                    Sizespinner.setVisibility(View.VISIBLE);
                } else {
                    Sizespinner.setVisibility(View.GONE);
                }
            }
        });

        citystate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (citystate.isChecked()) {
                    ZipcodetxtED.setVisibility(View.GONE);
                    Citytxt.setVisibility(View.VISIBLE);
                    Statetxt.setVisibility(View.VISIBLE);
                } else {
                    ZipcodetxtED.setVisibility(View.VISIBLE);
                    Citytxt.setVisibility(View.GONE);
                    Statetxt.setVisibility(View.GONE);
                }
            }
        });
        Zipcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Zipcode.isChecked()) {
                    ZipcodetxtED.setVisibility(View.VISIBLE);
                    Citytxt.setVisibility(View.GONE);
                    Statetxt.setVisibility(View.GONE);
                } else {
                    ZipcodetxtED.setVisibility(View.GONE);
                    Citytxt.setVisibility(View.VISIBLE);
                    Statetxt.setVisibility(View.VISIBLE);
                }

            }
        });
        local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (local.isChecked()) {
                    Search.setFocusable(true);
                    Search.setFocusableInTouchMode(true);
                    Search.requestFocus();
                    Zipcode.setVisibility(View.VISIBLE);
                    citystate.setVisibility(View.VISIBLE);
                    if (Zipcode.isChecked()) {
                        ZipcodetxtED.setVisibility(View.VISIBLE);
                        Citytxt.setVisibility(View.GONE);
                        Statetxt.setVisibility(View.GONE);
                    } else {
                        ZipcodetxtED.setVisibility(View.GONE);
                        Citytxt.setVisibility(View.VISIBLE);
                        Statetxt.setVisibility(View.VISIBLE);
                    }

                } else {
                    breed.setFocusable(true);
                    breed.setFocusableInTouchMode(true);
                    breed.requestFocus();
                    Zipcode.setVisibility(View.GONE);
                    citystate.setVisibility(View.GONE);
                    ZipcodetxtED.setVisibility(View.GONE);
                    Citytxt.setVisibility(View.GONE);
                    Statetxt.setVisibility(View.GONE);
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
                boolean citystatefb = citystate.isChecked();

                String breedtxtfb = breedtxt.getText().toString();
                String Zipcodelocaltext = ZipcodetxtED.getText().toString();
                String Citylocaltext = Citytxt.getText().toString();
                String Statelocaltext = Statetxt.getText().toString();

                String agetxtfb = Agespinner.getSelectedItem().toString();
                String sizetxtfb = Sizespinner.getSelectedItem().toString();
                String gendertxtfb = Genderspinner.getSelectedItem().toString();
                String animaltypetxtfb = animaltypespinner.getSelectedItem().toString();

                switch (sizetxtfb) {
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

                switch (animaltypetxtfb) {
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

                switch (gendertxtfb) {
                    case "Male":
                        gendertxtfb = "M";
                        break;
                    case "Female":
                        gendertxtfb = "F";
                        break;
                    default:
                        break;
                }

                int index = 0;
                String[] parts = breedtxtfb.split(" ");
                breedtxtfb = "";
                String temp;
                if (parts.length == 1) {
                    temp  = parts[index].substring(0, 1).toUpperCase();
                    breedtxtfb = temp + parts[index].substring(1).toLowerCase();
                } else {

                    while (index != parts.length) {
                        if (index == 0) {

                            temp = parts[index].substring(0, 1).toUpperCase();
                            breedtxtfb = temp + parts[index].substring(1).toLowerCase();

                        } else {

                            temp = parts[index].substring(0, 1).toUpperCase();
                            breedtxtfb += " " + temp + parts[index].substring(1).toLowerCase();
                        }
                        index++;
                    }
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
                mRootRef.child(USerid).child("Search").child("Zipcodetxt").setValue(Zipcodelocaltext);
                mRootRef.child(USerid).child("Search").child("Citytxt").setValue(Citylocaltext);
                mRootRef.child(USerid).child("Search").child("Statetxt").setValue(Statelocaltext);

                finish();
                startActivity(new Intent(search.this, Home.class));
                Home.offestformuiltplerecords = 0;
            }
        });

        draw = (DrawerLayout) findViewById(R.id.activity_search);
        toggle = new ActionBarDrawerToggle(this, draw, R.string.open, R.string.close);
        draw.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigation = (NavigationView) findViewById(R.id.nav_view);
        navigation.setNavigationItemSelectedListener(this);

    }

    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        mbreed.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean Temp = dataSnapshot.getValue(boolean.class);

                if (Temp) {//.equals("true")) {
                    //breedtxt.setText(Temp);
                    breed.setChecked(true);
                    breedtxt.setVisibility(View.VISIBLE);
                } else if (!Temp) {//.equals("false")) {
                    //breedtxt.setText(Temp);
                    breed.setChecked(false);
                    breedtxt.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        manimaltype.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean Temp = dataSnapshot.getValue(boolean.class);

                if (Temp) {//.equals("true")) {
                    animaltype.setChecked(true);
                    animaltypespinner.setVisibility(View.VISIBLE);
                } else if (!Temp) {//.equals("false")) {
                    animaltype.setChecked(false);
                    animaltypespinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mgender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean Temp = dataSnapshot.getValue(boolean.class);

                if (Temp) {//.equals("true")) {
                    gender.setChecked(true);
                    Genderspinner.setVisibility(View.VISIBLE);
                } else if (!Temp) {//.equals("false")) {
                    gender.setChecked(false);
                    Genderspinner.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean Temp = dataSnapshot.getValue(boolean.class);

                if (Temp) {//.equals("true")) {
                    age.setChecked(true);
                    Agespinner.setVisibility(View.VISIBLE);
                } else if (!Temp) {//.equals("false")) {
                    age.setChecked(false);
                    Agespinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        msize.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean Temp = dataSnapshot.getValue(boolean.class);

                if (Temp) {//.equals("true")) {
                    Sizespinner.setVisibility(View.VISIBLE);
                    size.setChecked(true);
                } else if (!Temp) {//.equals("false")) {
                    Sizespinner.setVisibility(View.GONE);
                    size.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mlocation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean Temp = dataSnapshot.getValue(boolean.class);

                if (Temp) {//.equals("true")) {
                    citystate.setVisibility(View.VISIBLE);
                    Zipcode.setVisibility(View.VISIBLE);
                    local.setChecked(true);
                } else if (!Temp) {//.equals("false")) {
                    local.setChecked(false);
                    Citytxt.setVisibility(View.GONE);
                    Statetxt.setVisibility(View.GONE);
                    ZipcodetxtED.setVisibility(View.GONE);
                    citystate.setVisibility(View.GONE);
                    Zipcode.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mCitytxt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Temp = dataSnapshot.getValue(String.class);
                Citytxt.setText(Temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mSatatetxt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Temp = dataSnapshot.getValue(String.class);
                Statetxt.setText(Temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        magetxt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Temp = dataSnapshot.getValue(String.class);
                switch (Temp) {
                    case "Baby":
                        Agespinner.setSelection(0);
                        break;
                    case "Young":
                        Agespinner.setSelection(1);
                        break;
                    case "Adult":
                        Agespinner.setSelection(2);
                        break;
                    case "Senior":
                        Agespinner.setSelection(3);
                        break;
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        msizetxt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Temp = dataSnapshot.getValue(String.class);
                switch (Temp) {
                    case "S":
                        Sizespinner.setSelection(0);
                        break;
                    case "M":
                        Sizespinner.setSelection(1);
                        break;
                    case "L":
                        Sizespinner.setSelection(2);
                        break;
                    case "XL":
                        Sizespinner.setSelection(3);
                        break;
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mgendertxt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Temp = dataSnapshot.getValue(String.class);
                switch (Temp) {
                    case "M":
                        Genderspinner.setSelection(0);
                        break;
                    case "F":
                        Genderspinner.setSelection(1);
                        break;
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        manimaltypetext.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Temp = dataSnapshot.getValue(String.class);

                switch (Temp) {
                    case "barnyard":
                        animaltypespinner.setSelection(0);
                        break;
                    case "bird":
                        animaltypespinner.setSelection(1);
                        break;
                    case "cat":
                        animaltypespinner.setSelection(2);
                        break;
                    case "dog":
                        animaltypespinner.setSelection(3);
                        break;
                    case "horse":
                        animaltypespinner.setSelection(4);
                        break;
                    case "reptile":
                        animaltypespinner.setSelection(5);
                        break;
                    case "smallfurry":
                        animaltypespinner.setSelection(6);
                        break;

                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mlocationrandiobutton.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean Temp = dataSnapshot.getValue(boolean.class);
                if (local.isChecked()) {
                    if (Temp) {//.equals("true")) {
                        citystate.setChecked(true);
                        Zipcode.setChecked(false);
                        Citytxt.setVisibility(View.VISIBLE);
                        Statetxt.setVisibility(View.VISIBLE);
                        ZipcodetxtED.setVisibility(View.GONE);
                    } else if (!Temp) {//.equals("false")) {
                        citystate.setChecked(false);
                        Zipcode.setChecked(true);
                        Citytxt.setVisibility(View.GONE);
                        Statetxt.setVisibility(View.GONE);
                        ZipcodetxtED.setVisibility(View.VISIBLE);
                    }
                } else {
                    Citytxt.setVisibility(View.GONE);
                    Statetxt.setVisibility(View.GONE);
                    ZipcodetxtED.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mZipcodetxt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Temp = dataSnapshot.getValue(String.class);
                ZipcodetxtED.setText(Temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mbreedtext.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Temp = dataSnapshot.getValue(String.class);
                breedtxt.setText(Temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                return true;
            case R.id.nav_Search:
                return true;
            case R.id.nav_favorite:
                return true;
            case R.id.nav_logout:
                return true;
            case R.id.nav_info:
                return true;
        }
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_search);
        if (drawerLayout.isDrawerOpen((GravityCompat.START)))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(search.this, Home.class));
        } else if (id == R.id.nav_Search) {
            DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_search);
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_favorite) {
            startActivity(new Intent(search.this, Favorite.class));
        } else if (id == R.id.nav_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(search.this);
            builder.setMessage("Are you Sure you want to Logout?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAuth.signOut();
                            startActivity(new Intent(search.this, Login.class));
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel", null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else if (id == R.id.nav_info) {
            startActivity(new Intent(search.this, info.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_search);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
