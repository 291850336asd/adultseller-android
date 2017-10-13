package adult.mas.com.adultgoodssell.netapi.request;

/**
 * Created by sunmeng on 17/8/1.
 *  test
 */

public class UserRequest {
    private long id;
    private String content;

    public UserRequest(long id, String content) {
        this.id = id;
        this.content = content;
    }
    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

}
