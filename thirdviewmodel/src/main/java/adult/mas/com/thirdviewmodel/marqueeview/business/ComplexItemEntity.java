package adult.mas.com.thirdviewmodel.marqueeview.business;

/**
 * Created by GongWen on 16/12/27.
 */

public class ComplexItemEntity {

    private String title;
    private String time;

    public ComplexItemEntity(String title, String time) {
        this.title = title;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
