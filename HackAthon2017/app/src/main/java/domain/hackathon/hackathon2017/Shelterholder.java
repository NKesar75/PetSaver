package domain.hackathon.hackathon2017;

import android.location.Address;

/**
 * Created by Hector on 11/12/2017.
 */

public class Shelterholder {
    private String Sheletername = "";
    private String Adress = "";
    private String state = "";
    private String city = "";
    private String country = "";
    private String zipcode= "";
    private String phonenumber = "";
    private String Emailaccount = "";

    public Shelterholder(){

    }

    public String getSheletername() {
        return Sheletername;
    }

    public String getAdress() {
        return Adress;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getEmailaccount() {
        return Emailaccount;
    }

    public Shelterholder(String name, String Addy, String Sta, String Cit, String Coun, String Zipcode, String Phone, String Emailacc){
        Sheletername = name;
        Adress = Addy;
        state = Sta;
        city = Cit;
        country = Coun;
        zipcode = Zipcode;
        phonenumber = Phone;
        Emailaccount = Emailacc;
    }
}
