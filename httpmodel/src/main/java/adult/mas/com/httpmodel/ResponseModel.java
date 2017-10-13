package adult.mas.com.httpmodel;

/**
 * Created by sunmeng on 17/8/1.
 */

public class ResponseModel<T> {
    protected int code;
    protected String message;
    protected T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
