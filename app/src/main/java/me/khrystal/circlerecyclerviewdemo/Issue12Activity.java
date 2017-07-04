package me.khrystal.circlerecyclerviewdemo;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.khrystal.library.widget.CircleRecyclerView;
import me.khrystal.library.widget.CurrentItemCallback;
import me.khrystal.library.widget.ScaleXViewMode;
import me.khrystal.library.widget.ScaleYViewMode;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/7/3
 * update time:
 * email: 723526676@qq.com
 */

public class Issue12Activity extends AppCompatActivity {

    private CircleRecyclerView mCircleRecyclerView;
    private ImageView topIv;

    private List<Integer> mImgList;

    private Integer[] mImgs = {
        R.color.color1,
        R.color.color2,
        R.color.color3,
        R.color.color4,
        R.color.color5,
        R.color.color6,
        R.color.color7,
        R.color.color8,
        R.color.color9,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue12);
        mCircleRecyclerView = (CircleRecyclerView) findViewById(R.id.circle_rv);
        topIv = (ImageView) findViewById(R.id.topIv);

        mCircleRecyclerView.setLayoutManager(new LinearLayoutManager(Issue12Activity.this, LinearLayoutManager.HORIZONTAL, false));
        mCircleRecyclerView.setViewMode(new ScaleXViewMode());
        mCircleRecyclerView.setNeedLoop(false);

        mImgList = Arrays.asList(mImgs);

        mCircleRecyclerView.setAdapter(new A());
        mCircleRecyclerView.setNeedCenterForce(true);

        mCircleRecyclerView.setCurrentItemCallback(new CurrentItemCallback() {
            @Override
            public void onItemInCenter(View centerItem) {
                int resId = (int) centerItem.getTag(R.string.item_position);
                Drawable drawable = topIv.getDrawable();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    drawable.setColorFilter(getColor(resId), PorterDuff.Mode.SRC_IN);
                else
                    drawable.setColorFilter(getResources().getColor(resId), PorterDuff.Mode.SRC_IN);
            }
        });

    }

    class A extends RecyclerView.Adapter<VH> {

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            VH h = null;
            h = new VH(LayoutInflater.from(Issue12Activity.this)
                    .inflate(R.layout.item_h, parent, false));

            return h;
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.tv.setText("Number :" + (position % mImgList.size()));
            Integer resId = mImgList.get(position % mImgList.size());
            holder.iv.setBackgroundResource(resId);
            holder.itemView.setTag(R.string.item_position, resId);
        }

        @Override
        public int getItemCount() {
            return Integer.MAX_VALUE;
        }
    }


    class VH extends RecyclerView.ViewHolder {

        TextView tv;
        ImageView iv;

        public VH(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.item_text);
            iv = (ImageView) itemView.findViewById(R.id.item_img);
        }
    }


}
