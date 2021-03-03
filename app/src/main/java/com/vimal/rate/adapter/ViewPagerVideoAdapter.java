package com.vimal.rate.adapter;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vimal.rate.R;

import java.util.ArrayList;


public class ViewPagerVideoAdapter extends RecyclerView.Adapter<ViewPagerVideoAdapter.VideoViewHolder> {

    private ArrayList<String> videoItems;
    public static MediaPlayer mMediaPlayer;

    public ViewPagerVideoAdapter(ArrayList<String> videoItems) {
        this.videoItems = videoItems;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_videopager,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.setVideoData(videoItems.get(position));
    }

    @Override
    public int getItemCount() {
        return videoItems.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {

        VideoView videoView;
        ProgressBar videoProgressBar;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            videoProgressBar = itemView.findViewById(R.id.videoProgressBar);
        }

        void setVideoData(String path){
            videoView.setVideoPath(path);

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    videoProgressBar.setVisibility(View.GONE);
                    mMediaPlayer = mp;
                    mMediaPlayer.start();
//                    float videoRatio = mp.getVideoWidth() / (float) mp.getVideoWidth();
//                    float screenRatio = videoView.getWidth() / (float) videoView.getHeight();
//                    float scale = videoRatio / screenRatio;
//                    if (scale >= 1f){
//                        videoView.setScaleX(scale);
//                    }else {
//                        videoView.setScaleY(1f / scale);
//                    }
                }
            });
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mMediaPlayer.start();
                }
            });

            videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.pause();
                    } else {
                        mMediaPlayer.start();
                    }
                }
            });
        }
    }
    public void removeView(int position) {
        videoItems.remove(position);
        notifyDataSetChanged();
    }
}
