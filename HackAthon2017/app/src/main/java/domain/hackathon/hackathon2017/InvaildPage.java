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
import android.view.Menu;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.R.id.toggle;
//import static domain.hackathon.hackathon2017.R.id.home_back;

public class InvaildPage extends AppCompatActivity {
    private static final String TAG = "InvaildPage";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String USerid = user.getUid();
    private EditText invalidMessage;
    TextView swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invaild_page);
        swipe = (TextView) findViewById(R.id.txtviewforswipe);

        invalidMessage = (EditText) findViewById(R.id.invaildpagetxt);
        if (Home.invaildarg == 1) {
            invalidMessage.setText("Please Check Your Spelling");
        } else {
            invalidMessage.setText("Could Not Find Results");
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (Home.invaildarg == 1) {
                finish();
                startActivity(new Intent(this, search.class));
            } else {
                if (Home.offestformuiltplerecords >= Home.numberofpets) {
                    Home.offestformuiltplerecords -= (Home.numberofpets * 3);
                    finish();
                    startActivity(new Intent(this, Home.class));
                } else {
                    finish();
                    startActivity(new Intent(this, search.class));
                }
            }
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onBackPressed() {

    }


}

