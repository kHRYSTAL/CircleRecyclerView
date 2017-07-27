package me.khrystal.circlerecyclerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.khrystal.library.widget.CircleRecyclerView;
import me.khrystal.library.widget.ScaleYViewMode;

/**
 * usage: RealLoop recyclerView, but efficiency not higher 2^30
 * author: kHRYSTAL
 * create time: 17/7/27
 * update time:
 * email: 723526676@qq.com
 */

public class Issue13Activity extends AppCompatActivity {

    private CircleRecyclerView mCircleRecyclerView;
    private List<Integer> mImgList;


    private Integer[] mImgs = {
            R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4,
            R.drawable.img_5, R.drawable.img_6, R.drawable.img_7, R.drawable.img_8,
            R.drawable.img_9, R.drawable.img_10, R.drawable.img_11, R.drawable.img_12
    };
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue13);
        mCircleRecyclerView = (CircleRecyclerView) findViewById(R.id.circle_rv);
        layoutManager = new LinearLayoutManager(Issue13Activity.this);
        mCircleRecyclerView.setLayoutManager(layoutManager);
        mCircleRecyclerView.setViewMode(new ScaleYViewMode());
        mCircleRecyclerView.setNeedLoop(false);  // set no fake loop, it's mean not use MAX_VALUE >> 1
        mImgList = Arrays.asList(mImgs);
        Collections.shuffle(mImgList);

        mCircleRecyclerView.setAdapter(new A());
        //if init not want find center view but want view scroll fix center write this below setAdapter()
        mCircleRecyclerView.setNeedCenterForce(true);
        mCircleRecyclerView.setOnScrollListener(new CircleRecyclerView.OnScrollListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                if (layoutManager.findLastVisibleItemPosition() <= 2) {
                    layoutManager.scrollToPosition(mImgList.size() + 2);
                } else if (layoutManager.findFirstVisibleItemPosition() + layoutManager.findLastVisibleItemPosition() > layoutManager.getItemCount() - 2) {//到底部添加数据
                    layoutManager.scrollToPosition(layoutManager.findFirstVisibleItemPosition() - mImgList.size());
                }
            }

            @Override
            public void onScrollStateChanged(int state) {

            }

            @Override
            public void onScrolled(int dx, int dy) {

            }
        });
    }

    class A extends RecyclerView.Adapter<Issue13Activity.VH> {

        @Override
        public Issue13Activity.VH onCreateViewHolder(ViewGroup parent, int viewType) {
            Issue13Activity.VH h = null;
            h = new Issue13Activity.VH(LayoutInflater.from(Issue13Activity.this)
                    .inflate(R.layout.item_v, parent, false));

            return h;
        }

        @Override
        public void onBindViewHolder(Issue13Activity.VH holder, int position) {
            holder.tv.setText("Number :" + (position % mImgList.size()));
            Glide.with(Issue13Activity.this)
                    .load(mImgList.get(position % mImgList.size()))
                    .bitmapTransform(new CropCircleTransformation(Issue13Activity.this))
                    .into(holder.iv);

        }

        @Override
        public int getItemCount() {
            return mImgList.size() * 3;
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

