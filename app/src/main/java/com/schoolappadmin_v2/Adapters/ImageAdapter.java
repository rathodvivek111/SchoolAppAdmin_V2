package com.schoolappadmin_v2.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.schoolappadmin_v2.R;
import com.schoolappadmin_v2.Models.ModelPictures;
import com.schoolappadmin_v2.utility.ZoomableImageView;

import java.util.ArrayList;

/**
 * Created by RATHOD'S on 6/17/2017.
 */

public class ImageAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<ModelPictures> list = new ArrayList<>();

    public ImageAdapter(Context context, ArrayList<ModelPictures> listImages) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list.addAll(listImages);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ZoomableImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.raw_image, container, false);
        ZoomableImageView imageView = (ZoomableImageView) itemView.findViewById(R.id.img);
        Glide
                .with(mContext)
                .load(list.get(position).getUrl())
                .placeholder(R.drawable.img_placeholder)
                .into(imageView);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ZoomableImageView) object);
    }
}
