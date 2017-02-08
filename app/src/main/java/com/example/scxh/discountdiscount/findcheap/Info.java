package com.example.scxh.discountdiscount.findcheap;


import java.util.List;

public class Info {
    List<AdvertisingKeyList> advertisingKey;
    List<HotkeyList> hotkey;

    public List<HotkeyList> getHotkey() {
        return hotkey;
    }

    public void setHotkey(List<HotkeyList> hotkey) {
        this.hotkey = hotkey;
    }

    public List<AdvertisingKeyList> getAdvertisingKey() {
        return advertisingKey;
    }

    public void setAdvertisingKey(List<AdvertisingKeyList> advertisingKey) {
        this.advertisingKey = advertisingKey;
    }
}
