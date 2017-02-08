package com.example.scxh.discountdiscount;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyErrorHelper;
import com.android.volley.myrequest.UTF8StringRequest;
import com.example.scxh.discountdiscount.volley.RequestManager;
import com.example.scxh.discountdiscount.findcheap.AdvertisingKeyList;
import com.example.scxh.discountdiscount.findcheap.HotkeyList;
import com.example.scxh.discountdiscount.findcheap.FindCheap;
import com.google.gson.Gson;
import com.scxh.slider.library.SliderLayout;
import com.scxh.slider.library.SliderTypes.BaseSliderView;
import com.scxh.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.List;

public class FindCheapFragment extends Fragment {
    MyBaseAdapter myBaseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.zhao_pian_yi_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final SliderLayout mSliderLayout = (SliderLayout) getView().findViewById(R.id.SliderLayout);
        final GridView gridView = (GridView) getView().findViewById(R.id.GridView);
        myBaseAdapter = new MyBaseAdapter(getContext());
        gridView.setAdapter(myBaseAdapter);
        String httpUrl = "http://www.warmtel.com:8080/keyConfig";
        UTF8StringRequest stringRequest = new UTF8StringRequest(Request.Method.GET, httpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        FindCheap findCheap = gson.fromJson(response, FindCheap.class);
                        com.example.scxh.discountdiscount.findcheap.Info info = findCheap.getInfo();
                        List<AdvertisingKeyList> advertisingKey = info.getAdvertisingKey();
                        List<HotkeyList> hotkeyLists = info.getHotkey();
                        if (mSliderLayout != null) {
                            mSliderLayout.removeAllSliders();
                        }
                        for (AdvertisingKeyList jpg : advertisingKey) {
                            TextSliderView textSliderView = new TextSliderView(getContext());
                            textSliderView
                                    .description(jpg.getDescription())
//                                    .image(jpg.getPicUrl())
                                    .image(R.drawable.naryou_logo)
                                    .setScaleType(BaseSliderView.ScaleType.Fit);
                            mSliderLayout.addSlider(textSliderView);
                        }
                        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
                        myBaseAdapter.setLists(hotkeyLists);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), VolleyErrorHelper.getMessage(error, getContext()), Toast.LENGTH_SHORT).show();
            }
        });
        RequestManager.addRequest(stringRequest, this);
    }

    public class MyBaseAdapter extends BaseAdapter {
        List<HotkeyList> lists = new ArrayList<>();
        LayoutInflater layoutInflater;

        MyBaseAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        public void setLists(List<HotkeyList> lists) {
            this.lists = lists;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.zhaopianyi_listview, null);
                TextView  textView = (TextView) convertView.findViewById(R.id.TextView);
                HotkeyList hotkeyList = (HotkeyList) getItem(position);
                textView.setText(hotkeyList.getKey());
            }
            return convertView;
        }
    }
}
