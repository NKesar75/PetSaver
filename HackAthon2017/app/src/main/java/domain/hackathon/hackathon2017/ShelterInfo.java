package domain.hackathon.hackathon2017;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ShelterInfo extends AppCompatActivity {

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
        setContentView(R.layout.activity_shelter_info);

        sname = (EditText)findViewById(R.id.Name_SI);
        sAddress = (EditText)findViewById(R.id.Address_SI);
        sCity = (EditText)findViewById(R.id.City_State_Zip_SI);
        sNumber = (EditText)findViewById(R.id.Number_SI);
        sEmail = (EditText)findViewById(R.id.Email_SI);
        Button hider = (Button)findViewById(R.id.Invisible_Shelter);
        hider.setFocusable(true);
        hider.setFocusableInTouchMode(true);
        hider.requestFocus();
        hider.setVisibility(View.GONE);
        final HandlexmlShelter obj = new HandlexmlShelter(urlShelter + Pet_description.shelterid);
        obj.FetchXml();
        while(obj.parsingcomplete);

        if (!tempholder.getSheletername().equals(null)|| !tempholder.getSheletername().equals(" ")) {
            sname.setText(tempholder.getSheletername());
        }
        else{
            sname.setText("N/A");
        }

        if (!tempholder.getAdress().equals(null) || !tempholder.getAdress().equals(" ")) {
            sAddress.setText(tempholder.getAdress());
        }
        else{
            sAddress.setText("N/A");
        }

        if (!tempholder.getCity().equals(null)|| !tempholder.getState().equals(null) || !tempholder.getState().equals(" ") || !tempholder.getCity().equals(" ")) {
            sCity.setText(tempholder.getCity()+", " + tempholder.getState() + " " + tempholder.getZipcode());
        }
        else{
            sCity.setText("N/A");
        }

        if (!tempholder.getPhonenumber().equals(null)|| !tempholder.getPhonenumber().equals(" ")) {
            sNumber.setText("Phone Number: "+ tempholder.getPhonenumber());
        }
        else{
            sNumber.setText("N/A");
        }

        if (!tempholder.getEmailaccount().equals(null)|| !tempholder.getEmailaccount().equals(" ")) {
            sEmail.setText("Email: "+ tempholder.getEmailaccount());
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

        sEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Create the Intent */
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {obj.getEmailaccount()});
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT,"");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, "");

/* Send it off to the Activity-Chooser */
                startActivity(Intent.createChooser(intent,"Send"));
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
