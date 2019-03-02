package com.example.inmoapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.inmoapp.Model.Photo;
import com.example.inmoapp.R;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<String> images;
    private TextView positionPhoto;

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    public ViewPagerAdapter(Context context, List<String> images, TextView positionPhoto) {
        this.context = context;
        this.images = images;
        this.positionPhoto = positionPhoto;
    }

    @Override
    public int getCount() {

        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {

        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        positionPhoto.findViewById(R.id.position);

        View view = layoutInflater.inflate(R.layout.custom_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        Glide
                .with(context)
                .load(images.get(position))
                .into(imageView);

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);

        positionPhoto.setText("" + position +"/" + images.size());

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
