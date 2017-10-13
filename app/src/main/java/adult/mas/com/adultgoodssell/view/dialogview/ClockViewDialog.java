package adult.mas.com.adultgoodssell.view.dialogview;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.thirdviewmodel.lockview.Lock9View;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/8/17.
 */

public class ClockViewDialog  extends Dialog {
    private View conentView;
    @BindView(R.id.lockview)
    Lock9View lockview;
    public ClockViewDialog(Context context, int themeResId) {
        super(context, themeResId);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.dialog_clckview, null);
        setContentView(conentView);
        ButterKnife.bind(this);
        lockview.setCallBack(new Lock9View.CallBack() {
            @Override
            public void onFinish(String password) {
                System.out.println(password);
            }
        });
        Window window = getWindow();
        window.setWindowAnimations(R.style.dialogPwWindowAnim);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}