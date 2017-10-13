package adult.mas.com.adultgoodssell.modelbean.mainview;

/**
 * Created by sunmeng on 17/8/3.
 */

public class MainBanner {
    private String bannerPicUrl;
    private String bannerChangeLink;
    private int bannerType;
    private String bannerPicDesc;
    private int currentPosition;

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getBannerPicDesc() {
        return bannerPicDesc;
    }

    public void setBannerPicDesc(String bannerPicDesc) {
        this.bannerPicDesc = bannerPicDesc;
    }

    public String getBannerPicUrl() {
        return bannerPicUrl;
    }

    public void setBannerPicUrl(String bannerPicUrl) {
        this.bannerPicUrl = bannerPicUrl;
    }

    public String getBannerChangeLink() {
        return bannerChangeLink;
    }

    public void setBannerChangeLink(String bannerChangeLink) {
        this.bannerChangeLink = bannerChangeLink;
    }

    public int getBannerType() {
        return bannerType;
    }

    public void setBannerType(int bannerType) {
        this.bannerType = bannerType;
    }
}
