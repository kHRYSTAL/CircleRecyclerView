package me.khrystal.circlerecyclerviewdemo;

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
    private List<Integer> mImgList;

    private Integer[] mImgs = {
            R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4,
            R.drawable.img_5, R.drawable.img_6, R.drawable.img_7, R.drawable.img_8,
            R.drawable.img_9, R.drawable.img_10, R.drawable.img_11, R.drawable.img_12
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue12);
        mCircleRecyclerView = (CircleRecyclerView) findViewById(R.id.circle_rv);
        mCircleRecyclerView.setLayoutManager(new LinearLayoutManager(Issue12Activity.this));
        mCircleRecyclerView.setViewMode(new ScaleYViewMode());
        mCircleRecyclerView.setNeedLoop(false);

        mImgList = Arrays.asList(mImgs);
        Collections.shuffle(mImgList);

        mCircleRecyclerView.setAdapter(new A());
        mCircleRecyclerView.setNeedCenterForce(true);

        mCircleRecyclerView.setCurrentItemCallback(new CurrentItemCallback() {
            @Override
            public void onItemInCenter(View centerItem) {
                int position = (int) centerItem.getTag(R.string.item_position);
                position = position % mImgList.size();
                Toast.makeText(Issue12Activity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    class A extends RecyclerView.Adapter<VH> {

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            VH h = null;
            h = new VH(LayoutInflater.from(Issue12Activity.this)
                    .inflate(R.layout.item_v, parent, false));

            return h;
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.tv.setText("Number :" + (position % mImgList.size()));
            Glide.with(Issue12Activity.this)
                    .load(mImgList.get(position % mImgList.size()))
                    .bitmapTransform(new CropCircleTransformation(Issue12Activity.this))
                    .into(holder.iv);
            holder.itemView.setTag(R.string.item_position, position);
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
