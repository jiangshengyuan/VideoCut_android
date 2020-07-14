package com.example.graduateapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    public static VideoView vv;
    public static ImageView image;
    public static List<Video> video_list=new ArrayList<>();
    public static List<Integer> video_choose_list=new ArrayList<>();
    public static VideoAdapter videoadapter;
    public static RecyclerView recyclerview_video;
    public static Button btn_add_video_behind;
    public static Button btn_close;
    public static Button btn_output;
    public static Button btn_spit;
    public static Button btn_merge;
    public static Button btn_delete;
    public static Button btn_add_video_before;
    public static Button btn_play_stop;
    public static Intent intent;
    public static int before_or_behind_add=0;
    public static LinearLayoutManager layoutManager;
    public static double videocut_begin_time=0;
    public static double videocut_end_time;
    public static double video_choose_time;
    public static int video_play_stop_status=0;
    public static String rootpath = Environment.getExternalStoragePublicDirectory("").getAbsolutePath();
    public static String choose_path_string=rootpath+"/videocut/choose";
    public static File choose_path = new File(choose_path_string);
//    public static String processing_path_string=rootpath+"/videocut/processing";
//    public static File processing_path=new File(processing_path_string);
    public static String list_path_string=rootpath+"/videocut/videolist";
    public static File list_path=new File(list_path_string);
    public static String output_path_string=rootpath+"/videocut/output";
    public static File output_path=new File(output_path_string);
    public static String video_tranport=rootpath+"/videocut/videolist/-1.mp4";
    public static String new_path=list_path_string+"/-1.mp4";
    @SuppressLint("StaticFieldLeak")
    public static Context mContext;
    public static int totalDx=0;
    public static int choose_item_positoin=0;
    //屏幕参数
    public static int width;
    public static int height;
    public static float density;
    public static int densityDpi;
    public static int screenWidth;
    public static int screenHeight;
    public static int video_gap;
    public static double percent=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;         // 屏幕宽度（像素）
        height = dm.heightPixels;       // 屏幕高度（像素）
        density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        Log.i("tag","实际屏幕转换倍数"+density);
        densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        Log.i("tag","实际屏幕密度"+densityDpi);
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        Log.i("tag","实际屏幕宽度"+screenWidth);
        screenHeight = (int) (height / density);// 屏幕高度(dp)
        Log.i("tag","实际屏幕高度"+screenHeight);
        video_gap=36;
       if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED||
               ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED||
               ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS},0);
       }

        File dirFile;
        dirFile = new File(rootpath+"/videocut");
        if (!dirFile.exists()) {
            boolean mkdirs = dirFile.exists();
            if (!mkdirs) {
                Log.e("TAG", dirFile.toString() + " 文件夹创建失败");
            } else {
                Log.e("TAG", "文件夹创建成功");
            }
        }
        dirFile = new File(choose_path_string);
        if (!dirFile.exists()) {
            boolean mkdirs = dirFile.mkdirs();
            if (!mkdirs) {
                Log.e("TAG", dirFile.toString() + " 文件夹创建失败");
            } else {
                Log.e("TAG", "文件夹创建成功");
            }
        }
