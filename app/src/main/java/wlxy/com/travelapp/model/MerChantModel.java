package wlxy.com.travelapp.model;

/**
 * @author dragon
 *         数据模型，使用内部类会出错
 */
public class MerChantModel {
    private String bname;
    private String address;
    private String image;
    private String level;

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "MerChantModel{" +
                "bname='" + bname + '\'' +
                ", address='" + address + '\'' +
                ", image='" + image + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
