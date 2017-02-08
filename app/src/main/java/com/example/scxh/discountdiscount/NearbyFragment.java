package com.example.scxh.discountdiscount;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyErrorHelper;
import com.android.volley.myrequest.UTF8StringRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.scxh.discountdiscount.nearby.NearBy;
import com.example.scxh.discountdiscount.nearby.Info;
import com.example.scxh.discountdiscount.nearby.MerchantKeyList;
import com.example.scxh.discountdiscount.volley.RequestManager;
import com.example.scxh.discountdiscount.menu.CirclesList;
import com.example.scxh.discountdiscount.menu.Menu;
import com.google.gson.Gson;
import com.warmtel.expandtab.ExpandPopTabView;
import com.warmtel.expandtab.KeyValueBean;
import com.warmtel.expandtab.PopOneListView;
import com.warmtel.expandtab.PopTwoListView;
import com.warmtel.xlistview.XListView;

import java.util.ArrayList;
import java.util.List;

import static com.example.scxh.discountdiscount.R.id.XListView;

public class NearbyFragment extends Fragment implements XListView.IXListViewListener {
    private ExpandPopTabView expandTabView;
    private XListView xListView;
    private MyBaseAdapter myBaseAdapter;
    private int pageNo = 0; //页号 ，表示第几页,第一页从0开始
    private int mTotalPageCount = 10; //总页数

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fu_jin_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        xListView = (XListView) getView().findViewById(XListView);
        expandTabView = (ExpandPopTabView) getView().findViewById(R.id.expandtab_view);
        xia();
        myBaseAdapter = new MyBaseAdapter(getContext());
        xListView.setAdapter(myBaseAdapter);
        xListView.setPullLoadEnable(true); //上拉加载更多开关
        xListView.setPullRefreshEnable(true);//下拉刷新开关
        xListView.setXListViewListener(this);
        getDataLists(pageNo);
    }

        /**
         * 网络取文本和图片、Json解析
         *
         * @param pageNo
         */

    public void getDataLists(int pageNo) {
        String httpUrl = "http://www.warmtel.com:8080/around";
        UTF8StringRequest stringRequest = new UTF8StringRequest(Request.Method.GET, httpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        xListView.stopLoadMore();
                        xListView.stopRefresh();
                        xListView.setRefreshTime("刚刚");
                        Gson gson = new Gson();
                        NearBy nearBy = gson.fromJson(response, NearBy.class);
                        Info info = nearBy.getInfo();
                        List<MerchantKeyList> merchantKeyLists = info.getMerchantKey();
                        myBaseAdapter.setMerchantKeyList(merchantKeyLists);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), VolleyErrorHelper.getMessage(error, getContext()), Toast.LENGTH_SHORT).show();
            }
        });
        RequestManager.addRequest(stringRequest, this);
    }


    @Override
    public void onRefresh() {
        pageNo = 0;
        getDataLists(pageNo);
    }

    @Override
    public void onLoadMore() {
        if (++pageNo > mTotalPageCount) {
            pageNo = mTotalPageCount;
            xListView.stopLoadMore();
            Toast.makeText(getContext(), "已加载到最后一页", Toast.LENGTH_SHORT).show();
            return;
        }
        getDataLists(pageNo);
    }

    public void xia() {
        String httpUrl = "http://www.warmtel.com:8080/configs";
        UTF8StringRequest stringRequest = new UTF8StringRequest(Request.Method.GET, httpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Menu menu = gson.fromJson(response, Menu.class);
                        com.example.scxh.discountdiscount.menu.Info info = menu.getInfo();
                        List<KeyValueBean> fujin = info.getDistanceKey();
                        List<KeyValueBean> hangye = info.getIndustryKey();
                        List<KeyValueBean> paixun = info.getSortKey();
                        List<KeyValueBean> mParentLists = new ArrayList<>();//父区域
                        List<ArrayList<KeyValueBean>> mChildrenListLists = new ArrayList<>();//子区域
                        for (CirclesList circlesBean : info.getCirclesList()) {
                            KeyValueBean keyValueBean = new KeyValueBean();
                            keyValueBean.setKey(circlesBean.getKey());
                            keyValueBean.setValue(circlesBean.getValue());
                            mParentLists.add(keyValueBean);
                            mChildrenListLists.add((ArrayList<KeyValueBean>) circlesBean.getCircles());
                        }
                        addItem(expandTabView, fujin, fujin.get(0).getKey(), fujin.get(0).getValue());
                        addItem(expandTabView, hangye, hangye.get(0).getKey(), hangye.get(0).getValue());
                        addItem(expandTabView, paixun, paixun.get(0).getKey(), paixun.get(0).getValue());
                        addItem(expandTabView, mParentLists, mChildrenListLists, mParentLists.get(0).getValue(),
                                mChildrenListLists.get(0).get(0).getKey(), mChildrenListLists.get(0).get(0).getValue());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), VolleyErrorHelper.getMessage(error, getContext()), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setShouldCache(false);
        RequestManager.addRequest(stringRequest, this);
    }

    public void addItem(ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        PopOneListView popOneListView = new PopOneListView(getContext());
        popOneListView.setDefaultSelectByValue(defaultSelect);
        //popViewOne.setDefaultSelectByKey(defaultSelect);
        popOneListView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                //弹出框选项点击选中回调方法
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, popOneListView);
    }

    public void addItem(ExpandPopTabView expandTabView, List<KeyValueBean> parentLists,
                        List<ArrayList<KeyValueBean>> childrenListLists, String defaultParentSelect, String defaultChildSelect, String defaultShowText) {
        PopTwoListView popTwoListView = new PopTwoListView(getContext());
        popTwoListView.setDefaultSelectByValue(defaultParentSelect, defaultChildSelect);
        //distanceView.setDefaultSelectByKey(defaultParent, defaultChild);
        popTwoListView.setCallBackAndData(expandTabView, parentLists, childrenListLists, new PopTwoListView.OnSelectListener() {
            @Override
            public void getValue(String showText, String parentKey, String childrenKey) {
                //弹出框选项点击选中回调方法
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, popTwoListView);
    }


public class MyBaseAdapter extends BaseAdapter {
    List<MerchantKeyList> merchantKeyList = new ArrayList<>();
    LayoutInflater layoutInflater;

    public MyBaseAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    public void setMerchantKeyList(List<MerchantKeyList> merchantKeyList) {
        this.merchantKeyList = merchantKeyList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return merchantKeyList.size();
    }

    @Override
    public Object getItem(int position) {
        return merchantKeyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Mseeages mseeages;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.fujing_listview_layout, null);
            NetworkImageView tupian = (NetworkImageView) convertView.findViewById(R.id.NetworkImageView);
            TextView biaoti = (TextView) convertView.findViewById(R.id.TextView_biaoti);
            TextView youhui = (TextView) convertView.findViewById(R.id.TextView_youhui);
            TextView neirong = (TextView) convertView.findViewById(R.id.TextView_neirong);
            TextView juli = (TextView) convertView.findViewById(R.id.TextView_juli);
            ImageView quan = (ImageView) convertView.findViewById(R.id.ImageView_quan);
            ImageView ka = (ImageView) convertView.findViewById(R.id.ImageView_ka);
            ImageView tuan = (ImageView) convertView.findViewById(R.id.ImageView_tuan);
            mseeages = new Mseeages();
            mseeages.tupian = tupian;
            mseeages.biaoti = biaoti;
            mseeages.youhui = youhui;
            mseeages.neirong = neirong;
            mseeages.juli = juli;
            mseeages.quan = quan;
            mseeages.ka = ka;
            mseeages.tuan = tuan;
            convertView.setTag(mseeages);
        }
        mseeages = (Mseeages) convertView.getTag();
        MerchantKeyList merchantKeyList = (MerchantKeyList) getItem(position);

        mseeages.tupian.setDefaultImageResId(R.drawable.loading_pin);
        mseeages.tupian.setErrorImageResId(R.drawable.t017dd2114dfd5b9410);
        mseeages.tupian.setImageUrl(merchantKeyList.getPicUrl(), RequestManager.getImageLoader());
        mseeages.biaoti.setText(merchantKeyList.getName());
        mseeages.youhui.setText(merchantKeyList.getCoupon());
        mseeages.neirong.setText(merchantKeyList.getLocation());
        mseeages.juli.setText(merchantKeyList.getDistance());
        if (merchantKeyList.getCouponType().equals("YES")) {
            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.near_ticket);
            mseeages.quan.setImageBitmap(icon);
        } else {
            mseeages.quan.setImageBitmap(null);
        }
        if (merchantKeyList.getCardType().equals("YES")) {
            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.near_card);
            mseeages.ka.setImageBitmap(icon);
        } else {
            mseeages.ka.setImageBitmap(null);
        }
        if (merchantKeyList.getGroupType().equals("YES")) {
            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.near_group);
            mseeages.tuan.setImageBitmap(icon);
        } else {
            mseeages.tuan.setImageBitmap(null);
        }
        return convertView;
    }

    class Mseeages {
        NetworkImageView tupian;
        TextView biaoti;
        TextView youhui;
        TextView neirong;
        TextView juli;
        ImageView quan;
        ImageView ka;
        ImageView tuan;
    }
}
}
