package prochain.com.tronbox.setting;


public class SettingListInfo {

    private int image;

    private String name;

    private String introduce;

    private int icon;

    public SettingListInfo(int image, String name, String introduce, int icon) {
        this.image = image;
        this.name = name;
        this.introduce = introduce;
        this.icon = icon;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
