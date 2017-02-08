package com.example.scxh.discountdiscount.menu;


import com.warmtel.expandtab.KeyValueBean;

import java.util.List;

public class Info {
    List<CirclesList> areaKey;
    List<KeyValueBean> distanceKey;
    List<KeyValueBean> industryKey;
    List<KeyValueBean> sortKey;

    public List<CirclesList> getCirclesList() {
        return areaKey;
    }

    public List<KeyValueBean> getDistanceKey() {
        return distanceKey;
    }

    public void setDistanceKey(List<KeyValueBean> distanceKey) {
        this.distanceKey = distanceKey;
    }

    public List<KeyValueBean> getIndustryKey() {
        return industryKey;
    }

    public void setIndustryKey(List<KeyValueBean> industryKey) {
        this.industryKey = industryKey;
    }

    public List<KeyValueBean> getSortKey() {
        return sortKey;
    }

    public void setSortKey(List<KeyValueBean> sortKey) {
        this.sortKey = sortKey;
    }


}
