package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.R;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.bitmap.BitmapLoader;


public class GalleryAdapterEffect extends Adapter<GalleryAdapterEffect.ViewHolder> implements OnClickListener {
    private Context context;
    private String[] listItem;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private int selectedPosition = -1;
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String str);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView,imageViewClick;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.item_sticker);
            imageViewClick = (ImageView) view.findViewById(R.id.item_sticker_click);
        }
    }

    public GalleryAdapterEffect(String[] listItem, Context ct,int selectedPosition) {
        this.listItem = listItem;
        this.context = ct;
        this.selectedPosition = selectedPosition;
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gallery, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);

        return vh;
    }

    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        viewHolder.imageView.setImageBitmap(BitmapLoader.loadFromAsset(this.context, new int[]{200, 200}, this.listItem[position]));
        if (selectedPosition == position) {
            viewHolder.imageViewClick.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imageViewClick.setVisibility(View.INVISIBLE);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                notifyDataSetChanged();
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, (String) v.getTag());
                }
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("selected_position", selectedPosition);
                editor.apply();
            }
        });
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
