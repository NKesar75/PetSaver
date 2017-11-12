package domain.hackathon.hackathon2017;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Fav_ShelterInfo extends AppCompatActivity {
    private String urlShelter = "http://api.petfinder.com/shelter.get?key=58fe2e272bebddbc0f5e66901f239055&id=";

    private EditText sname;
    private EditText sAddress;
    private EditText sCity;
    private EditText sNumber;
    private EditText sEmail;
    public static Shelterholder tempholder = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav__shelter_info);

        Button hider =(Button)findViewById(R.id.invisible_FAvshelter);
        hider.setFocusable(true);
        hider.setFocusableInTouchMode(true);
        hider.requestFocus();
        hider.setVisibility(View.GONE);
        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        sname = (EditText)findViewById(R.id.Name_SI_fav);
        sAddress = (EditText)findViewById(R.id.Address_SI_fav);
        sCity = (EditText)findViewById(R.id.City_State_Zip_SI_fav);
        sNumber = (EditText)findViewById(R.id.Number_SI_fav);
        sEmail = (EditText)findViewById(R.id.Email_SI_fav);

        favshelterxmlhandler obj = new favshelterxmlhandler(urlShelter + Fav_Desc.shelterid);
        obj.FetchXml();
        while(obj.parsingcomplete);

        if (!tempholder.getSheletername().equals(null)) {
            sname.setText(tempholder.getSheletername());
        }
        else{
            sname.setText("N/A");
        }

        if (!tempholder.getAdress().equals(null)) {
            sAddress.setText(tempholder.getAdress());
        }
        else{
            sAddress.setText("N/A");
        }

        if (!tempholder.getCity().equals(null)|| !tempholder.getState().equals("")) {
            sCity.setText(tempholder.getCity()+", " + tempholder.getState() + " " + tempholder.getZipcode());
        }
        else{
            sCity.setText("N/A");
        }

        if (!tempholder.getPhonenumber().equals(null)) {
            sNumber.setText(tempholder.getPhonenumber());
        }
        else{
            sNumber.setText("N/A");
        }

        if (!tempholder.getEmailaccount().equals(null)) {
            sEmail.setText(tempholder.getEmailaccount());
        }
        else{
            sEmail.setText("N/A");
        }
        sNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sNumber.equals("N/A")){
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + tempholder.getPhonenumber()));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}


