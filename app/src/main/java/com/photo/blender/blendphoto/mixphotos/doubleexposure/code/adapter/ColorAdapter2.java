package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build.VERSION;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.R;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.other.ColorCircleDrawable;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.other.DisplayUtil;
import com.yalantis.ucrop.view.CropImageView;


public class ColorAdapter2 extends Adapter<ColorAdapter2.ViewHolder> implements OnClickListener {
    private Context context;
    private String[] listItem;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private int margin;
    private int width;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String str);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public ViewHolder(View view) {
            super(view);
            this.image = (ImageView) view.findViewById(R.id.item_sticker);
        }
    }

    public ColorAdapter2(String[] listItem, Context ct, int width) {
        this.listItem = listItem;
        this.context = ct;
        this.width = width;
        this.margin = DisplayUtil.dip2px(this.context, CropImageView.DEFAULT_MAX_SCALE_MULTIPLIER);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gallery, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        LayoutParams layoutParams = new LayoutParams(this.width - (this.margin * 2), this.width - (this.margin * 2));
        layoutParams.setMargins(this.margin, this.margin, 0, this.margin);
        viewHolder.image.setLayoutParams(layoutParams);
        if (VERSION.SDK_INT < 16) {
            viewHolder.image.setBackgroundDrawable(new ColorCircleDrawable(Color.parseColor(this.listItem[position])));
        } else {
            viewHolder.image.setBackground(new ColorCircleDrawable(Color.parseColor(this.listItem[position])));
        }
        viewHolder.itemView.setTag(this.listItem[position]);
    }

    public void onClick(View v) {
        if (this.mOnItemClickListener != null) {
            this.mOnItemClickListener.onItemClick(v, (String) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public int getItemCount() {
        return this.listItem.length;
    }
}
