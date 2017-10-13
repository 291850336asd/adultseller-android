package adult.mas.com.adultgoodssell.modelbean.mainview.zone;

import java.util.List;

/**
 * Created by sunmeng on 17/8/20.
 */

public class CityZone {

    private String name;
    private List<CityZone> zones;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityZone> getZones() {
        return zones;
    }

    public void setZones(List<CityZone> zones) {
        this.zones = zones;
    }
}
