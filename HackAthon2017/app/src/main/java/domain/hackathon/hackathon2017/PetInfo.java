package domain.hackathon.hackathon2017;

/**
 * Created by Raj Chandan on 11/11/2017.
 */

public class PetInfo
{
    private  String imageid;
    private String age;
    private int petnumber;

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
}
