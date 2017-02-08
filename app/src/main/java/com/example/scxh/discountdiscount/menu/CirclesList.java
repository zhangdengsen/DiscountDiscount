package com.example.scxh.discountdiscount.menu;


import com.warmtel.expandtab.KeyValueBean;

import java.util.List;

public class CirclesList {
    String key;
    String value;
    List<KeyValueBean> circles;

    public List<KeyValueBean> getCircles() {
        return circles;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
