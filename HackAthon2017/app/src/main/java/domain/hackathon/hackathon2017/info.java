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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class info extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "info";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String USerid = user.getUid();

    private DrawerLayout draw;
    private ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

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

        draw = (DrawerLayout) findViewById(R.id.activity_info);
        toggle = new ActionBarDrawerToggle(this, draw, R.string.open, R.string.close);
        draw.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigation = (NavigationView) findViewById(R.id.nav_view);
        navigation.setNavigationItemSelectedListener(this);

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
            DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_info);
            if (drawerLayout.isDrawerOpen((GravityCompat.START)))
                drawerLayout.closeDrawer(GravityCompat.START);
            else
                super.onBackPressed();
        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                startActivity(new Intent(info.this, Home.class));
            } else if (id == R.id.nav_Search) {
                startActivity(new Intent(info.this,search.class));
            } else if (id == R.id.nav_favorite) {
                startActivity(new Intent(info.this, Favorite.class));
            } else if (id == R.id.nav_logout) {

                AlertDialog.Builder builder = new AlertDialog.Builder(info.this);
                builder.setMessage("Are you Sure you want to Logout?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAuth.signOut();
                                startActivity(new Intent(info.this,Login.class));
                            }
                        })
                        .setNegativeButton("Cancel",null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            else if(id == R.id.nav_info)
            {
                DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_info);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_info);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    }