package com.example.adamkhar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;


public class ViewPagerAdapter extends PagerAdapter {

    private Integer[] layouts;
    private Context context;
    private LayoutInflater layoutInflater;

    public ViewPagerAdapter(Integer[] layouts,Context context){
        this.layouts = layouts;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull View container, int position) {
        View view = layoutInflater.inflate(layouts[position], (ViewGroup) container,false);
        ((ViewGroup) container).addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}