//        dirFile = new File(processing_path_string);
//        if (!dirFile.exists()) {
//            boolean mkdirs = dirFile.mkdirs();
//            if (!mkdirs) {
//                Log.e("TAG", "文件夹创建失败");
//            } else {
//                Log.e("TAG", "文件夹创建成功");
//            }
//        }
        dirFile = new File(output_path_string);
        if (!dirFile.exists()) {
            boolean mkdirs = dirFile.mkdirs();
            if (!mkdirs) {
                Log.e("TAG", "文件夹创建失败");
            } else {
                Log.e("TAG", "文件夹创建成功");
            }
        }

        dirFile = new File(list_path_string);
        if (!dirFile.exists()) {
            boolean mkdirs = dirFile.mkdirs();
            if (!mkdirs) {
                Log.e("TAG", "文件夹创建失败");
            } else {
                Log.e("TAG", "文件夹创建成功");
            }
        }

        vv = findViewById(R.id.vdo_play);
        vv.setVisibility(View.VISIBLE);
        image = findViewById(R.id.image);
        image.setVisibility(View.INVISIBLE);

        btn_close= findViewById(R.id.btn_close);//关闭应用程序
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_output= findViewById(R.id.btn_output);//视频输出
        btn_output.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<video_list.size();i++){
                    String old_file_path_string=list_path_string+"/"+video_list.get(i).getName()+".mp4";
                    String new_file_path_string=output_path_string+"/"+video_list.get(i).getName()+".mp4";
                    try{
                        FileInputStream fileInputStream = new FileInputStream(old_file_path_string);
                        FileOutputStream fileOutputStream = new FileOutputStream(new_file_path_string);
                        byte[] buffer = new byte[1024];
                        int byteRead;
                        while (-1 != (byteRead = fileInputStream.read(buffer))) {
                            fileOutputStream.write(buffer, 0, byteRead);
                        }
                        fileInputStream.close();
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                finish();
            }
        });

        btn_spit= findViewById(R.id.btn_spit);//视频剪切
        btn_spit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                choose_item_positoin = totalDx / 540;
                percent = (((float) (totalDx - choose_item_positoin * 540) / 540));
                String old_path = list_path_string + "/" + video_list.get(choose_item_positoin).getName() + ".mp4";//被剪切视频所在完整路径//输出的完整路径
                videocut_end_time = (int) (getLocalVideoDuration(old_path));
                cutMp4(old_path, list_path_string + "/-1.mp4", percent, videocut_end_time);
                Bitmap bitmap = imageScale(getVideoThumbNail(list_path_string + "/-1.mp4"));
                Video video_temp = new Video("-2", bitmap, choose_item_positoin + 1, 0);
                video_list.add(choose_item_positoin + 1, video_temp);
                new File(list_path_string + "/-1.mp4").renameTo(new File(list_path_string + "/" + (choose_item_positoin + 1) + ".mp4"));
                cutMp4(old_path, old_path, 0, percent);
                listRefresh(video_list);
                video_choose_list.clear();
            }
        });

        btn_merge= findViewById(R.id.btn_merge);//视频合并
        btn_merge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(video_choose_list.size()==2){
                    Collections.sort(video_choose_list);
                    List<String> video_choose_string=new ArrayList<>();
                    for(int i=0;i<video_choose_list.size();i++){
                        video_choose_string.add(list_path_string+"/"+video_list.get(video_choose_list.get(i)).getName()+".mp4");//选择视频地址数组
                    }
                    Toast.makeText(MainActivity.this, "请稍等", Toast.LENGTH_SHORT).show();
                    mergeMp4(video_choose_string,new_path);//合并方法
                    Bitmap bitmap=imageScale(getVideoThumbNail(new_path));
                    Video video_temp = new Video("new", bitmap, video_choose_list.get(0), 0);
                    int min=(video_choose_list.get(0));
                    for(int i=video_choose_list.size()-1;i>=0;i--){
                        videoDelete(list_path_string+"/"+video_list.get(video_choose_list.get(i)).getName()+".mp4");
                        video_list.remove((int)(video_choose_list.get(i)));
                    }
                    video_list.add(min, video_temp);
                    listRefresh(video_list);
                    Toast.makeText(MainActivity.this, "合并完成", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "视频选择数量不为2", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_delete= findViewById(R.id.btn_delete);//视频删除
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(video_choose_list.size()>0){
                    for(int i=video_choose_list.size()-1;i>=0;i--){
                        videoDelete(list_path_string+"/"+video_list.get(video_choose_list.get(i)).getName()+".mp4");
                        video_list.remove((int)video_choose_list.get(i));
                    }
                    listRefresh(video_list);
                }
                }

        });

        btn_add_video_before= findViewById(R.id.btn_addvideo_before);//当前视频前添加视频
        btn_add_video_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(video_choose_list.size()==0){
                    intent = new Intent(MainActivity.this,FileChooseActivity.class);
                    startActivityForResult(intent,0);
                }
                else if(video_choose_list.size()==1){
                    intent = new Intent(MainActivity.this,FileChooseActivity.class);
                    startActivityForResult(intent,0);
                }else Toast.makeText(MainActivity.this, "视频选择数量过多或未选择", Toast.LENGTH_SHORT).show();
            }
        });

        btn_play_stop= findViewById(R.id.btn_play_stop);//播放/暂停
        btn_play_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(video_choose_list.size()==1){
                    image.setVisibility(View.INVISIBLE);
                    vv.setVisibility(View.VISIBLE);
                    String video_path=list_path_string+"/"+video_list.get(video_choose_list.get(0)).getName()+".mp4";
                    vv.setVideoPath(video_path);
                    if(video_play_stop_status==0){
                        vv.start();
                        video_play_stop_status=1;
                    }else {vv.pause();video_play_stop_status=0;}
                }
            }
        });

        btn_add_video_behind = findViewById(R.id.btn_addvideo_behind);//当前视频后添加视频
        btn_add_video_behind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(video_choose_list.size()==0){
                    intent = new Intent(MainActivity.this,FileChooseActivity.class);
                    startActivityForResult(intent,1);
                }
                else if(video_choose_list.size()==1){
                    intent = new Intent(MainActivity.this,FileChooseActivity.class);
                    startActivityForResult(intent,1);
                }else Toast.makeText(MainActivity.this, "视频选择数量过多或未选择", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerview_video = findViewById(R.id.recyclerview_video_choose);
        layoutManager=new LinearLayoutManager(MainActivity.this); // Example of a call to a native method
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview_video.setLayoutManager(layoutManager);
        videoadapter = new VideoAdapter(video_list);
        recyclerview_video.setAdapter(videoadapter);
        //listRefresh(video_list);
        //recyclerview_video.addItemDecoration(new SpaceItemDecoration((int)(video_gap*density)));
        recyclerview_video.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                Log.i("tag","this is you log info"+totalDx);
            }
        });
    }
    @Override
    protected  void onActivityResult(int requestcode,int resultCode,Intent data) {//接收文件选择完毕并刷新视图
        super.onActivityResult(requestcode, resultCode, data);
        switch (requestcode) {
            case 0: {
                if (resultCode == RESULT_OK) {
                    video_tranport = data.getStringExtra("data_return");
                    Bitmap bitmap = imageScale(getVideoThumbNail(video_tranport));
                    Video video_temp = new Video("-1", bitmap, choose_item_positoin, 0);
                    video_list.add(choose_item_positoin, video_temp);
                    listRefresh(video_list);
                    videoadapter.notifyDataSetChanged();
                    choose_item_positoin=0;
                    video_choose_list.clear();
                    Toast.makeText(MainActivity.this, "加载成功！", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(MainActivity.this, "加载失败！", Toast.LENGTH_SHORT).show();
            }break;
            case 1:{
                if (resultCode == RESULT_OK) {
                    if(video_list.size()!=0){
                        Bitmap bitmap = imageScale(getVideoThumbNail(video_tranport));
                        Video video_temp = new Video("-1", bitmap, choose_item_positoin, 0);
                        video_list.add(choose_item_positoin+1, video_temp);
                        listRefresh(video_list);
                        Toast.makeText(MainActivity.this, "加载成功！", Toast.LENGTH_SHORT).show();
                    }else {
                        Bitmap bitmap = imageScale(getVideoThumbNail(video_tranport));
                        Video video_temp = new Video("0", bitmap, choose_item_positoin, 0);
                        video_list.add(0, video_temp);
                        listRefresh(video_list);
                        Toast.makeText(MainActivity.this, "加载成功！", Toast.LENGTH_SHORT).show();
                    }
                }else Toast.makeText(MainActivity.this, "加载失败！", Toast.LENGTH_SHORT).show();
            }break;
            default:
        }
    }
    public void listRefresh(List<Video> video_list){//recyclerview 刷新
        for(int i=video_list.size()-1;i>=0;i--) {
            if(!(new File(list_path_string+"/"+i+".mp4").exists())) {
                new File(list_path_string + "/" + video_list.get(i).getName() + ".mp4").renameTo(
                        new File(list_path_string + "/" + i + ".mp4"));
            }
            video_list.get(i).setName("" + i);
            video_list.get(i).setPosition(i);
            video_list.get(i).resetChosen(0);

        }
        videoadapter.notifyDataSetChanged();
        video_choose_list.clear();
        choose_item_positoin=0;
//        recyclerview_video.addItemDecoration(new SpaceItemDecoration(video_gap));
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {//权限响应
        if (requestCode == 0) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        if (grantResults[2] == PackageManager.PERMISSION_GRANTED)
                            Toast.makeText(MainActivity.this, "已授权存储权限", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private static void delete(File file) {
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File f : listFiles) {
                if (f.isDirectory()) {
                    delete(f);
                } else {
                    f.delete();
                }
            }
        }
        file.delete();
    }

    private synchronized void cutMp4(final String FilePath,  final String output_path,final double startTime, final double endTime){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //视频剪切
                    VideoClipUtils.clip(FilePath, output_path, videocut_begin_time, videocut_end_time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private synchronized void mergeMp4(final List<String> inputVideos, final String outputPath){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    AWMp4ParserHelper.mergeVideos(inputVideos,outputPath);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public static int getLocalVideoDuration(String videoPath) {
        int duration;
        try {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(videoPath);
            duration = Integer.parseInt(mmr.extractMetadata
                    (MediaMetadataRetriever.METADATA_KEY_DURATION));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return duration;
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

    public static void videoDelete(String path){
        File file=new File(path);
        file.delete();
    }

    public static Bitmap imageScale(Bitmap bitmap) {
        int bitmap_w=200*(400/160);
        int bitmap_h=80*(400/160);
        int src_w = bitmap.getWidth();
        int src_h = bitmap.getHeight();
        float scale_w = ((float) bitmap_w) / src_w;
        float scale_h = ((float) bitmap_h) / src_h;
        Matrix matrix = new Matrix();
        matrix.postScale(scale_w, scale_h);
        return Bitmap.createBitmap(bitmap, 0, 0, src_w, src_h, matrix, true);
    }
}
