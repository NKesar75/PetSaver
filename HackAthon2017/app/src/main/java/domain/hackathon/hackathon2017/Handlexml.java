package domain.hackathon.hackathon2017;

import android.text.TextUtils;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hector on 11/11/2017.
 */

public class Handlexml {
    private String breed = null;
    private String age = null;
    private String animalname = null;
    private String shelterid = null;
    private String animaltype = null;
    private String petid = null;
    private String gender = null;
    private String Size = null;
    private String image = null;
    private boolean hasbeencalledbreed = false;
    private boolean hasbeencalledimage = false;
    private String urlstring = null;
    XmlPullParserFactory xmlfactoryobj;
    public volatile boolean parsingcomplete = true;


    public Handlexml(String url) {
        urlstring = url;
    }

    public String getBreed() {
        return breed;
    }

    public String getPetid() {
        return petid;
    }

    public String getGender() {
        return gender;
    }

    public String getSize() {
        return Size;
    }

    public String getImage() {
        return image;
    }

    public String getAge() {
        return age;
    }

    public String getAnimaltype() {
        return animaltype;
    }

    public String getName() {
        return animalname;
    }

    public String getShelterid() {
        return shelterid;
    }

    public void ParseXmlAndStoreit(XmlPullParser myparser) {
        int event;
        String text = null;
        try {
            event = myparser.getEventType();
            Home.invaildarg = 0;
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
                                animalname = text;
                                String[] parts = animalname.split("\\(");
                                animalname = parts[0];
                                break;
                            case "breed":
                                if (hasbeencalledbreed == false) {
                                    breed += text; //text;
                                    hasbeencalledbreed = true;
                                    break;
                                } else {
                                    breed += " " + "and " + text;
                                    break;
                                }
                            case "age":
                                age = text;
                                break;
                            case "message":
                                if (text.contains("invalid arguments") || text.contains("Invalid geographical location"))
                                    Home.invaildarg = 1;
                                break;
                            case "shelterId":
                                shelterid = text;
                                break;
                            case "animal":
                                animaltype = text;
                                break;
                            case "id":
                                petid = text;
                                hasbeencalledimage = false;
                                break;
                            case "photo":
                                if (hasbeencalledimage == false) {
                                    image = text;
                                    Home.petList.add( new PetInfo(image,animalname.toUpperCase(),Integer.parseInt(petid)));
                                    Home.invaildarg = 0;
                                    hasbeencalledimage = true;
                                }
                                break;
                            default:
                                if(hasbeencalledimage == false)
                                {
                                    Home.invaildarg = 2;
                                }
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
