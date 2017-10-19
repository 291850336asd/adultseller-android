package adult.mas.com.adultgoodssell.bussiness.webview;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by sunmeng on 17/10/19.
 */

public class JSHook {

    public static final String JSHOOK= "seller";
    private Context context;

    public int showDetail = 0;


    public JSHook(Context context) {
        this.context = context.getApplicationContext();
    }

    @JavascriptInterface()
    public void showDetail(){
        showDetail ++;
    }

    @JavascriptInterface()
    public void closeDetail(){
        showDetail = 0;
    }

    @JavascriptInterface()
    public void getInfo(){
        Toast.makeText(context, "seller tostring", Toast.LENGTH_SHORT).show();
    }
}
