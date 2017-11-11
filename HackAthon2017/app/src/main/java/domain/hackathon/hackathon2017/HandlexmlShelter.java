package domain.hackathon.hackathon2017;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Hector on 11/11/2017.
 */

public class HandlexmlShelter {
    private String Sheletername = "";
    private String Adress = "";
    private String state = "";
    private String city = "";
    private String country = "";
    private String zipcode= "";
    private String phonenumber = "";
    private String Emailaccount = "";
    private String urlstring = null;

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

    XmlPullParserFactory xmlfactoryobj;
    public volatile boolean parsingcomplete = true;

    public HandlexmlShelter(String url) {
        urlstring = url;
    }

    public void ParseXmlAndStoreit(XmlPullParser myparser) {
        int event;
        String text = null;
        try {
            event = myparser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myparser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myparser.getText();
                        break;
                    case XmlPullParser.END_TAG:

                        switch (name) {
                            case "name":
                                Sheletername = text;
                                break;
                            case "address1":
                                Adress += text;
                                break;
                            case "city":
                                city = text;
                                break;
                            case "state":
                                state = text;
                                break;
                            case "zip":
                                zipcode = text;
                                break;
                            case "country":
                                country = text;
                                break;
                            case "phone":
                                phonenumber = text;
                                break;
                            case "email":
                                Emailaccount = text;
                                break;
                            default:
                                break;
                        }

                }
                event = myparser.next();
            }
            parsingcomplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void FetchXml() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlstring);
                    HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                    connect.setReadTimeout(10000);
                    connect.setConnectTimeout(15000);
                    connect.setRequestMethod("GET");
                    connect.setDoInput(true);
                    connect.connect();

                    InputStream stream = connect.getInputStream();
                    xmlfactoryobj = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlfactoryobj.newPullParser();
                    myparser.setInput(stream, null);
                    ParseXmlAndStoreit(myparser);
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
