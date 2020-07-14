package com.example.graduateapp;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int totalCount = parent.getAdapter().getItemCount();
        if (position == 0) {//第一个
            outRect.left =(int)(MainActivity.screenWidth*MainActivity.density/2);
        }
        else if(position == 1){
            outRect.left =space;
        }
        else if(position == 2){
            outRect.left =space;
        }
        else if(position == 3){
            outRect.left =space;
        }
        else if(position == 4){
            outRect.left =space;
        }
        else if(position == 5){
            outRect.left =space;
        }
        else if(position == 6){
            outRect.left =space;
        }
        else if(position == 7){
            outRect.left =space;
        }
        else if(position == 8){
            outRect.left =space;
        }
        else if(position == 9){
            outRect.left =space;
        }
        else if(position == 10){
            outRect.left =space;
        }
        else if(position == 11){
            outRect.left =space;
        }
        else if(position == 12){
            outRect.left =space;
        }
        else if(position == 13){
            outRect.left =space;
        }
        else if(position == 14){
            outRect.left =space;
        }


//        if (position == 0) {//第一个
//            if(totalCount==1){
//                outRect.left =(int)(MainActivity.screenWidth*MainActivity.density/2-MainActivity.video_gap*MainActivity.density);
//              //  outRect.right = (int)(MainActivity.screenWidth*MainActivity.density/2);
//            }
//            else {
//                outRect.left =(int)(MainActivity.screenWidth*MainActivity.density/2-MainActivity.video_gap*MainActivity.density);
//            }
//        } else if (position == totalCount - 1) {//最后一个
//            //outRect.left = space;
//            outRect.right =(int)(MainActivity.screenWidth*MainActivity.density/2);
//        }
//        else {//中间其它的
//            outRect.left = space;
//        }
    }
}