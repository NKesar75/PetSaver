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
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static domain.hackathon.hackathon2017.R.id.home_back;

public class Favorite extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static int petNumber1;
    private static final String TAG = "Favorite";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String USerid = user.getUid();
    private ViewStub stubGrid;
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;

    public static List<PetInfo> petList1 = new ArrayList<>();
    private DrawerLayout draw;
    private ActionBarDrawerToggle toggle;


    private String urlbase = "http://api.petfinder.com/pet.get?key=58fe2e272bebddbc0f5e66901f239055&id="; //base url

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        stubGrid = (ViewStub) findViewById(R.id.stub_grid);
        stubGrid.inflate();
        gridView = (GridView) findViewById(R.id.mygridview);
        gridView.setOnItemClickListener(onItemClick);

        draw = (DrawerLayout) findViewById(R.id.activity_favorite);
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
        petList1.clear();
        int index = 1;
        int id = 0;
        boolean isitstillfav = false;
        for (int i = 0; i < dataSnapshot.child(USerid).child("Favs").getChildrenCount(); i++)
        {
            if(dataSnapshot.child(USerid).child("Favs").child("Fav" + (index)).child("FavOrNot").getValue(boolean.class) != null)
                isitstillfav =  dataSnapshot.child(USerid).child("Favs").child("Fav" + (index)).child("FavOrNot").getValue(boolean.class).booleanValue();
            if (isitstillfav)
            {
                if(dataSnapshot.child(USerid).child("Favs").child("Fav" + (index)).child("Id").getValue(int.class) != null)
                    id = dataSnapshot.child(USerid).child("Favs").child("Fav" + (index)).child("Id").getValue(int.class).intValue();
                HandlexmlFav obj = new HandlexmlFav(urlbase + id);
                obj.FetchXml();
                while (obj.parsingcomplete) ;
            }
            index++;
        }
        gridViewAdapter = new GridViewAdapter(this, R.layout.griditem, petList1);
        gridView.setAdapter(gridViewAdapter);
    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            petNumber1 = petList1.get(position).getPetnumber();
            startActivity(new Intent(Favorite.this, Fav_Desc.class));
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
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_favorite);
        if (drawerLayout.isDrawerOpen((GravityCompat.START)))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(Favorite.this, Home.class));
        } else if (id == R.id.nav_Search) {
            startActivity(new Intent(Favorite.this, search.class));
        } else if (id == R.id.nav_favorite) {
            DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_favorite);
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Favorite.this);
            builder.setMessage("Are you Sure you want to Logout?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAuth.signOut();
                            startActivity(new Intent(Favorite.this, Login.class));
                        }
                    })
                    .setNegativeButton("Cancel", null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else if (id == R.id.nav_info) {
            startActivity(new Intent(Favorite.this, info.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_favorite);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
