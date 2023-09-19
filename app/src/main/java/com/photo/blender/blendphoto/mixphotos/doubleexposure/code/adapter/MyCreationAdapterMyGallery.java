package com.photo.blender.blendphoto.mixphotos.doubleexposure.code.adapter;

import static com.photo.blender.blendphoto.mixphotos.doubleexposure.code.activity.MyCreationScreenActivity.back;
import static com.photo.blender.blendphoto.mixphotos.doubleexposure.code.activity.MyCreationScreenActivity.cancel;
import static com.photo.blender.blendphoto.mixphotos.doubleexposure.code.activity.MyCreationScreenActivity.selectall;
import static com.photo.blender.blendphoto.mixphotos.doubleexposure.code.activity.MyCreationScreenActivity.unselect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.photo.blender.blendphoto.mixer.mixphotos.doubleexposure.R;
import com.photo.blender.blendphoto.mixphotos.doubleexposure.code.activity.MyCreationScreenActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class MyCreationAdapterMyGallery extends RecyclerView.Adapter<MyCreationAdapterMyGallery.ViewHolderMyCreation> {
    List<Uri> listImageSaved = new ArrayList<>();
    private int selectedCount = 0;
    Context context;
    OnClickImage onClickImage;

    boolean[] selected;
    public boolean isSelectionMode = false;

    public static String path = "";

    private HashSet<Uri> selectedUris = new HashSet<>();
    public MyCreationAdapterMyGallery(Context context, List<Uri> listImageSaved, OnClickImage onClickImage) {
        this.context = context;
        this.listImageSaved = listImageSaved;
        this.onClickImage = onClickImage;
        selected = new boolean[listImageSaved.size()];
        Arrays.fill(selected,false);
    }

    @NonNull
    @Override
    public ViewHolderMyCreation onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_creation, parent, false);
        return new ViewHolderMyCreation(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMyCreation holder, @SuppressLint("RecyclerView") int position) {
        Uri uri = listImageSaved.get(position);
        if (selected[position]) {
            Drawable customDrawable = context.getResources().getDrawable(R.drawable.bg_image_selected);
            holder.itemView.setBackgroundDrawable(customDrawable);
            selectedCount++;
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
            selectedCount--;
        }
        Glide.with(this.context).load(uri.toString()).thumbnail(0.1f).dontAnimate().centerCrop().placeholder(R.drawable.imgpsh_fullsize_anim).error(R.drawable.imgpsh_fullsize_anim).into(holder.imgAlbum);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                toggleSelection(position);
                isSelectionMode = true;
                if (getSelectedCount() >= 1) {
                    ((MyCreationScreenActivity) context).showSelectAll();
                    ((MyCreationScreenActivity) context).showClearButton();
                    ((MyCreationScreenActivity) context).showCancel();
                } else {
                    unselect.setVisibility(View.GONE);
                    cancel.setVisibility(View.GONE);
                    selectall.setVisibility(View.GONE);
                    ((MyCreationScreenActivity) context).hideClearButton();
                    back.setVisibility(View.VISIBLE);
                }
                if (getSelectedCount() == listImageSaved.size()) {
                    unselect.setVisibility(View.VISIBLE);
                    selectall.setVisibility(View.GONE);
                    int color = Color.parseColor("#5C5C5C");
                    unselect.setTextColor(color);
                }

                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelectionMode ==true ) {
                    toggleSelection(position);
                    if (getSelectedCount() >= 1) {
                        ((MyCreationScreenActivity) context).showSelectAll();
                        ((MyCreationScreenActivity) context).showClearButton();
                        ((MyCreationScreenActivity) context).showCancel();
                        unselect.setVisibility(View.GONE);
                    } else {
                        cancel.setVisibility(View.GONE);
                        unselect.setVisibility(View.GONE);
                        selectall.setVisibility(View.GONE);
                        ((MyCreationScreenActivity) context).hideClearButton();
                        back.setVisibility(View.VISIBLE);
                        isSelectionMode = false;
                    }
                    if (getSelectedCount() == listImageSaved.size()) {
                        unselect.setVisibility(View.VISIBLE);
                        selectall.setVisibility(View.GONE);
                        int color = Color.parseColor("#5C5C5C");
                        unselect.setTextColor(color);
                    }
                } else {
                    onClickImage.onClickImage(position);
                }
            }
        });

    }
    public void selectAll() {
        for (int i = 0; i < listImageSaved.size(); i++) {
            selected[i] = true;
            selectedUris.add(listImageSaved.get(i));
            notifyItemChanged(i);;
        }
    }

    public void clearSelection() {
        for (int i = 0; i < listImageSaved.size(); i++) {
            selected[i] = false;
            selectedUris.clear();
            notifyItemChanged(i);
            isSelectionMode = false;
        }
    }
    public void clearSelection1() {
        for (int i = 0; i < listImageSaved.size(); i++) {
            selected[i] = false;
            selectedUris.clear();
            notifyItemChanged(i);
            isSelectionMode = true;
        }
    }
    public void toggleSelection(int position) {
        selected[position] = !selected[position];
        Uri uri = listImageSaved.get(position);
        if (selected[position]) {
            selectedUris.add(uri);
        } else {
            unselect.setVisibility(View.GONE);
            selectedUris.remove(uri);

        }
        notifyItemChanged(position);
    }
    public void removeSelectedItems() {
        listImageSaved.removeAll(selectedUris);
        notifyDataSetChanged();
        clearSelection();
        isSelectionMode = false;
        selectedUris.clear();
    }
    public int getSelectedCount() {
        return selectedUris.size();
    }
    public ArrayList<Uri> getSelectedUris() {
        ArrayList<Uri> selectedUris = new ArrayList<>(this.selectedUris);
        for (Uri uri: selectedUris){
              path = uri.getPath();
        }
        return selectedUris;
    }

    @Override
    public int getItemCount() {
        return listImageSaved.size();
    }


    public class ViewHolderMyCreation extends RecyclerView.ViewHolder {
        ImageView imgAlbum;


        public ViewHolderMyCreation(@NonNull View itemView) {
            super(itemView);
            imgAlbum = itemView.findViewById(R.id.itemImg);
        }
    }

    public interface OnClickImage {
        void onClickImage(int pos);
    }
}
