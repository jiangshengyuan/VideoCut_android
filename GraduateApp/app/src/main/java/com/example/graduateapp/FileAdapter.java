package com.example.graduateapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class FileItemAdapter extends RecyclerView.Adapter<FileItemAdapter.ViewHolder> {

    private List<FileItem> mFileitemList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View fileView;
        ImageView fileImage;
        TextView fileName;

        public ViewHolder(View view) {
            super(view);
            fileView = view;
            fileImage = view.findViewById(R.id.file_image);
            fileName = view.findViewById(R.id.file_name);
        }
    }

    public FileItemAdapter(List<FileItem> fileList) {
        mFileitemList=fileList;
    }//构造函数

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.fileView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = holder.getAbsoluteAdapterPosition();
                FileItem fileitem = mFileitemList.get(position);
                FileChooseActivity.chosedvideoname = fileitem.getName();//点击响应设置选择文件夹名称
                Toast.makeText(v.getContext(), "您选择了"+fileitem.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.fileImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                int position = holder.getAbsoluteAdapterPosition();
                FileItem fileitem= mFileitemList.get(position);
                FileChooseActivity.chosedvideoname = fileitem.getName();
                Toast.makeText(v.getContext(), "您选择了"+fileitem.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return  holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FileItem fileitem = mFileitemList.get(position);
        holder.fileImage.setImageBitmap(fileitem.getBitmap());
        holder.fileName.setText(fileitem.getName());
    }
    @Override
    public int getItemCount() {
        return mFileitemList.size();
    }
}