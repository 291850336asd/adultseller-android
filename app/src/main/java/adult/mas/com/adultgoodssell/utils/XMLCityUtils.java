package adult.mas.com.adultgoodssell.utils;

import android.content.Context;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adult.mas.com.adultgoodssell.R;
import adult.mas.com.adultgoodssell.modelbean.mainview.zone.CityZone;

/**
 * Created by sunmeng on 17/8/20.
 */

final public class XMLCityUtils {

    private final static String province = "province";
    private final static String city = "city";
    private final static String district = "district";

    private XMLCityUtils() {
    }

    public static List<CityZone> loadCityFromFile(Context context) throws XmlPullParserException, IOException {
        XmlResourceParser xmlResourceParser = context.getResources().getXml(R.xml.city_county);
        List<CityZone> provinceList = new ArrayList<>();
        List<CityZone> cityZoneList = null;
        List<CityZone> countyList = null;
        int eventType = xmlResourceParser.getEventType();
        CityZone provienceItem = null;
        CityZone cityitem = null;
        CityZone districtItem;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG: {
                    String name = xmlResourceParser.getName();
                    String paraName = xmlResourceParser.getAttributeValue(null, "name");
                    if(province.equalsIgnoreCase(name)){
                        provienceItem = new CityZone();
                        if(cityZoneList == null){
                            cityZoneList = new ArrayList<>();
                        }
                        provienceItem.setName(paraName);
                    }else if(city.equalsIgnoreCase(name)){
                        cityitem =new CityZone();
                        cityitem.setName(paraName);
                        if(countyList == null){
                            countyList = new ArrayList<>();
                        }
                    }else if(district.equalsIgnoreCase(name)){
                        if(cityitem != null){
                            districtItem = new CityZone();
                            districtItem.setName(paraName);
                            countyList.add(districtItem);
                        }
                    }
                }
                break;

                case XmlPullParser.END_TAG: {
                    String tagName = xmlResourceParser.getName();
                    if(province.equalsIgnoreCase(tagName)){
                        if(provienceItem != null){
                            provienceItem.setZones(cityZoneList);
                            provinceList.add(provienceItem);
                        }
                        cityZoneList = null;
                    }else if(city.equalsIgnoreCase(tagName)){
                        if(cityitem != null){
                            cityitem.setZones(countyList);
                            cityZoneList.add(cityitem);
                        }
                        countyList = null;
                    }
                }
                break;
            }
            eventType = xmlResourceParser.next();
        }
        return provinceList;
    }
}
