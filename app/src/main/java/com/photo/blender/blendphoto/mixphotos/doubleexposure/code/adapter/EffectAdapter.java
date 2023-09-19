package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.R;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.bitmap.BitmapLoader;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.other.DisplayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EffectAdapter extends Adapter<EffectAdapter.ViewHolder> implements OnClickListener {
    private Context context;
    private List<String> listColor;
    private String[] listItem;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private Typeface typeface;
    private int width;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String str);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public TextView text;

        public ViewHolder(View view) {
            super(view);
            this.image = (ImageView) view.findViewById(pl.aprilapps.easyphotopicker.R.id.image);

            this.text = (TextView) view.findViewById(pl.aprilapps.easyphotopicker.R.id.text);
        }
    }

    public EffectAdapter(String[] listItem, Context ct, int width) {
        this.listItem = listItem;
        this.context = ct;
        this.width = width - DisplayUtil.dip2px(this.context, 15.0f);
        this.typeface = Typeface.createFromAsset(this.context.getAssets(), "fonts/Roboto-Light.ttf");
        this.listColor = new ArrayList(Arrays.asList(new String[]{"#5508c8", "#3f97e9", "#e4c120", "#c82970", "#e49820", "#949494", "#e46f20", "#ba0bc0", "#3fa3c3", "#3fc3b2", "#3fc390", "#d2434d", "#7fa31e", "#3f6ec3", "#8f08c8", "#a3951e", "#c00b98", "#1ea340"}));
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_effect, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.image.setImageBitmap(BitmapLoader.loadFromAsset(this.context, new int[]{200, 200}, this.listItem[position]));
        LayoutParams layoutParams = new LayoutParams(this.width, this.width / 4);
        layoutParams.addRule(12);


        viewHolder.itemView.setTag(this.listItem[position]);
        if (position == 0) {
            viewHolder.text.setText("orignal");
        } else {
            viewHolder.text.setText("Effect" + position);

        }

        viewHolder.text.setBackgroundColor(Color.parseColor((String) this.listColor.get(position % this.listColor.size())));
        viewHolder.text.setTypeface(this.typeface);
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
