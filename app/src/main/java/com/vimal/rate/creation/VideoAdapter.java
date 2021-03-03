package com.vimal.rate.creation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vimal.rate.R;
import com.vimal.rate.activity.PreviewActivity;

import java.util.ArrayList;




public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<String> data = new ArrayList<>();

    public VideoAdapter(ArrayList<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_video_adapter, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {
//        final Status status = list.get(position);
//        holder.imageView.setImageBitmap(status.getThumbnail());

        Glide.with(context)
                .load(data.get(position))
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imageView);


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PreviewActivity.class);
//                Intent intent = new Intent(context, ShowZoomActivity.class);
                intent.putExtra("path", data.get(position));
                intent.putExtra("position", position);
                intent.putExtra("arraylist", data);
                context.startActivity(intent);
            }
        });


    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.ivThumbnail);

        }
    }

    @Override
    public int getItemCount( ) {
        return data.size();
    }

}
