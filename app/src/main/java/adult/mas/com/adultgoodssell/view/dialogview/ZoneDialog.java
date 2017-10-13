package adult.mas.com.adultgoodssell.view.dialogview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.event.NoDoubleClickListener;
import adult.mas.com.adultgoodssell.modelbean.mainview.zone.CityZone;
import adult.mas.com.adultgoodssell.utils.CollectionUtils;
import adult.mas.com.adultgoodssell.utils.ResourcesUtils;
import adult.mas.com.adultgoodssell.utils.ToastUtils;
import adult.mas.com.adultgoodssell.utils.XMLCityUtils;
import adult.mas.com.adultgoodssell.view.commondialog.CommBottomTopDialog;
import adult.mas.com.thirdviewmodel.loopview.LoopView;
import adult.mas.com.thirdviewmodel.loopview.OnItemSelectedListener;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunmeng on 17/8/17.
 */

public class ZoneDialog extends CommBottomTopDialog {
    private View conentView;
    @BindView(R.id.loopViewprovince)
    LoopView loopViewprovince;
    @BindView(R.id.loopViewCity)
    LoopView loopViewCity;
    @BindView(R.id.loopViewCounty)
    LoopView loopViewCounty;
    private int selectProvince = 0;
    private int selectCity = 0;
    List<CityZone> zones = null;
    public ZoneDialog(Context context, int themeResId) {
        super(context, themeResId);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.dialog_zone, null);
        setContentView(conentView);
        ButterKnife.bind(this);
        Window window = getWindow();
        window.setWindowAnimations(R.style.dialogPwWindowAnim);
        try {
            zones =  XMLCityUtils.loadCityFromFile(getContext());
            if(!CollectionUtils.isEmpty(zones)){
                ArrayList<String> proviencelist = new ArrayList<>();
                for (CityZone item : zones){
                    proviencelist.add(item.getName());
                }
                //滚动监听
                loopViewprovince.setListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int index) {
                        selectProvince = index;
                        selectCity = 0;
                        ArrayList<String> cityList = new ArrayList<>();
                        for (CityZone item : zones.get(selectProvince).getZones()){
                            cityList.add(item.getName());
                        }
                        loopViewCity.setItems(cityList);
                        loopViewCity.setCurrentPosition(0);
                        ArrayList<String> countyList = new ArrayList<>();
                        for (CityZone item : zones.get(selectProvince).getZones().get(selectCity).getZones()){
                            countyList.add(item.getName());
                        }
                        loopViewCounty.setItems(countyList);
                        loopViewCounty.setInitPosition(0);

                    }
                });
                loopViewprovince.setItems(proviencelist);
                loopViewprovince.setInitPosition(0);

                //市
                ArrayList<String> cityList = new ArrayList<>();
                for (CityZone item : zones.get(selectProvince).getZones()){
                    cityList.add(item.getName());
                }
                loopViewCity.setListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int indexCity) {
                        selectCity = indexCity;
                        ArrayList<String> countyList = new ArrayList<>();
                        for (CityZone item : zones.get(selectProvince).getZones().get(indexCity).getZones()){
                            countyList.add(item.getName());
                        }
                        loopViewCounty.setItems(countyList);
                        loopViewCounty.setCurrentPosition(0);
                    }
                });
                loopViewCity.setItems(cityList);
                loopViewCity.setInitPosition(0);
                //县

                ArrayList<String> countyList = new ArrayList<>();
                for (CityZone item : zones.get(selectProvince).getZones().get(selectCity).getZones()){
                    countyList.add(item.getName());
                }
                loopViewCounty.setListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int index) {

                    }
                });
                loopViewCounty.setItems(countyList);
                loopViewCounty.setInitPosition(0);
            }
        } catch (Exception e) {
            dismissDialogAni();
            ToastUtils.showToastLong(getContext(), ResourcesUtils.getStringById(getContext(), R.string.zone_error));
        }
    }

    public String getSelectAddress(){
        String str = "";
        try{
            if(!CollectionUtils.isEmpty(zones)){
                str += zones.get(selectProvince).getName();
                str += zones.get(selectProvince).getZones().get(selectCity).getName();
                str += zones.get(selectProvince).getZones().get(selectCity).getZones().get(loopViewCounty.getSelectedItem()).getName();
            }
        }catch (Exception e){
            str = "";
        }
        return str;
    }

    public String getProvience(){
        return zones.get(selectProvince).getName();
    }

    public String getCity(){
        return  zones.get(selectProvince).getZones().get(selectCity).getName();
    }

    public String getCounty(){
        return zones.get(selectProvince).getZones().get(selectCity).getZones().get(loopViewCounty.getSelectedItem()).getName();
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