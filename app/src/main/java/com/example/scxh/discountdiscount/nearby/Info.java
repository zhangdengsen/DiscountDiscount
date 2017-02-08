package com.example.scxh.discountdiscount.nearby;


import java.util.List;

public class Info {
    PageInfo pageInfo;
    List<MerchantKeyList> merchantKey;

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<MerchantKeyList> getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(List<MerchantKeyList> merchantKey) {
        this.merchantKey = merchantKey;
    }


}
