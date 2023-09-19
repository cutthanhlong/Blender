package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.R;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.bitmap.BitmapLoader;


public class GalleryAdapterSticker extends Adapter<GalleryAdapterSticker.ViewHolder> implements OnClickListener {
    private Context context;
    private String[] listItem;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String str);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.item_sticker);
        }
    }

    public GalleryAdapterSticker(String[] listItem, Context ct) {
        this.listItem = listItem;
        this.context = ct;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gallery1, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.imageView.setImageBitmap(BitmapLoader.loadFromAsset(this.context, new int[]{200, 200}, this.listItem[position]));
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
