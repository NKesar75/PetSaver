package domain.hackathon.hackathon2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fav_Desc extends AppCompatActivity {

    private static final String TAG = "Fav_Desc";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav__desc);



    }
}
