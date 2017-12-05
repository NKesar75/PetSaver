package domain.hackathon.hackathon2017;

import android.content.Intent;
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

public class Fav_Desc extends AppCompatActivity {

    private static final String TAG = "Fav_Desc";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private int isitinthedaatabase = 0;
    FirebaseUser user = mAuth.getCurrentUser();
    String userID = user.getUid();
    long amountofchildren = 0;
    private EditText Pet_name;
    private EditText Pet_age;
    private EditText Pet_gender;
    private EditText Pet_type;
    private EditText Pet_size;
    private EditText Pet_breed;
    private ImageView Pet_image;
    private Button Shelterinfobtn;
    public static String shelterid;
    public static PetInfo petdescinfo;
    private String urlbase = "http://api.petfinder.com/"; //base url
    private String urlkey = "key=58fe2e272bebddbc0f5e66901f239055"; //key for api
    private String urlmethodfindmuiltplerecords = "pet.get?"; //used for getting a random pet
    private String urlargforpetrecord = ""; //argumentpassedintoparmaert
    private String urlShelter = "http://api.petfinder.com/shelter.get?key=58fe2e272bebddbc0f5e66901f239055&id=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav__desc);

        Pet_image = (ImageView)findViewById(R.id.image_fav);
        Pet_name   = (EditText)findViewById(R.id.Name_fav);
        Pet_age    = (EditText)findViewById(R.id.Age_fav);
        Pet_gender = (EditText)findViewById(R.id.Gender_fav);
        Pet_type   = (EditText)findViewById(R.id.Type_fav);
        Pet_size   = (EditText)findViewById(R.id.Size_fav);
        Pet_breed  = (EditText)findViewById(R.id.Breed_fav);
        Shelterinfobtn = (Button)findViewById(R.id.Shelter_Info_fav);
        urlargforpetrecord = "&id="+ Favorite.petNumber1;
        HandlexmlFavDesc petObj = new HandlexmlFavDesc(urlbase + urlmethodfindmuiltplerecords + urlkey + urlargforpetrecord);
        petObj.FetchXml();
        while(petObj.parsingcomplete);
        shelterid = petdescinfo.getShelterid();
        Pet_name.setText("NAME: "+ petdescinfo.getAnimalname());
        Pet_age.setText("AGE: "+ petdescinfo.getAge());
        Pet_gender.setText("GENDER: "+ petdescinfo.getGender());
        Pet_type.setText("TYPE: "+ petdescinfo.getAnimaltype());
        Pet_size.setText("SIZE: "+ petdescinfo.getSize());
        Pet_breed.setText("BREED: "+ petdescinfo.getBreed());
        Glide.with(Fav_Desc.this).load(petdescinfo.getImageid().toString()).into(Pet_image);

        Pet_image.setFocusable(true);
        Pet_image.setFocusableInTouchMode(true);
        Pet_image.requestFocus();

        Shelterinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Fav_Desc.this,Fav_ShelterInfo.class));
            }
        });

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
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
                isitinthedaatabase = 0;
                for (int i = 0; i <amountofchildren; i++) {
                    if(dataSnapshot.child(USerid).child("Favs").child("Fav" + (index)).child("Id").getValue(int.class) != null) {
                        id = dataSnapshot.child(USerid).child("Favs").child("Fav" + index).child("Id").getValue(int.class).intValue();
                    }

                    if (id == Favorite.petNumber1){
                        isitinthedaatabase = index;
                       break;
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

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.unfav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fav_btn:
                
                   myRef.child(userID).child("Favs").child("Fav" + (isitinthedaatabase)).child("FavOrNot").setValue(false);
                    Toast.makeText(this, "Removed From Favorites!!!",
                            Toast.LENGTH_SHORT).show();
               break;

        }

        if(item.getItemId()==android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
