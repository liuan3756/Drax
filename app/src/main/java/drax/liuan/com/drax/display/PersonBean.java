package drax.liuan.com.drax.display;

public class PersonBean {
    public static final int PERSON_TYPE_STAFF = 1;
    public static final int PERSON_TYPE_VISITOR = 2;
    private String name;
    private String imgUrl;
    private int staffType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getStaffType() {
        return staffType;
    }

    public void setStaffType(int staffType) {
        this.staffType = staffType;
    }
}
