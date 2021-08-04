package com.sp.igeeti.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sp.igeeti.R;
import com.sp.igeeti.listener.ClickItem;
import com.sp.igeeti.utils.MusicMetaData;

import java.util.ArrayList;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {
    ArrayList<String> songs;
    ClickItem listener;
    ArrayList<String> files;

    public ViewAdapter(ArrayList<String> songs,ArrayList<String> files,ClickItem listener) {
        this.songs = songs;
        this.listener = listener;
        this.files = files;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv.setText(songs.get(position));
        holder.artistName.setText(MusicMetaData.getArtistName(files.get(position)));
        MusicMetaData.setImageIcon(holder.iconImage,files.get(position));
        holder.recyclerItem.setOnClickListener(v -> listener.OnClick(position,files));
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView tv,artistName;
        ImageView iconImage;
        ConstraintLayout recyclerItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.song_text);
            artistName = itemView.findViewById(R.id.artistNameItem);
            iconImage = itemView.findViewById(R.id.iconImage);
            recyclerItem = itemView.findViewById(R.id.recyclerItem);
        }
    }
}
