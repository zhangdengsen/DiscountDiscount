package com.example.scxh.discountdiscount;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyErrorHelper;
import com.android.volley.myrequest.UTF8StringRequest;
import com.example.scxh.discountdiscount.teyou.Info;
import com.example.scxh.discountdiscount.teyou.MainKeyList;
import com.example.scxh.discountdiscount.teyou.TeYou;
import com.example.scxh.discountdiscount.volley.RequestManager;
import com.google.gson.Gson;
import com.scxh.slider.library.SliderLayout;
import com.scxh.slider.library.SliderTypes.BaseSliderView;
import com.scxh.slider.library.SliderTypes.TextSliderView;

import java.util.List;

public class TeYouFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.te_you_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final SliderLayout mSliderLayout = (SliderLayout) getView().findViewById(R.id.SliderLayout);
        String httpUrl = "http://www.warmtel.com:8080/maininit";
        UTF8StringRequest stringRequest = new UTF8StringRequest(Request.Method.GET, httpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        TeYou teYou = gson.fromJson(response, TeYou.class);
                        Info info = teYou.getInfo();
                        List<MainKeyList> mainKeyLists = info.getMainKey();
                        if (mSliderLayout != null) {
                            mSliderLayout.removeAllSliders();
                        }
                        for (MainKeyList jpg : mainKeyLists) {
                            TextSliderView textSliderView = new TextSliderView(getContext());
                            textSliderView
                                    .description(jpg.getDescription())
                                    .image(jpg.getBigpicUrl())
                                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);
                            mSliderLayout.addSlider(textSliderView);
                        }
                        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), VolleyErrorHelper.getMessage(error, getContext()), Toast.LENGTH_SHORT).show();
            }
        });
        RequestManager.addRequest(stringRequest, this);
    }
}
