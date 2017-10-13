package adult.mas.com.adultgoodssell.bussiness.address;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.anupcowkur.reservoir.Reservoir;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.TranslucentActivity;
import adult.mas.com.adultgoodssell.constant.ConstantData;
import adult.mas.com.adultgoodssell.event.NoDoubleClickListener;
import adult.mas.com.adultgoodssell.modelbean.mainview.zone.ZoneBean;
import adult.mas.com.adultgoodssell.utils.ResourcesUtils;
import adult.mas.com.adultgoodssell.utils.StringUtils;
import adult.mas.com.adultgoodssell.utils.ToastUtils;
import adult.mas.com.adultgoodssell.view.dialogview.ZoneDialog;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/8/19.
 */

public class AddAddressActivity extends TranslucentActivity implements View.OnLayoutChangeListener {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.searchIcon)
    View searchIcon;
    @BindView(R.id.back)
    View back;
    @BindView(R.id.selectCity)
    TextView selectCity;
    @BindView(R.id.saveBtn)
    TextView saveBtn;
    ZoneDialog shopDialog;
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.userPhone)
    EditText userPhone;
    @BindView(R.id.userDetail)
    EditText userDetail;
    @BindView(R.id.rootParent)
    View rootParent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_address);
        ButterKnife.bind(this);
        initialView();
        rootParent.addOnLayoutChangeListener(this);
    }

    private void initialView() {
        back.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                finish();
            }
        });
        title.setText(R.string.goods_address);
        searchIcon.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        selectCity.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                if(shopDialog == null) {
                    shopDialog = new ZoneDialog(getContext(), R.style.dialog_full);
                    View contentView = shopDialog.getContentView();
                    contentView.findViewById(R.id.sure).setOnClickListener(new NoDoubleClickListener() {
                        @Override
                        public void onClickNoDouble(View view) {
                            selectCity.setText(shopDialog.getSelectAddress());
                            shopDialog.dismissDialogAni();

                        }
                    });
                }
                shopDialog.show();
            }
        });
        saveBtn.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onClickNoDouble(View view) {
                if(!StringUtils.isEmpty(userName.getText().toString())){
                    if(!StringUtils.isEmpty(userPhone.getText().toString())){
                        if(StringUtils.isMobile(userPhone.getText().toString())){
                            if(!StringUtils.isEmpty(userDetail.getText().toString())){
                                if(shopDialog == null) {
                                    shopDialog = new ZoneDialog(getContext(), R.style.dialog_full);
                                    View contentView = shopDialog.getContentView();
                                    contentView.findViewById(R.id.sure).setOnClickListener(new NoDoubleClickListener() {
                                        @Override
                                        public void onClickNoDouble(View view) {
                                            selectCity.setText(shopDialog.getSelectAddress());
                                            shopDialog.dismissDialogAni();

                                        }
                                    });
                                    shopDialog.show();
                                }else {
                                    ZoneBean zoneBean = new ZoneBean();
                                    zoneBean.setDetail(userDetail.getText().toString());
                                    zoneBean.setReceiveName(userName.getText().toString());
                                    zoneBean.setReceivePhone(userPhone.getText().toString());
                                    zoneBean.setProvince(shopDialog.getProvience());
                                    zoneBean.setCity(shopDialog.getCity());
                                    zoneBean.setArea(shopDialog.getCounty());

                                    try {
                                       Reservoir.put(ConstantData.CacheUsers_NOLOGIN, zoneBean);
                                    }catch (Exception e){

                                    }
                                    finish();
                                }

                            }else{
                                ToastUtils.showToastShort(getContext(), ResourcesUtils.getStringById(getContext(),R.string.user_detail_address_empty));
                            }
                        }else {
                            ToastUtils.showToastShort(getContext(), ResourcesUtils.getStringById(getContext(),R.string.user_phone_model_error));
                        }
                    }else {
                        ToastUtils.showToastShort(getContext(), ResourcesUtils.getStringById(getContext(),R.string.user_phone_empty));
                    }
                }else{
                    ToastUtils.showToastShort(getContext(), ResourcesUtils.getStringById(getContext(),R.string.user_name_empty));
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        System.out.println("bootom : " + bottom + "-----" + oldBottom);
    }
}
