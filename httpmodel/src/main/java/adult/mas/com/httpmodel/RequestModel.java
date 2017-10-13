package adult.mas.com.httpmodel;

/**
 * Created by sunmeng on 17/8/1.
 */

public class RequestModel<T> extends RequestPageBean{
    private int type = 3;
    private String requestCode;
    private T data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
