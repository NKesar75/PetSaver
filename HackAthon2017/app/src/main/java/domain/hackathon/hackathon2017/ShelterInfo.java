package domain.hackathon.hackathon2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        HandlexmlShelter obj = new HandlexmlShelter(urlShelter + Pet_description.shelterid);
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
    }
}
