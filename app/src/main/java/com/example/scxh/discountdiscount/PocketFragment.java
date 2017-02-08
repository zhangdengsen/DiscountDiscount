package com.example.scxh.discountdiscount;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyErrorHelper;
import com.android.volley.myrequest.UTF8StringRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.scxh.discountdiscount.teyou.Info;
import com.example.scxh.discountdiscount.teyou.MainKeyList;
import com.example.scxh.discountdiscount.teyou.TeYou;
import com.example.scxh.discountdiscount.volley.RequestManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PocketFragment extends Fragment {
    MyBaseAdapter myBaseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.kou_dai_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView listView = (ListView) getView().findViewById(R.id.ListView);
        myBaseAdapter = new MyBaseAdapter(getContext());
        listView.setAdapter(myBaseAdapter);
        String httpUrl = "http://www.warmtel.com:8080/maininit";
        UTF8StringRequest stringRequest = new UTF8StringRequest(Request.Method.GET, httpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        TeYou teYou = gson.fromJson(response, TeYou.class);
                        Info info = teYou.getInfo();
                        List<MainKeyList> mainKeyLists = info.getMainKey();
                        myBaseAdapter.setLists(mainKeyLists);
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
        List<MainKeyList> lists = new ArrayList<>();
        LayoutInflater layoutInflater;

        MyBaseAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        public void setLists(List<MainKeyList> lists) {
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
            Messages messages;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.koudai_listview, null);
                NetworkImageView imageView = (NetworkImageView) convertView.findViewById(R.id.NetworkImageView);
                messages = new Messages();
                messages.imageView = imageView;
                convertView.setTag(messages);
            }
            messages= (Messages) convertView.getTag();
            MainKeyList mainKeyList = (MainKeyList) getItem(position);
            messages.imageView.setDefaultImageResId(R.drawable.loading_pin);
            messages. imageView.setErrorImageResId(R.drawable.t017dd2114dfd5b9410);
            messages. imageView.setImageUrl(mainKeyList.getPicUrl(), RequestManager.getImageLoader());
            return convertView;
        }
        class Messages {
            NetworkImageView imageView;
        }
    }
}
