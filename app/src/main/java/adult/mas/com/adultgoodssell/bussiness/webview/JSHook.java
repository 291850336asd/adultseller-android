package adult.mas.com.adultgoodssell.bussiness.webview;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import adult.mas.com.adultgoodssell.BaseActivity;

/**
 * Created by sunmeng on 17/10/19.
 */

public class JSHook {

    public static final String JSHOOK= "seller";
    private Context context;

    public int showDetail = 0;


    public JSHook(Context context) {
        this.context = context;
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
        Toast.makeText(context.getApplicationContext(), "seller tostring", Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface()
    public void closeActivity(){
        ((BaseActivity)context).finish();
    }

}
