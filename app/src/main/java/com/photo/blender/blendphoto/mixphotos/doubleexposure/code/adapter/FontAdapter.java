package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.R;

public class FontAdapter extends Adapter<FontAdapter.ViewHolder> implements OnClickListener {
    private Context context;
    private String[] listItem;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private int selectedPosition = -1;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String str);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        TextView text1;

        public ViewHolder(View view) {
            super(view);
            this.text = (TextView) view.findViewById(pl.aprilapps.easyphotopicker.R.id.text);
            text1 = view.findViewById(R.id.text1);
        }
    }

    public FontAdapter(String[] listItem, Context ct) {
        this.listItem = listItem;
        this.context = ct;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_font, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        if (selectedPosition == position) {
            viewHolder.text1.setVisibility(View.VISIBLE);
        } else {
            viewHolder.text1.setVisibility(View.INVISIBLE);
        }
        viewHolder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = position;
                notifyDataSetChanged();
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, (String) view.getTag());
                }
            }
        });
        viewHolder.text.setTypeface(Typeface.createFromAsset(this.context.getAssets(), this.listItem[position]));
        viewHolder.text1.setTypeface(Typeface.createFromAsset(this.context.getAssets(), this.listItem[position]));
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
