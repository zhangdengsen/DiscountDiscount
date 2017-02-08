package com.example.scxh.discountdiscount;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private Fragment mFuJinFragment, mZhaoPianYiFragment, mTeHuiFragment, mKouDaiFragment, mGengDuoFragment;
    private LinearLayout mLinearLayout_fujin, mmLinearLayout_zhaopianyi, mLinearLayout_tehui, mLinearLayout_koudia, mLinearLayout_gengduo;
    private ImageView mImageView_fujin, mImageView_zhaopianyi,mImageView_tehui, mImageView_koudai, mImageView_gengduo;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_pager, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPager = (ViewPager) getView().findViewById(R.id.ViewPager);
        mLinearLayout_fujin = (LinearLayout) getView().findViewById(R.id.LinearLayout_fijin);
        mmLinearLayout_zhaopianyi = (LinearLayout) getView().findViewById(R.id.LinearLayout_zhaopianyi);
        mLinearLayout_tehui = (LinearLayout) getView().findViewById(R.id.LinearLayout_tehui);
        mLinearLayout_koudia = (LinearLayout) getView().findViewById(R.id.LinearLayout_koudai);
        mLinearLayout_gengduo = (LinearLayout) getView().findViewById(R.id.LinearLayout_gengduo);
        mImageView_fujin = (ImageView) getView().findViewById(R.id.ImageView_fujin);
        mImageView_zhaopianyi = (ImageView) getView().findViewById(R.id.ImageView_zhaopianyi);
        mImageView_tehui = (ImageView) getView().findViewById(R.id.ImageView_tehui);
        mImageView_koudai = (ImageView) getView().findViewById(R.id.ImageView_koudai);
        mImageView_gengduo = (ImageView) getView().findViewById(R.id.ImageView_genduo);
        List<Fragment> mDataLists = getDataLists();
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(myViewPagerAdapter);
        myViewPagerAdapter.setDataLists(mDataLists);

        mLinearLayout_fujin.setBackgroundResource(R.drawable.tab_bottom_bg_selected);
        mImageView_fujin.setImageResource(R.drawable.tab_near_s);
        viewPager.addOnPageChangeListener(this);
        mLinearLayout_fujin.setOnClickListener(this);
        mmLinearLayout_zhaopianyi.setOnClickListener(this);
        mLinearLayout_tehui.setOnClickListener(this);
        mLinearLayout_koudia.setOnClickListener(this);
        mLinearLayout_gengduo.setOnClickListener(this);
        mImageView_fujin.setOnClickListener(this);
        mImageView_zhaopianyi.setOnClickListener(this);
        mImageView_tehui.setOnClickListener(this);
        mImageView_koudai.setOnClickListener(this);
        mImageView_gengduo.setOnClickListener(this);
    }

    /**
     * 设置ViewPager数据源
     */
    public List<Fragment> getDataLists() {
        List<Fragment> mDataLists = new ArrayList<>();
        mFuJinFragment = new NearbyFragment();
        mZhaoPianYiFragment = new FindCheapFragment();
        mTeHuiFragment = new TeYouFragment();
        mKouDaiFragment = new PocketFragment();
        mGengDuoFragment = new MoreFragment();
        mDataLists.add(mFuJinFragment);
        mDataLists.add(mZhaoPianYiFragment);
        mDataLists.add(mTeHuiFragment);
        mDataLists.add(mKouDaiFragment);
        mDataLists.add(mGengDuoFragment);
        return mDataLists;
    }

    @Override
    public void onClick(View v) {
        clearBackgroundColor();
        switch (v.getId()) {
            case R.id.ImageView_fujin:
                mLinearLayout_fujin.setBackgroundResource(R.drawable.tab_bottom_bg_selected);
                mImageView_fujin.setImageResource(R.drawable.tab_near_s);
                viewPager.setCurrentItem(0);
                break;
            case R.id.ImageView_zhaopianyi:
                mmLinearLayout_zhaopianyi.setBackgroundResource(R.drawable.tab_bottom_bg_selected);
                mImageView_zhaopianyi.setImageResource(R.drawable.tab_cheap_s);
                viewPager.setCurrentItem(1);
                break;
            case R.id.ImageView_tehui:
                mLinearLayout_tehui.setBackgroundResource(R.drawable.tab_bottom_bg_selected);
                viewPager.setCurrentItem(2);
                break;
            case R.id.ImageView_koudai:
                mLinearLayout_koudia.setBackgroundResource(R.drawable.tab_bottom_bg_selected);
                mImageView_koudai.setImageResource(R.drawable.tab_pocket_s);
                viewPager.setCurrentItem(3);
                break;
            case R.id.ImageView_genduo:
                mLinearLayout_gengduo.setBackgroundResource(R.drawable.tab_bottom_bg_selected);
                mImageView_gengduo.setImageResource(R.drawable.tab_more_s);
                viewPager.setCurrentItem(4);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        clearBackgroundColor();
        switch (position) {
            case 0:
                mLinearLayout_fujin.setBackgroundResource(R.drawable.tab_bottom_bg_selected);
                mImageView_fujin.setImageResource(R.drawable.tab_near_s);
                break;
            case 1:
                mmLinearLayout_zhaopianyi.setBackgroundResource(R.drawable.tab_bottom_bg_selected);
                mImageView_zhaopianyi.setImageResource(R.drawable.tab_cheap_s);
                break;
            case 2:
                mLinearLayout_tehui.setBackgroundResource(R.drawable.tab_bottom_bg_selected);
                break;
            case 3:
                mLinearLayout_koudia.setBackgroundResource(R.drawable.tab_bottom_bg_selected);
                mImageView_koudai.setImageResource(R.drawable.tab_pocket_s);
                break;
            case 4:
                mLinearLayout_gengduo.setBackgroundResource(R.drawable.tab_bottom_bg_selected);
                mImageView_gengduo.setImageResource(R.drawable.tab_more_s);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void clearBackgroundColor() {
        mLinearLayout_fujin.setBackgroundResource(android.R.color.white);
        mImageView_fujin.setImageResource(R.drawable.tab_near);

        mmLinearLayout_zhaopianyi.setBackgroundResource(android.R.color.white);
        mImageView_zhaopianyi.setImageResource(R.drawable.tab_cheap);

        mLinearLayout_tehui.setBackgroundResource(android.R.color.white);

        mLinearLayout_koudia.setBackgroundResource(android.R.color.white);
        mImageView_koudai.setImageResource(R.drawable.tab_pocket);

        mLinearLayout_gengduo.setBackgroundResource(android.R.color.white);
        mImageView_gengduo.setImageResource(R.drawable.tab_more);
    }

    class MyViewPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> dataLists = new ArrayList<>();

        private void setDataLists(List<Fragment> list) {
            this.dataLists = list;
            notifyDataSetChanged();
        }

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return dataLists.get(position);
        }

        @Override
        public int getCount() {
            return dataLists.size();
        }
    }
}
