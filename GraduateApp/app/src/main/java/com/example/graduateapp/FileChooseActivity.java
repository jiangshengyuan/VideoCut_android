package com.example.graduateapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.media.ThumbnailUtils.createVideoThumbnail;

public class FileChooseActivity extends AppCompatActivity {
    public static String chosedvideoname="";
    private static String rootpath= Environment.getExternalStorageDirectory().getAbsolutePath();

    private static String videoroot = rootpath+"/videocut/choose";
//    private static File videoroot = new File(rootpath);
    public static List<FileItem> video_file_list=new ArrayList<>();
    public static RecyclerView recyclerview_video_choose;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_choose);
        video_file_list.clear();
        GetVideoFileList(video_file_list,rootpath);
        if(video_file_list.size()==0)
            Toast.makeText(FileChooseActivity.this, "目录下无文件", Toast.LENGTH_SHORT).show();
        Button btn_back = findViewById(R.id.btn_back);//取消选择
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FileChooseActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Button btn_confirm = findViewById(R.id.btn_confirm);//文件选择则确认
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FileChooseActivity.this,MainActivity.class);
                if(chosedvideoname==""){Toast.makeText(FileChooseActivity.this, "未选择", Toast.LENGTH_SHORT).show();}
                else {
                    String videochoosepath=chosedvideoname;

                    Toast.makeText(FileChooseActivity.this, "正在导入，请稍等", Toast.LENGTH_SHORT).show();
                    try {
                        if(fileCopy(videochoosepath,rootpath+"/videocut/videolist/-1.mp4")){
                            Toast.makeText(FileChooseActivity.this, "导入成功", Toast.LENGTH_SHORT).show();
                        }
                        else Toast.makeText(FileChooseActivity.this, "导入失败", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                intent.putExtra("data_return",rootpath+"/videocut/videolist/"+"-1.mp4");
                chosedvideoname="";
                setResult(RESULT_OK,intent);
                finish();

            }
        });
        recyclerview_video_choose = findViewById(R.id.file_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this); // Example of a call to a native method
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview_video_choose.setLayoutManager(layoutManager);
        FileItemAdapter fileadapter = new FileItemAdapter(video_file_list);
        recyclerview_video_choose.setAdapter(fileadapter);
    }
    public static boolean fileCopy(String oldFilePath,String newFilePath) throws IOException {
        //如果原文件不存在
        if(!fileExists(oldFilePath)){
            return false;
        }
        //获得原文件流
        FileInputStream inputStream = new FileInputStream(new File(oldFilePath));
        byte[] data = new byte[1024];
        //输出流
        FileOutputStream outputStream =new FileOutputStream(new File(newFilePath));
        //开始处理流
        while (inputStream.read(data) != -1) {
            outputStream.write(data);
        }
        inputStream.close();
        outputStream.close();
        return true;
    }

    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public static Bitmap getVideoThumbNail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        }
        catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        finally {
            try {
                retriever.release();
            }
            catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
    public void  GetVideoFileList(List<FileItem> videolist,String str){
        File root_path=new File(str);
        String [] path;
        path=root_path.list();
        if(path!=null){
            for(int i=0;i<path.length;i++){
                path[i]=root_path+"/"+path[i];
                if(!(new File(path[i]).isDirectory())){
                    if(path[i].trim().toLowerCase().endsWith(".mp4")){
                        Bitmap bitmap=imageScale(getVideoThumbNail(path[i]));
                        FileItem fileitem=new FileItem(path[i],bitmap);
                        videolist.add(fileitem);
                    }
                }
            }
        }
        else {
            Intent intent=new Intent(FileChooseActivity.this,MainActivity.class);
            Toast.makeText(FileChooseActivity.this, "目录无文件", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }

    }
    public  Bitmap imageScale(Bitmap bitmap) {
        int bitmap_w=70*(400/160);
        int bitmap_h=70*(400/160);
        int src_w = bitmap.getWidth();
        int src_h = bitmap.getHeight();
        float scale_w = ((float) bitmap_w) / src_w;
        float scale_h = ((float) bitmap_h) / src_h;
        Matrix matrix = new Matrix();
        matrix.postScale(scale_w, scale_h);
        return Bitmap.createBitmap(bitmap, 0, 0, src_w, src_h, matrix, true);
    }

}
