package adult.mas.com.adultgoodssell.view.dialogview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.event.NoDoubleClickListener;
import adult.mas.com.adultgoodssell.view.commondialog.CommBottomTopDialog;

/**
 * Created by sunmeng on 17/8/17.
 */

public class MarketShopDialog extends CommBottomTopDialog {
    private View conentView;
    public MarketShopDialog(Context context, int themeResId) {
        super(context, themeResId);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.dialog_marketshop, null);

        setContentView(conentView);
        Window window = getWindow();
        window.setWindowAnimations(R.style.dialogPwWindowAnim);
    }

    @Override
    public View setContentLLAniView() {
        conentView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                dismissDialogAni();
            }
        });
        View view = conentView.findViewById(R.id.contentLL);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return  view;
    }
    public View getContentView(){
        return conentView;
    }
}