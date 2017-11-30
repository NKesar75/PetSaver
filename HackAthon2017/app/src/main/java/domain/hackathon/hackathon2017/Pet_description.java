package domain.hackathon.hackathon2017;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Pet_description extends AppCompatActivity {

    private static final String TAG = "Pet_description";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private boolean isitinthedaatabase = false;
    private boolean databaseisflase = false;
    FirebaseUser user = mAuth.getCurrentUser();
    String userID = user.getUid();

    private EditText Pet_name;
    private EditText Pet_age;
    private EditText Pet_gender;
    private EditText Pet_type;
    private EditText Pet_size;
    private EditText Pet_breed;
    private ImageView Pet_image;
    private Button Shelterinfobtn;
    long amountofchildren = 0;

    public static PetInfo petdescinfo;
    public static String shelterid;


    private String urlbase = "http://api.petfinder.com/"; //base url
    private String urlkey = "key=58fe2e272bebddbc0f5e66901f239055"; //key for api
    private String urlmethodfindmuiltplerecords = "pet.get?"; //used for getting a random pet
    private int offestformuiltplerecords = 0; //used to get more records if they want to keep looking
    private String urlargforpetrecord = ""; //argumentpassedintoparmaert
    private String urlShelter = "http://api.petfinder.com/shelter.get?key=58fe2e272bebddbc0f5e66901f239055&id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_description);
        Pet_image = (ImageView)findViewById(R.id.PD_IMAGe);
        Pet_name   = (EditText)findViewById(R.id.Name_pd);
        Pet_age    = (EditText)findViewById(R.id.Age_pd);
        Pet_gender = (EditText)findViewById(R.id.Gender_pd);
        Pet_type   = (EditText)findViewById(R.id.Type_pd);
        Pet_size   = (EditText)findViewById(R.id.Size_pd);
        Pet_breed  = (EditText)findViewById(R.id.Breed_pd);
        Shelterinfobtn = (Button)findViewById(R.id.Shelter_Info);
        urlargforpetrecord += "&id="+ Home.petNumber;
        HandlexmlDesc petObj = new HandlexmlDesc(urlbase + urlmethodfindmuiltplerecords + urlkey + urlargforpetrecord);
        petObj.FetchXml();
        while(petObj.parsingcomplete);
        shelterid = petdescinfo.getShelterid();
        Pet_name.setText("NAME: "+ petdescinfo.getAnimalname());
        Pet_age.setText("AGE: "+ petdescinfo.getAge());
        Pet_gender.setText("GENDER: "+ petdescinfo.getGender());
        Pet_type.setText("TYPE: "+ petdescinfo.getAnimaltype());
        Pet_size.setText("SIZE: "+ petdescinfo.getSize());
        Pet_breed.setText("BREED: "+ petdescinfo.getBreed());
        Glide.with(Pet_description.this).load(petdescinfo.getImageid().toString()).into(Pet_image);

        Shelterinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Pet_description.this,ShelterInfo.class));
            }
        });

        Pet_image.setFocusable(true);
        Pet_image.setFocusableInTouchMode(true);
        Pet_image.requestFocus();

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
                amountofchildren = dataSnapshot.child(userID).child("Favs").getChildrenCount();
                FirebaseUser user = mAuth.getCurrentUser();
                String USerid = user.getUid();
                int index = 1;
                int id = 0;
                boolean isitstillfav = false;
                isitinthedaatabase = false;
                for (int i = 0; i <amountofchildren; i++) {
                    if(dataSnapshot.child(USerid).child("Favs").child("Fav" + (index)).child("Id").getValue(int.class) != null)
                    id = dataSnapshot.child(USerid).child("Favs").child("Fav" + (index)).child("Id").getValue(int.class).intValue();
                    if(dataSnapshot.child(USerid).child("Favs").child("Fav" + (index)).child("FavOrNot").getValue(boolean.class) != null)
                    isitstillfav = dataSnapshot.child(USerid).child("Favs").child("Fav" + (index)).child("FavOrNot").getValue(boolean.class).booleanValue();
                    if (id == Home.petNumber ){
                        if (isitstillfav == true){
                            databaseisflase = true;
                        }
                        isitinthedaatabase = true;
                    }
                    index++;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fav_btn:
                if (isitinthedaatabase == false && databaseisflase == false) {
                    myRef.child(userID).child("Favs").child("Fav" + (amountofchildren + 1)).child("Id").setValue(Home.petNumber);
                    myRef.child(userID).child("Favs").child("Fav" + (amountofchildren + 1)).child("FavOrNot").setValue(true);
                    Toast.makeText(Pet_description.this, "Added to Favorites!!!",
                            Toast.LENGTH_SHORT).show();
                    break;
                }else if (isitinthedaatabase == true && databaseisflase == false){
                    myRef.child(userID).child("Favs").child("Fav" + (amountofchildren + 1)).child("FavOrNot").setValue(true);
                    Toast.makeText(Pet_description.this, "Added to Favorites!!!",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Pet_description.this, "Already added to favs",
                            Toast.LENGTH_SHORT).show();
                }
        }

        if(item.getItemId()==android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
