package domain.hackathon.hackathon2017;

/**
 * Created by Raj Chandan on 11/11/2017.
 */

public class PetInfo
{
    private  String imageid;
    private String age;
    private int petnumber;
    private String breed = "";
    private String animalname = "";
    private String shelterid = "";
    private String animaltype = "";
    private String gender = "";
    private String Size = "";

    public String getBreed() {
        return breed;
    }

    public String getAnimalname() {
        return animalname;
    }

    public String getShelterid() {
        return shelterid;
    }

    public String getAnimaltype() {
        return animaltype;
    }


    public String getGender() {
        return gender;
    }

    public String getSize() {
        return Size;
    }

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getPetnumber() {
        return petnumber;
    }

    public void setPetnumber(int petnumber) {
        this.petnumber = petnumber;
    }

    public PetInfo()
    {

    }

    public PetInfo(String imageid, String age, int petnumber)
    {
        this.imageid = imageid;
        this.age = age;
        this.petnumber = petnumber;
    }
    public PetInfo(String imageid, String age, int petnumber, String breed, String animalname, String shleterid, String animaltype, String gender, String size)
    {
        this.imageid = imageid;
        this.age = age;
        this.petnumber = petnumber;
        this.breed = breed;
        this.animalname = animalname;
        this.shelterid = shleterid;
        this.animaltype = animaltype;
        this.gender = gender;
        this.Size = size;

    }
}
