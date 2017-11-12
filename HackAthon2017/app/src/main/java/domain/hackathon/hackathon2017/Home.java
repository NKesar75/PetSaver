package domain.hackathon.hackathon2017;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.google.android.gms.gcm.PeriodicTask;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String urlbase = "http://api.petfinder.com/"; //base url
    private String urlkey = "key=58fe2e272bebddbc0f5e66901f239055"; //key for api
    private String urlmethodfindmuiltplerecords = "pet.find?"; //used for getting a random pet
    private int offestformuiltplerecords = 0; //used to get more records if they want to keep looking
    private String urlargforpetrecord = "&output=basic"; //argumentpassedintoparmaert
    private String urlShelter = "http://api.petfinder.com/shelter.get?key=58fe2e272bebddbc0f5e66901f239055&id=";

    private static final String TAG = "Home";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String USerid = user.getUid();
    private Handlexml petObj = new Handlexml(urlbase + urlmethodfindmuiltplerecords + urlkey + urlargforpetrecord);
    public static int petNumber;
    public static List<PetInfo> petList = new ArrayList<>();

    private ViewStub stubGrid;
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;


    private DrawerLayout draw;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        stubGrid = (ViewStub) findViewById(R.id.stub_grid);
        stubGrid.inflate();
        gridView = (GridView) findViewById(R.id.mygridview);
        gridView.setOnItemClickListener(onItemClick);

        draw = (DrawerLayout) findViewById(R.id.activity_home);
        toggle = new ActionBarDrawerToggle(this, draw, R.string.open, R.string.close);

        draw.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigation = (NavigationView) findViewById(R.id.nav_view);
        navigation.setNavigationItemSelectedListener(this);

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
                showdata(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void showdata(DataSnapshot dataSnapshot) {

        petList.clear();
        String breedfb;
        String animaltypefb;
        String genderfb;
        String agefb;
        String sizefb;
        String localfb;
        String citystatefb;
        String breedtxtfb;
        String CityFb;
        String StateFB;
        String ZipCodeFB;
        String agetxtfb;
        String sizetxtfb;
        String gendertxtfb;
        String animaltypetxtfb;

        breedfb = dataSnapshot.child(USerid).child("Search").child("breedcb").getValue(String.class).toString();
        breedtxtfb = dataSnapshot.child(USerid).child("Search").child("breedtext").getValue(String.class).toString();
        animaltypefb = dataSnapshot.child(USerid).child("Search").child("animaltypecb").getValue(String.class).toString();
        animaltypetxtfb = dataSnapshot.child(USerid).child("Search").child("animaltypetext").getValue(String.class).toString();
        genderfb = dataSnapshot.child(USerid).child("Search").child("gendercb").getValue(String.class).toString();
        gendertxtfb = dataSnapshot.child(USerid).child("Search").child("gendertxt").getValue(String.class).toString();
        agefb = dataSnapshot.child(USerid).child("Search").child("agecb").getValue(String.class).toString();
        agetxtfb = dataSnapshot.child(USerid).child("Search").child("agetxt").getValue(String.class).toString();
        sizefb = dataSnapshot.child(USerid).child("Search").child("sizecb").getValue(String.class).toString();
        sizetxtfb = dataSnapshot.child(USerid).child("Search").child("sizetxt").getValue(String.class).toString();
        localfb = dataSnapshot.child(USerid).child("Search").child("locationcb").getValue(String.class).toString();
        citystatefb = dataSnapshot.child(USerid).child("Search").child("locationrb").getValue(String.class).toString();
        CityFb = dataSnapshot.child(USerid).child("Search").child("Citytxt").getValue(String.class).toString();
        StateFB = dataSnapshot.child(USerid).child("Search").child("Statetxt").getValue(String.class).toString();
        ZipCodeFB = dataSnapshot.child(USerid).child("Search").child("Zipcodetxt").getValue(String.class).toString();

        if (breedfb.equals("true")) {
            urlargforpetrecord += "&breed=" + breedtxtfb;
        }
        if (animaltypefb.equals("true")) {
            urlargforpetrecord += "&animal=" + animaltypetxtfb;
        }
        if (genderfb.equals("true")) {
            urlargforpetrecord += "&sex=" + gendertxtfb;
        }
        if (agefb.equals("true")) {
            urlargforpetrecord += "&age=" + agetxtfb;
        }
        if (sizefb.equals("true")) {
            urlargforpetrecord += "&size=" + sizetxtfb;
        }
        if (citystatefb.equals("true")) {
            urlargforpetrecord += "&location=" + CityFb + ',' + StateFB;
        } else {
            urlargforpetrecord += "&location=" + ZipCodeFB;
        }
        urlargforpetrecord += "&count=9";
        offestformuiltplerecords += 9;
        if (offestformuiltplerecords <= 9) {
            Handlexml petObj = new Handlexml(urlbase + urlmethodfindmuiltplerecords + urlkey + urlargforpetrecord);
            petObj.FetchXml();
            while (petObj.parsingcomplete) ;
        } else {
            Handlexml petObj = new Handlexml(urlbase + urlmethodfindmuiltplerecords + urlkey + urlargforpetrecord + "&offset=" + offestformuiltplerecords);
            petObj.FetchXml();
            while (petObj.parsingcomplete) ;
        }

        gridViewAdapter = new GridViewAdapter(this, R.layout.griditem, petList);
        gridView.setAdapter(gridViewAdapter);

    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            petNumber = petList.get(position).getPetnumber();

            startActivity(new Intent(Home.this, Pet_description.class));
        }
    };

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
        }
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_home);
        if (drawerLayout.isDrawerOpen((GravityCompat.START)))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_home);
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_Search) {
            startActivity(new Intent(Home.this, search.class));
        } else if (id == R.id.nav_favorite) {
            startActivity(new Intent(Home.this, Favorite.class));
        } else if (id == R.id.nav_logout) {
            //startActivity(new Intent(Home.this,Profile.class));

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_home);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
