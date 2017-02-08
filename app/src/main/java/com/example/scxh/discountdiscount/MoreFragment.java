package com.example.scxh.discountdiscount;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class MoreFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.geng_duo_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GridView gridView = (GridView) getView().findViewById(R.id.GridView);
        String[] from = {"图片", "标题"};
        ArrayList list = getListData();
        int[] to = {R.id.ImageView_tupian, R.id.TextView_biaoti};
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), list, R.layout.gengduo_gridview_layout, from, to);
        gridView.setAdapter(simpleAdapter);
    }

    public ArrayList getListData() {
        ArrayList<HashMap<String, Object>> listData = new ArrayList();
        HashMap<String, Object> item = new HashMap<String, Object>();
        item.put("图片", R.drawable.gerenzhongxin);
        item.put("标题", "个人中心");
        listData.add(item);
        item = new HashMap<String, Object>();
        item.put("图片", R.drawable.wodiguanzhu);
        item.put("标题", "我的关注");
        listData.add(item);
        item = new HashMap<String, Object>();
        item.put("图片", R.drawable.zuijinliulan);
        item.put("标题", "最近浏览");
        listData.add(item);
        item = new HashMap<String, Object>();
        item.put("图片", R.drawable.guanyuwomen);
        item.put("标题", "关于我们");
        listData.add(item);
        item = new HashMap<String, Object>();
        item.put("图片", R.drawable.xitongxinxi);
        item.put("标题", "系统信息");
        listData.add(item);
        item = new HashMap<String, Object>();
        item.put("图片", R.drawable.xuanxiangshezhi);
        item.put("标题", "选项设置");
        listData.add(item);
        item = new HashMap<String, Object>();
        item.put("图片", R.drawable.shangjiafabu);
        item.put("标题", "商家发布");
        listData.add(item);
        item = new HashMap<String, Object>();
        item.put("图片", R.drawable.yijianfangkui);
        item.put("标题", "意见反馈");
        listData.add(item);
        return listData;
    }
}
