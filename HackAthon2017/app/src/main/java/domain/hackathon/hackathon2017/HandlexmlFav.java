package domain.hackathon.hackathon2017;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Hector on 11/12/2017.
 */

public class HandlexmlFav {
    private String breed = "";
    private String age = "";
    private String animalname = "";
    private String shelterid = "";
    private String animaltype = "";
    private String petid = "";
    private String gender = "";
    private String Size = "";
    private String image = "";
    private boolean hasbeencalledbreed = false;
    private boolean hasbeencalledimage = false;
    private String urlstring = null;
    XmlPullParserFactory xmlfactoryobj;
    public volatile boolean parsingcomplete = true;



    public HandlexmlFav(String url) {
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
                                    Favorite.petList1.add(new PetInfo(image,age,Integer.parseInt(petid)));
                                    hasbeencalledimage = true;
                                }
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
