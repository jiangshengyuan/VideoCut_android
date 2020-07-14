package com.example.graduateapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private List<Video> mVideoList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View videoView;
        ImageView videoImage;
        public ViewHolder(View view) {
            super(view);
            videoView = view;
            videoImage = view.findViewById(R.id.id_video_image);
        }
    }

    public VideoAdapter(List<Video> videoList) {
        mVideoList = videoList;
    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.videoView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                int position= holder.getBindingAdapterPosition();
                MainActivity.vv.pause();
                MainActivity.video_play_stop_status=0;
                MainActivity.vv.setVisibility(View.INVISIBLE);
                MainActivity.image.setVisibility(View.VISIBLE);
                MainActivity.image.setImageBitmap(MainActivity.video_list.get(position).getBitmap());
                MainActivity.choose_item_positoin=position;
                int temp=0;
                for(int i=0;i<MainActivity.video_choose_list.size();i++){
                    if(MainActivity.video_choose_list.get(i)==position){
                        temp=1;
                    }
                }
                if(temp==0){
                    MainActivity.video_choose_list.add(MainActivity.video_list.get(position).getPosition());
                    MainActivity.videoadapter.notifyDataSetChanged();
                    Toast.makeText(v.getContext(), "你选择了第"+(position+1)+"个视频", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(v.getContext(), "该视频已被选择", Toast.LENGTH_SHORT).show();


            }
        });
        holder.videoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position= holder.getBindingAdapterPosition();
                MainActivity.vv.pause();
                MainActivity.video_play_stop_status=0;
                MainActivity.vv.setVisibility(View.INVISIBLE);
                MainActivity.image.setVisibility(View.VISIBLE);
                MainActivity.image.setImageBitmap(MainActivity.video_list.get(position).getBitmap());
                MainActivity.choose_item_positoin=position;
                int temp=0;
                for(int i=0;i<MainActivity.video_choose_list.size();i++){
                    if(MainActivity.video_choose_list.get(i)==position){
                        temp=1;
                    }
                }
                if(temp==0){
                    MainActivity.video_choose_list.add(MainActivity.video_list.get(position).getPosition());
                    MainActivity.videoadapter.notifyDataSetChanged();
                    Toast.makeText(v.getContext(), "你选择了第"+(position+1)+"个视频", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(v.getContext(), "该视频已被选择", Toast.LENGTH_SHORT).show();
            }
        });
        return  holder;
    }
    @Override
    public void onBindViewHolder(VideoAdapter.ViewHolder holder, int position) {
        Video video = mVideoList.get(position);
        holder.videoImage.setImageBitmap(video.getBitmap());
    }
    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

}

