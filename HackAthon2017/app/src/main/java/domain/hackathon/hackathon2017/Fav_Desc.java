package domain.hackathon.hackathon2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fav_Desc extends AppCompatActivity {

    private static final String TAG = "Fav_Desc";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
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

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)

            finish();

        return super.onOptionsItemSelected(item);
    }
}
