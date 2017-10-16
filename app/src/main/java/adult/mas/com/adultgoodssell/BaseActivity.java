package adult.mas.com.adultgoodssell;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import adult.mas.com.adultgoodssell.constant.ConstantData;
import adult.mas.com.adultgoodssell.utils.StatusBarUtils;

/**
 * Created by sunmeng on 17/7/31.
 *
 * 全屏 沉浸式
 */

public class BaseActivity extends AppCompatActivity {
    protected ATRequestPermissionCallBack permissionCallBack;
   // private static ClockViewDialog clockViewDialog;
    private static boolean isBackGround = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        AdultApplication.addActivity(this);
        setupTransparencyStatus();
        StatusBarUtils.StatusBarLightMode(this);
    }
    /**
     * 设置透明状态栏
     */
    protected void setupTransparencyStatus() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }
    }
    public void addPermissionCallBack(ATRequestPermissionCallBack permissionCallBack) {
        this.permissionCallBack = permissionCallBack;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isBackGround){
//            clockViewDialog = new ClockViewDialog(getContext(), R.style.dialog_full);
//            clockViewDialog.show();
            isBackGround = false;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (!isAppOnForeground()) {
            isBackGround = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (permissionCallBack != null) {
                permissionCallBack.success(requestCode);
            }
            // If request is cancelled, the result arrays are empty.
            switch (requestCode) {
                case ConstantData.MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                    Intent moblieIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(moblieIntent, ConstantData.PHONE_REQUEST_CODE);
                    break;
                }
                case ConstantData.MY_PERMISSIONS_REQUEST_READ_WRITE_STORAGE: {

                }
            }
            // other 'case' lines to check for other
            // permissions this app might request
        } else {
            if (requestCode == ConstantData.MY_PERMISSIONS_REQUEST_READ_WRITE_STORAGE) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        requestCode);
            }
            // permission was granted, yay! Do the
            // contacts-related task you need to do.
        }
    }

    protected boolean isHavePermission(String permission, final int requestCode) {
        int havaPermission = -10;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            havaPermission = ContextCompat.checkSelfPermission(this, permission);
        } else {
            PackageManager pm = getPackageManager();
            havaPermission = pm.checkPermission(permission, getPackageName());
        }
        if (havaPermission != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.
            String[] requestPermissionArray = new String[]{permission};
            if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permission)) {
                requestPermissionArray = new String[]{permission, Manifest.permission.ACCESS_COARSE_LOCATION};
            } else if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
                requestPermissionArray = new String[]{permission, Manifest.permission.READ_EXTERNAL_STORAGE};
            }
            ActivityCompat.requestPermissions(this,
                    requestPermissionArray,
                    requestCode);
            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AdultApplication.removeActivity(this);
    }

    public interface ATRequestPermissionCallBack {
        void success(int myRequestCode);
    }




    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    public Context getContext(){
        return this;
    }
}